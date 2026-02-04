#!/usr/bin/env node
/**
 * icon_testディレクトリのスナップショットを生成するスクリプト
 * 現在のテストファイル構造をJSON形式で出力します
 */
const fs = require('fs');
const path = require('path');

/**
 * ディレクトリ構造を再帰的にスキャン
 */
function scanDirectory(dir, baseDir = dir) {
    const result = {
        name: path.basename(dir),
        path: path.relative(baseDir, dir) || '.',
        type: 'directory',
        children: []
    };
    
    try {
        const entries = fs.readdirSync(dir, { withFileTypes: true });
        
        // ディレクトリとファイルを分類
        const dirs = entries.filter(e => e.isDirectory()).sort((a, b) => a.name.localeCompare(b.name));
        const files = entries.filter(e => e.isFile()).sort((a, b) => a.name.localeCompare(b.name));
        
        // サブディレクトリを処理
        for (const entry of dirs) {
            const fullPath = path.join(dir, entry.name);
            // node_modules と .git は除外
            if (entry.name !== 'node_modules' && entry.name !== '.git') {
                result.children.push(scanDirectory(fullPath, baseDir));
            }
        }
        
        // ファイルを処理
        for (const entry of files) {
            const fullPath = path.join(dir, entry.name);
            try {
                const stats = fs.statSync(fullPath);
                result.children.push({
                    name: entry.name,
                    path: path.relative(baseDir, fullPath),
                    type: 'file',
                    size: stats.size
                });
            } catch (error) {
                // ファイルがアクセスできない場合はスキップ
            }
        }
    } catch (error) {
        result.error = error.message;
    }
    
    return result;
}

/**
 * 統計情報を収集
 */
function collectStats(structure) {
    const stats = {
        directories: 0,
        files: 0,
        totalSize: 0,
        byExtension: {}
    };
    
    function traverse(node) {
        if (node.type === 'directory') {
            stats.directories++;
            if (node.children) {
                node.children.forEach(traverse);
            }
        } else if (node.type === 'file') {
            stats.files++;
            stats.totalSize += node.size || 0;
            
            const ext = path.extname(node.name).toLowerCase();
            if (ext) {
                stats.byExtension[ext] = (stats.byExtension[ext] || 0) + 1;
            }
        }
    }
    
    traverse(structure);
    return stats;
}

/**
 * メイン処理
 */
function main() {
    const iconTestDir = path.join(__dirname, '..', 'icon_test');
    const outputFile = path.join(__dirname, '..', 'icon_test', 'snapshot.json');
    
    if (!fs.existsSync(iconTestDir)) {
        console.error(`❌ Directory not found: ${iconTestDir}`);
        process.exit(1);
    }
    
    console.log('📸 Scanning icon_test directory...\n');
    
    // ディレクトリ構造をスキャン
    const structure = scanDirectory(iconTestDir);
    
    // 統計情報を収集
    const stats = collectStats(structure);
    
    // 結果を構築
    const snapshot = {
        generatedAt: new Date().toISOString(),
        summary: {
            totalDirectories: stats.directories,
            totalFiles: stats.files,
            totalSize: stats.totalSize,
            topExtensions: Object.entries(stats.byExtension)
                .sort((a, b) => b[1] - a[1])
                .slice(0, 10)
                .map(([ext, count]) => `${ext}: ${count}`)
        },
        structure: structure
    };
    
    // スナップショットを保存
    fs.writeFileSync(outputFile, JSON.stringify(snapshot, null, 2), 'utf-8');
    
    console.log('✅ Snapshot generated successfully!\n');
    console.log('📊 Summary:');
    console.log(`   • Directories: ${stats.directories}`);
    console.log(`   • Files: ${stats.files}`);
    console.log(`   • Total Size: ${(stats.totalSize / 1024).toFixed(2)} KB`);
    console.log(`\n📁 Saved to: ${outputFile}`);
}

main();

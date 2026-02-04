#!/usr/bin/env node
/**
 * アイコンテスト用のファイル・フォルダー構造を生成するスクリプト
 */
const fs = require('fs');
const path = require('path');

/**
 * JSONファイルを読み込む
 */
function loadJson(filePath) {
    try {
        const content = fs.readFileSync(filePath, 'utf-8');
        return JSON.parse(content);
    } catch (error) {
        console.error(`Error loading ${filePath}:`, error.message);
        return {};
    }
}

/**
 * Icon -> [ext1, ext2] 形式を ext1 -> Icon, ext2 -> Icon に変換
 */
function flattenMapping(iconMap) {
    const result = {};
    for (const [iconName, extensions] of Object.entries(iconMap)) {
        for (const ext of extensions) {
            result[ext] = iconName;
        }
    }
    return result;
}

/**
 * テスト用のファイル・フォルダー構造を作成
 */
function createTestStructure(baseDir, theme, maxSamples = 100) {
    // 設定ファイルのパス
    const settingsDir = path.join(__dirname, '..', 'src', 'main', 'resources', 'settings', theme);
    
    // マッピングを読み込み
    const extensionsMapRaw = loadJson(path.join(settingsDir, 'icon_extensions.json'));
    const filenamesMapRaw = loadJson(path.join(settingsDir, 'icon_filenames.json'));
    const foldersMapRaw = loadJson(path.join(settingsDir, 'icon_folders.json'));
    
    const extensionsMap = flattenMapping(extensionsMapRaw);
    const filenamesMap = flattenMapping(filenamesMapRaw);
    const foldersMap = flattenMapping(foldersMapRaw);
    
    // テスト用ディレクトリを作成
    const testDir = path.join(baseDir, theme);
    if (fs.existsSync(testDir)) {
        fs.rmSync(testDir, { recursive: true, force: true });
    }
    fs.mkdirSync(testDir, { recursive: true });
    
    // ファイルを作成（拡張子ベース）
    console.log(`Creating test files for ${theme}...`);
    let count = 0;
    const extEntries = Object.entries(extensionsMap).slice(0, maxSamples);
    for (const [ext, iconName] of extEntries) {
        const filePath = path.join(testDir, `test${String(count).padStart(2, '0')}.${ext}`);
        fs.writeFileSync(filePath, `Test file for ${iconName}\n`, 'utf-8');
        count++;
    }
    
    // ファイルを作成（ファイル名ベース）
    count = 0;
    const fileEntries = Object.entries(filenamesMap).slice(0, maxSamples);
    for (const [filename, iconName] of fileEntries) {
        let filePath = path.join(testDir, filename);
        // ファイル名がディレクトリを含む場合はディレクトリを作成
        if (filename.includes('/') || filename.includes('\\')) {
            const normalizedFilename = filename.replace(/\//g, path.sep).replace(/\\/g, path.sep);
            filePath = path.join(testDir, normalizedFilename);
            fs.mkdirSync(path.dirname(filePath), { recursive: true });
        }
        fs.writeFileSync(filePath, `Test file for ${iconName}\n`, 'utf-8');
        count++;
    }
    
    // フォルダーを作成
    count = 0;
    const folderEntries = Object.entries(foldersMap).slice(0, maxSamples);
    for (const [folderName, iconName] of folderEntries) {
        let folderPath = path.join(testDir, folderName);
        if (folderName.includes('/') || folderName.includes('\\')) {
            const normalizedFolderName = folderName.replace(/\//g, path.sep).replace(/\\/g, path.sep);
            folderPath = path.join(testDir, normalizedFolderName);
        }
        fs.mkdirSync(folderPath, { recursive: true });
        // ダミーファイルを作成
        fs.writeFileSync(path.join(folderPath, '.gitkeep'), '', 'utf-8');
        count++;
    }
    
    console.log(`Created ${testDir}`);
    console.log(`  - Files by extension: ${Math.min(maxSamples, Object.keys(extensionsMap).length)}`);
    console.log(`  - Files by name: ${Math.min(maxSamples, Object.keys(filenamesMap).length)}`);
    console.log(`  - Folders: ${Math.min(maxSamples, Object.keys(foldersMap).length)}`);
}

/**
 * メイン処理
 */
function main() {
    const baseDir = path.join(__dirname, '..', 'icon_test');
    
    // 両方のテーマでテスト構造を作成
    for (const theme of ['vscord', 'material']) {
        try {
            createTestStructure(baseDir, theme, 100);
        } catch (error) {
            console.error(`Error creating test structure for ${theme}:`, error.message);
        }
    }
    
    console.log('\n✓ Test files generated successfully!');
    console.log(`Location: ${baseDir}`);
}

main();

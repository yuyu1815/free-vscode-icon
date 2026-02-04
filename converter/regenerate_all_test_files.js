#!/usr/bin/env node
/**
 * icon_testディレクトリ全体を自動生成するメインスクリプト
 * VSCode IconsとMaterial Iconsの両方のテストファイルを生成します
 */
const { execSync } = require('child_process');
const path = require('path');

console.log('🚀 Starting icon test files regeneration...\n');

const scriptDir = __dirname;
const vscodeScript = path.join(scriptDir, 'generate_vscode_test_files.js');
const materialScript = path.join(scriptDir, 'generate_material_test_files.js');

try {
    // VSCode Icons用テストファイルを生成
    console.log('📦 Generating VSCode Icons test files...');
    execSync(`node "${vscodeScript}"`, { stdio: 'inherit' });
    
    console.log('\n');
    
    // Material Icons用テストファイルを生成
    console.log('📦 Generating Material Icons test files...');
    execSync(`node "${materialScript}"`, { stdio: 'inherit' });
    
    console.log('\n✅ All icon test files generated successfully!');
    console.log('📁 Location: path.join(__dirname, "..", "icon_test")');
    
} catch (error) {
    console.error('\n❌ Error generating test files:', error.message);
    process.exit(1);
}

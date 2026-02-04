# free-vscode-icon

An IntelliJ IDEA plugin that brings beautiful VS Code-style file and folder icons to your JetBrains IDE. Supports multiple icon themes including VSCode Icons and Material Icons.

![Build](https://github.com/yuyu1815/free-vscode-icon/workflows/Build/badge.svg)

## Features

- **Multiple Icon Themes**
  - **VSCode Icons**: Based on the popular [vscode-icons/vscode-icons](https://github.com/vscode-icons/vscode-icons) project
  - **Material Icons**: Beautiful Material Design-inspired icons

- **Comprehensive Coverage**
  - Support for 100+ file extensions
  - Special icons for popular frameworks and tools
  - Custom folder icons for common directories

- **Easy Configuration**
  - Simple project-level settings
  - Enable/disable custom icons per project
  - Switch themes instantly without restart

## Installation

### Using JetBrains Marketplace

1. Open your JetBrains IDE
2. Go to <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd>
3. Click on <kbd>Marketplace</kbd>
4. Search for "free-vscode-icon"
5. Click <kbd>Install</kbd>

### Manual Installation

1. Download the latest release from [GitHub Releases](https://github.com/yuyu1815/free-vscode-icon/releases/latest)
2. Go to <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd>
3. Click the gear icon ⚙️
4. Select <kbd>Install plugin from disk...</kbd>
5. Choose the downloaded file

## Configuration

After installing the plugin:

1. Go to <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Tools</kbd> > <kbd>Icon Theme Settings</kbd>
2. Select your preferred theme from the dropdown:
   - **VSCode Icons**: Traditional VS Code style icons
   - **Material Icons**: Material Design inspired icons
3. Check/uncheck "Enable custom file icons" to toggle the plugin
4. Click <kbd>Apply</kbd> to save your changes

## Supported Icons

### File Extensions
- Programming languages: `.js`, `.ts`, `.py`, `.java`, `.kt`, `.go`, `.rs`, etc.
- Markup & Style: `.html`, `.css`, `.scss`, `.json`, `.xml`, etc.
- Frameworks: `.vue`, `.jsx`, `.tsx`, `.svelte`, etc.
- Config files: `.yaml`, `.toml`, `.ini`, `.env`, etc.

### Folders
- Common folders: `src`, `dist`, `build`, `test`, etc.
- Framework-specific: `node_modules`, `.vscode`, etc.

## Development

### Building the Plugin

```bash
./gradlew buildPlugin
```

### Running Tests

```bash
./gradlew test
```

### Generating Test Files

```bash
# Generate test files for all themes
node converter/regenerate_all_test_files.js

# Generate VSCode Icons test files only
node converter/generate_vscode_test_files.js

# Generate Material Icons test files only
node converter/generate_material_test_files.js

# Generate snapshot of test files
node converter/generate_icon_test_snapshot.js
```

## Credits

### Icons

This plugin uses icons from the following projects:

- **[vscode-icons/vscode-icons](https://github.com/vscode-icons/vscode-icons)**
  - License: [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/)
  - Copyright © 2016 Roberto Huertas

- **[PKief/vscode-material-icon-theme](https://github.com/PKief/vscode-material-icon-theme)**
  - License: [MIT](https://opensource.org/licenses/MIT)
  - Copyright © PKief
  - Material Design Icons for Visual Studio Code (30M+ installations)

### License

This plugin is licensed under the [Creative Commons Attribution-ShareAlike 4.0 International License](https://creativecommons.org/licenses/by-sa/4.0/).

```

You are free to:
- Share — copy and redistribute the material in any medium or format
- Adapt — remix, transform, and build upon the material

Under the following terms:
- **Attribution** — You must give appropriate credit to:
  - vscode-icons project (https://github.com/vscode-icons/vscode-icons)
  - vscode-material-icon-theme project (https://github.com/PKief/vscode-material-icon-theme)
  - This plugin
- **ShareAlike** — If you remix, transform, or build upon the material, you must distribute your contributions under the same license

```

**Icon Sources**
- VSCode Icons: [vscode-icons/vscode-icons repository](https://github.com/vscode-icons/vscode-icons)
- Material Icons: [PKief/vscode-material-icon-theme repository](https://github.com/PKief/vscode-material-icon-theme) (included in `/converter/vscode-material-icon-theme`)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

If you encounter any issues or have suggestions, please [open an issue](https://github.com/yuyu1815/free-vscode-icon/issues).

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history and changes.

---

Made with ❤️ by [yuyu1815](https://github.com/yuyu1815)

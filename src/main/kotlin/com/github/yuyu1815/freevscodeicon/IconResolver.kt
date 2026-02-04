package com.github.yuyu1815.freevscodeicon

import com.github.yuyu1815.freevscodeicon.IconTheme.MATERIAL_ICONS
import com.github.yuyu1815.freevscodeicon.IconTheme.VSCODE_ICONS

object IconResolver {
    private var currentTheme: IconTheme = VSCODE_ICONS

    /**
     * Set the current icon theme
     */
    fun setTheme(theme: IconTheme) {
        currentTheme = theme
    }

    /**
     * Get the current icon theme
     */
    fun getTheme(): IconTheme = currentTheme

    fun resolveIconName(name: String, isDirectory: Boolean): String? {
        val lowercaseName = name.lowercase()
        return if (isDirectory) {
            resolveFolderIcon(lowercaseName)
        } else {
            resolveFileIcon(lowercaseName)
        }
    }

    private fun resolveFolderIcon(name: String): String? {
        // Use theme-specific mapping for folder icons
        return when (currentTheme) {
            VSCODE_ICONS -> Mappings.vscordFolderNameMap[name]
            MATERIAL_ICONS -> resolveMaterialFolderIcon(name)
        }
    }

    private fun resolveFileIcon(name: String): String? {
        // Use theme-specific mapping for file icons
        return when (currentTheme) {
            VSCODE_ICONS -> resolveVSCodeFileIcon(name)
            MATERIAL_ICONS -> resolveMaterialFileIcon(name)
        }
    }

    private fun resolveVSCodeFileIcon(name: String): String? {
        // 1. Exact filename match
        Mappings.vscordFileNameMap[name]?.let { return it }

        // 2. Extension match (complex to simple)
        // e.g. "test.spec.ts" -> check "spec.ts" -> check "ts"
        var current = name
        while (current.contains(".")) {
            current = current.substringAfter(".")
            Mappings.vscordFileExtensionMap[current]?.let { return it }
        }
        return null
    }

    private fun resolveMaterialFileIcon(name: String): String? {
        // 1. Exact filename match
        Mappings.materialFileNameMap[name]?.let { return it }

        // 2. Extension match (complex to simple)
        var current = name
        while (current.contains(".")) {
            current = current.substringAfter(".")
            Mappings.materialFileExtensionMap[current]?.let { return it }
        }
        return null
    }

    private fun resolveMaterialFolderIcon(name: String): String? {
        return Mappings.materialFolderNameMap[name]
    }
}

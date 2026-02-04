package com.github.yuyu1815.freevscodeicon

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class VscordIconProvider : FileIconProvider {
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
        // Return null if plugin is disabled (uses default IDE icons)
        val config = project?.let { IconThemeConfig.getInstance() }
        if (config?.isEnabled() == false) {
            return null
        }

        // Resolve icon name based on current theme
        val theme = config?.getSelectedTheme() ?: IconTheme.VSCODE_ICONS
        IconResolver.setTheme(theme)

        val iconName = IconResolver.resolveIconName(file.name, file.isDirectory)

        return iconName?.let {
            loadIcon(it, file.isDirectory, theme)
        }
    }

    private fun loadIcon(name: String, isFolder: Boolean, theme: IconTheme): Icon? {
        // Material theme mappings include the prefix (e.g., "folder-src")
        // VSCode theme mappings don't include prefix (e.g., "src")
        val iconFileName = when (theme) {
            IconTheme.MATERIAL_ICONS -> name  // Use name from mapping as-is
            IconTheme.VSCODE_ICONS -> {
                // Add prefix for VSCode theme only
                val prefix = if (isFolder) "folder_type_" else "file_type_"
                "$prefix$name"
            }
        }

        val themePrefix = when (theme) {
            IconTheme.VSCODE_ICONS -> "vscode/"
            IconTheme.MATERIAL_ICONS -> "material/"
        }
        val path = "/icons/${themePrefix}$iconFileName.svg"
        val icon = IconLoader.findIcon(path, VscordIconProvider::class.java)

        // Log if icon file is not found (helps with debugging)
        if (icon == null) {
            println("[VscordIconProvider] Icon not found: $path (isFolder=$isFolder, theme=$theme)")
        }

        // Scale down icon if it's larger than 16x16 pixels
        return icon?.let {
            if (it.iconWidth > 16 || it.iconHeight > 16) {
                com.intellij.util.IconUtil.scale(it, null, 16.0f / it.iconWidth)
            } else {
                it
            }
        }
    }
}

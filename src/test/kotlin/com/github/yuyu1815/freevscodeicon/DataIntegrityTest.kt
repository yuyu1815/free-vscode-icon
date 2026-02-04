package com.github.yuyu1815.freevscodeicon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class DataIntegrityTest {

    @Test
    fun `check all vscord file extension mappings`() {
        val errors = mutableListOf<String>()
        val map = Mappings.vscordFileExtensionMap
        
        println("Checking ${map.size} file extension mappings...")

        map.forEach { (ext, iconName) ->
            // 1. Verify Resolution Logic
            // Note: resolveIconName does complex matching, so simple "file.$ext" might be tricky if precedence exists,
            // but for basic extension mapping it should work.
            val resolved = IconResolver.resolveIconName("dummy.$ext", false)
            if (resolved != iconName) {
                // It's possible that a filename rule overrides it, or complex extension logic.
                // But generally direct extension mapping should resolve.
                errors.add("Logic Error: Extension '.$ext' resolved to '$resolved' but expected '$iconName'")
            }

            // 2. Verify Resource Existence
            if (!iconResourceExists(iconName, false)) {
                errors.add("Resource Error: Icon 'file_type_$iconName.svg' not found for extension '.$ext'")
            }
        }

        if (errors.isNotEmpty()) {
            throw AssertionError("Found ${errors.size} integrity errors:\n" + errors.joinToString("\n"))
        }
    }

    @Test
    fun `check all vscord file name mappings`() {
        val errors = mutableListOf<String>()
        val map = Mappings.vscordFileNameMap

        println("Checking ${map.size} file name mappings...")

        map.forEach { (fileName, iconName) ->
            // 1. Verify Resolution
            val resolved = IconResolver.resolveIconName(fileName, false)
            if (resolved != iconName) {
                errors.add("Logic Error: Filename '$fileName' resolved to '$resolved' but expected '$iconName'")
            }

            // 2. Verify Resource
            if (!iconResourceExists(iconName, false)) {
                errors.add("Resource Error: Icon 'file_type_$iconName.svg' not found for filename '$fileName'")
            }
        }

        if (errors.isNotEmpty()) {
            throw AssertionError("Found ${errors.size} integrity errors:\n" + errors.joinToString("\n"))
        }
    }

    @Test
    fun `check all vscord folder mappings`() {
        val errors = mutableListOf<String>()
        val map = Mappings.vscordFolderNameMap

        println("Checking ${map.size} folder mappings...")

        map.forEach { (folderName, iconName) ->
            // 1. Verify Resolution
            val resolved = IconResolver.resolveIconName(folderName, true)
            if (resolved != iconName) {
                errors.add("Logic Error: Folder '$folderName' resolved to '$resolved' but expected '$iconName'")
            }

            // 2. Verify Resource
            if (!iconResourceExists(iconName, true)) {
                errors.add("Resource Error: Icon 'folder_type_$iconName.svg' not found for folder '$folderName'")
            }
        }

        if (errors.isNotEmpty()) {
            throw AssertionError("Found ${errors.size} integrity errors:\n" + errors.joinToString("\n"))
        }
    }

    private fun iconResourceExists(iconName: String, isFolder: Boolean): Boolean {
        val prefix = if (isFolder) "folder_type_" else "file_type_"
        val resourcePath = "/icons/$prefix$iconName.svg"
        return javaClass.getResource(resourcePath) != null
    }
}

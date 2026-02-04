package com.github.yuyu1815.freevscodeicon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class MappingsTest {

    @Test
    fun `should load vscord file extension map`() {
        // Arrange & Act
        // Mappings is a singleton, so loading happens on first access
        val map = Mappings.vscordFileExtensionMap

        // Assert
        assertNotNull(map, "File extension map should not be null")
        assertEquals("typescript", map["ts"], "ts extension should map to typescript")
        assertEquals("reactts", map["tsx"], "tsx extension should map to reactts")
    }

    @Test
    fun `should load vscord file name map`() {
        // Arrange & Act
        val map = Mappings.vscordFileNameMap

        // Assert
        assertNotNull(map, "File name map should not be null")
        assertEquals("npm", map["package.json"], "package.json should map to npm")
    }

    @Test
    fun `should load vscord folder name map`() {
        // Arrange & Act
        val map = Mappings.vscordFolderNameMap

        // Assert
        assertNotNull(map, "Folder name map should not be null")
        assertEquals("src", map["src"], "src folder should map to src")
        assertEquals("test", map["test"], "test folder should map to test")
    }

    @Test
    fun `should load material file extension map`() {
        // Arrange & Act
        val map = Mappings.materialFileExtensionMap

        // Assert
        assertNotNull(map, "Material file extension map should not be null")
        assertEquals("html", map["htm"], "htm extension should map to html")
    }

    @Test
    fun `should load material file name map`() {
        // Arrange & Act
        val map = Mappings.materialFileNameMap

        // Assert
        assertNotNull(map, "Material file name map should not be null")
        assertEquals("pug", map[".pug-lintrc"], ".pug-lintrc should map to pug")
    }

    @Test
    fun `should load material folder name map`() {
        // Arrange & Act
        val map = Mappings.materialFolderNameMap

        // Assert
        assertNotNull(map, "Material folder name map should not be null")
        assertEquals("folder-rust", map["rust"], "rust folder should map to folder-rust")
    }
}

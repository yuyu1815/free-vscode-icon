package com.github.yuyu1815.freevscodeicon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IconResolverTest {

    @Test
    fun `resolveIconName should return folder icon for exact match`() {
        // Arrange
        val name = "src"
        val isDirectory = true

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        assertEquals("src", result)
    }

    @Test
    fun `resolveIconName should return null for unknown folder`() {
        // Arrange
        val name = "unknown_folder_xyz"
        val isDirectory = true

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        assertNull(result)
    }

    @Test
    fun `resolveIconName should prioritize filename match over extension`() {
        // Arrange
        val name = "package.json"
        val isDirectory = false

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        assertEquals("npm", result)
    }

    @Test
    fun `resolveIconName should resolve simple extension`() {
        // Arrange
        val name = "app.ts"
        val isDirectory = false

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        assertEquals("typescript", result)
    }

    @Test
    fun `resolveIconName should resolve compound extension`() {
        // Arrange
        // TypeScript definition files use compound extension "d.ts"
        val name = "types.d.ts"
        val isDirectory = false

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        // "d.ts" maps to "typescriptdef"
        assertEquals("typescriptdef", result)
    }

    @Test
    fun `resolveIconName should handle case insensitivity`() {
        // Arrange
        val name = "APP.TS"
        val isDirectory = false

        // Act
        val result = IconResolver.resolveIconName(name, isDirectory)

        // Assert
        assertEquals("typescript", result)
    }
}

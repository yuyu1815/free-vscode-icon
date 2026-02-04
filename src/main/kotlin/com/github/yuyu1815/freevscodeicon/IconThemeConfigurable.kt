package com.github.yuyu1815.freevscodeicon

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.*

/**
 * Configuration UI for icon theme settings
 * Persists settings in JSON format
 */
class IconThemeConfigurable : Configurable {
    private var mainPanel: JPanel? = null
    private var themeComboBox: ComboBox<String>? = null
    private var enabledCheckBox: JBCheckBox? = null

    override fun getDisplayName(): String = "Icon Theme Settings"

    override fun createComponent(): JComponent? {
        if (mainPanel != null) return mainPanel

        mainPanel = JPanel(BorderLayout())
        val formPanel = JPanel()
        formPanel.layout = BoxLayout(formPanel, BoxLayout.Y_AXIS)

        // Enable/disable checkbox
        enabledCheckBox = JBCheckBox("Enable custom file icons")
        enabledCheckBox?.setSelected(true)
        formPanel.add(enabledCheckBox)
        formPanel.add(Box.createVerticalStrut(10))

        // Theme selection combo box
        val themeLabel = JLabel("Icon Theme:")
        formPanel.add(themeLabel)
        formPanel.add(Box.createVerticalStrut(5))

        themeComboBox = ComboBox()
        IconTheme.values().forEach { theme ->
            themeComboBox?.addItem(theme.displayName)
        }
        themeComboBox?.setSelectedIndex(0)
        formPanel.add(themeComboBox)
        formPanel.add(Box.createVerticalStrut(10))

        // Theme description
        val descriptionLabel = JLabel("<html><div style='width: 400px;'>${IconTheme.VSCODE_ICONS.description}</div></html>")
        descriptionLabel.alignmentX = Component.LEFT_ALIGNMENT
        formPanel.add(descriptionLabel)

        // Update description when combo box selection changes
        themeComboBox?.addActionListener {
            val selected = themeComboBox?.selectedItem as? String ?: return@addActionListener
            val theme = IconTheme.fromDisplayName(selected)
            descriptionLabel.text = "<html><div style='width: 400px;'>${theme?.description ?: ""}</div></html>"
        }

        formPanel.add(Box.createVerticalStrut(20))

        // Information panel
        val infoPanel = JPanel()
        infoPanel.layout = BoxLayout(infoPanel, BoxLayout.Y_AXIS)
        infoPanel.border = BorderFactory.createTitledBorder("Theme Information")

        IconTheme.values().forEach { theme ->
            val infoLabel = JLabel("• ${theme.displayName}: ${theme.iconCount} icons")
            infoLabel.alignmentX = Component.LEFT_ALIGNMENT
            infoPanel.add(infoLabel)
        }

        formPanel.add(infoPanel)

        mainPanel?.add(formPanel, BorderLayout.NORTH)
        return mainPanel
    }

    override fun isModified(): Boolean {
        val config = IconThemeConfig.getInstance()
        val selectedTheme = themeComboBox?.selectedItem as? String
        return enabledCheckBox?.isSelected != config.isEnabled() ||
               selectedTheme != config.getSelectedTheme().displayName
    }

    override fun apply() {
        val config = IconThemeConfig.getInstance()
        val selectedTheme = themeComboBox?.selectedItem as? String
        
        selectedTheme?.let { themeName ->
            IconTheme.fromDisplayName(themeName)?.let { theme ->
                config.setSelectedTheme(theme)
            }
        }
        
        config.setEnabled(enabledCheckBox?.isSelected ?: true)
    }

    override fun reset() {
        val config = IconThemeConfig.getInstance()
        enabledCheckBox?.setSelected(config.isEnabled())
        themeComboBox?.selectedItem = config.getSelectedTheme().displayName
    }

    override fun disposeUIResources() {
        mainPanel = null
        themeComboBox = null
        enabledCheckBox = null
    }
}

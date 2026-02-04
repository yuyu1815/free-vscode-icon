package com.github.yuyu1815.freevscodeicon

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persistent icon theme configuration
 * Saves and loads settings in JSON format
 */
@State(
    name = "IconThemeSettings",
    storages = [Storage("free-vscode-icon-settings.json")]
)
@Service(Service.Level.PROJECT)
class IconThemeConfig : PersistentStateComponent<IconThemeConfig.State> {

    data class State(
        var selectedTheme: String = IconTheme.VSCODE_ICONS.displayName,
        var isEnabled: Boolean = true
    ) {
        // Empty constructor for JSON serialization
        constructor() : this(
            selectedTheme = IconTheme.VSCODE_ICONS.displayName,
            isEnabled = true
        )
    }

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    /**
     * Get the currently selected theme
     */
    fun getSelectedTheme(): IconTheme {
        return IconTheme.fromDisplayName(state.selectedTheme) ?: IconTheme.VSCODE_ICONS
    }

    /**
     * Set the icon theme
     */
    fun setSelectedTheme(theme: IconTheme) {
        state.selectedTheme = theme.displayName
    }

    /**
     * Check if the plugin is enabled
     */
    fun isEnabled(): Boolean = state.isEnabled

    /**
     * Enable or disable the plugin
     */
    fun setEnabled(enabled: Boolean) {
        state.isEnabled = enabled
    }

    companion object {
        fun getInstance(): IconThemeConfig {
            return com.intellij.openapi.components.service()
        }
    }
}

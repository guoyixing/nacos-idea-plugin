package io.github.guoyixing.nacosideaplugin.core.setting

import com.intellij.openapi.options.SearchableConfigurable
import io.github.guoyixing.nacosideaplugin.core.setting.ui.SettingUi
import javax.swing.JComponent

/**
 * 设置界面-idea插件接口
 */
class SettingFactory : SearchableConfigurable{

    private val settingUI = SettingUi()

    /**
     * 唯一标识
     */
    override fun getId(): String {
        return "nacos.idea.plugin.setting"
    }

    /**
     * 窗体中展示的内容
     */
    override fun createComponent(): JComponent? {
        return settingUI
    }

    override fun isModified(): Boolean {
        return true
    }

    /**
     * 点击apply按钮时触发
     */
    override fun apply() {
        TODO("Not yet implemented")
    }

    /**
     * 显示名称
     */
    override fun getDisplayName(): String {
        return "Nacos"
    }


}
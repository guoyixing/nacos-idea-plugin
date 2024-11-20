package io.github.guoyixing.nacosideaplugin.ui.setting

import javax.swing.BoxLayout
import javax.swing.JPanel

/**
 * 设置界面
 */
class SettingUi : JPanel() {

    private val connectUI = SettingConnectUi()

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        add(connectUI)
    }

}
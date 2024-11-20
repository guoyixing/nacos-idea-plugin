package io.github.guoyixing.nacosideaplugin.ui.setting

import com.intellij.ui.TitledSeparator
import javax.swing.BoxLayout
import javax.swing.JPanel

/**
 * 设置连接界面
 *
 * @author 敲代码的旺财
 * @date 2024/11/20 00:59
 */
class SettingConnectUi : JPanel() {

    private val content = JPanel()

    private val connectTitledSeparator = TitledSeparator("Nacos")

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(connectTitledSeparator)

        initContent()
        add(content)

    }

    private fun initContent() {

    }
}
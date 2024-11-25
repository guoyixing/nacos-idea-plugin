package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.ui.components.JBList
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListCellRenderer

/**
 * nacos配置列表渲染
 *
 * @author 敲代码的旺财
 * @date 2024/11/25 23:06
 */
class ConfigurationListCellRenderer :ListCellRenderer<NacosConfigsResp>{

    override fun getListCellRendererComponent(
        list: JList<out NacosConfigsResp>?,
        value: NacosConfigsResp?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (list == null || value == null) {
            return JPanel().apply { add(JLabel("No data available")) }
        }
        val panel = JPanel(BorderLayout()).apply {
            accessibleContext.accessibleName = "Nacos Configuration Panel"
        }
        val label = JLabel(value.dataId)
        panel.add(label)
        if (isSelected) {
            panel.background = list.selectionBackground
            label.foreground = list.selectionForeground
        } else {
            panel.background = list.background
            label.foreground = list.foreground
        }
        return panel
    }

}
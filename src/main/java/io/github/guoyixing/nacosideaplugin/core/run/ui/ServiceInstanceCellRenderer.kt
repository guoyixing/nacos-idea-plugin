package io.github.guoyixing.nacosideaplugin.core.run.ui

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosServiceInstancesResp
import org.jdesktop.swingx.prompt.PromptSupport.setBackground
import org.jdesktop.swingx.prompt.PromptSupport.setForeground
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.*
import javax.swing.table.TableCellRenderer


/**
 * nacos服务列表渲染
 *
 * @author 郭一行
 * @date 2024/11/29 12:46
 */
class ServiceInstanceCellRenderer(
) : TableCellRenderer {
    private val button: JButton = JButton()

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        if (table == null || value == null || value !is NacosServiceInstancesResp.Host) {
            return button
        }

        val panel = JPanel(BorderLayout()).apply {
            accessibleContext.accessibleName = "Nacos Service Instance Panel"
        }
        button.text = value.enabled.let { if (it) "下线" else "上线" }
        panel.add(button, BorderLayout.CENTER)
        if (isSelected) {
            panel.background = table.selectionBackground
            button.foreground = table.selectionForeground
        } else {
            panel.background = table.background
            button.foreground = table.foreground
        }
        return panel
    }
}
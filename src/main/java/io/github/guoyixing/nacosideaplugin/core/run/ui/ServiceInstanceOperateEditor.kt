package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.codeInsight.options.JavaInspectionControls.button
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosServiceInstancesResp
import java.awt.Component
import java.awt.event.ActionListener
import javax.swing.*


/**
 * 服务实例操作编辑器
 *
 * @author 郭一行
 * @date 2024/11/29 16:29
 */
class ServiceInstanceOperateEditor:DefaultCellEditor(JTextField()) {

    private var data:NacosServiceInstancesResp.Host? = null

    init {
        setClickCountToStart(1);
    }

    override fun getTableCellEditorComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        row: Int,
        column: Int
    ): Component {
        if (value == null || value !is NacosServiceInstancesResp.Host) {
            return JPanel().apply { add(JLabel("Inoperable")) }
        }
        data = value
        val button = JButton()
        button.addActionListener {
            value.enabled = !value.enabled
            fireEditingStopped()
            //发送请求
        }

        button.text = value.enabled.let { if (it) "下线" else "上线" }
        return button
    }

    override fun getCellEditorValue(): Any {
        return data!!
    }

}
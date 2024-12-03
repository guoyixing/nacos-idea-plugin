package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.common.NotifyUtil
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosServiceInstancesResp
import java.awt.Component
import javax.swing.*


/**
 * 服务实例操作编辑器
 *
 * @author 郭一行
 * @date 2024/11/29 16:29
 */
class ServiceInstanceOperateEditor(
    private val project: Project,
    private val nacosClient: NacosClient
) :DefaultCellEditor(JTextField()) {

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
            fireEditingStopped()
            //发送请求
            value.enabled = !value.enabled
            if (!nacosClient.nsServiceInstances(value)) {
                //通知
                NotifyUtil.notify(project, "Nacos", "操作失败")
                value.enabled = !value.enabled
            }

        }

        button.text = value.enabled.let { if (it) "下线" else "上线" }
        return button
    }

    override fun getCellEditorValue(): Any {
        return data!!
    }

}
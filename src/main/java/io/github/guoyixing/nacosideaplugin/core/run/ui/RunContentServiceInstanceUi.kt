package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
import java.awt.BorderLayout
import javax.swing.JPanel


/**
 * 用来展示Nacos的服务内容
 *
 * @author 郭一行
 * @date 2024/11/28 13:22
 */
class RunContentServiceInstanceUi(
    private val project: Project,
    nacosClient: NacosClient
) : JPanel() {
    init {
        layout = BorderLayout()
        val serviceInstances = nacosClient.getServiceInstancesByApplication()


        val hosts = serviceInstances.hosts

        val data = Array(hosts.size) {
            arrayOf(
                hosts[it].ip,
                hosts[it].port,
                hosts[it].weight,
                hosts[it].healthy,
                hosts[it]
            )
        }

        val columnNames = arrayOf<Any>("IP", "Port", "Weight", "Healthy", "Operate")
        val model = ServiceInstanceTableModel(data, columnNames)

        val jbTable = JBTable()
        jbTable.setShowColumns(true)
        jbTable.model = model
        jbTable.columnModel.getColumn(4).cellRenderer = ServiceInstanceCellRenderer()
        jbTable.columnModel.getColumn(4).cellEditor = ServiceInstanceOperateEditor()

        val scrollPane = JBScrollPane(jbTable)
        add(scrollPane, BorderLayout.CENTER)
    }
}
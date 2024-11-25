package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.containers.toArray
import io.github.guoyixing.nacosideaplugin.nacos.config.NacosClient
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * 用来展示Nacos的配置列表
 *
 * @author 敲代码的旺财
 * @date 2024/11/25 20:58
 */
class RunContentConfigurationListUi(
    private val project: Project,
    private val nacosClient: NacosClient
) : JPanel() {

    init {
        layout = BorderLayout()
        val configs = nacosClient.getConfigs()

        val jbList = JBList(configs)
        jbList.cellRenderer = ConfigurationListCellRenderer()

        val jbScrollPane = JBScrollPane(jbList)


        add(jbScrollPane, BorderLayout.CENTER)
    }
}
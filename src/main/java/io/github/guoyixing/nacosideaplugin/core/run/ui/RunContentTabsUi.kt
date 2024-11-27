package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.TabInfo
import com.intellij.ui.tabs.impl.JBTabsImpl
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 *
 *
 * @author 郭一行
 * @date 2024/11/25 14:37
 */
class RunContentTabsUi(
    project: Project,
    nacosClient: NacosClient
) : JBTabsImpl(project) {

    init {
        layout = BorderLayout()


        val configurationTabInfo = TabInfo(RunContentConfigurationUi(project,nacosClient))
        configurationTabInfo.text = "配置"
        addTab(configurationTabInfo, 1)


//        val serviceTabInfo = TabInfo(JPanel())
//        serviceTabInfo.text = "服务"
//        addTab(serviceTabInfo, 2)
    }


}
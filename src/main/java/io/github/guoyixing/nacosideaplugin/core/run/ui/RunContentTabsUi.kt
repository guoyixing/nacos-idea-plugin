package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.TabInfo
import com.intellij.ui.tabs.impl.JBTabsImpl
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 *
 *
 * @author 郭一行
 * @date 2024/11/25 14:37
 */
class RunContentTabsUi(project: Project) : JBTabsImpl(project) {

    init {
        layout = BorderLayout()


        val configurationTabInfo = TabInfo(RunContentConfigurationUi(project))
        configurationTabInfo.text = "配置"
        addTab(configurationTabInfo, 1)


        val serviceTabInfo = TabInfo(JPanel())
        serviceTabInfo.text = "服务"
        addTab(serviceTabInfo, 2)
    }


}
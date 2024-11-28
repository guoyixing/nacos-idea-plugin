package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.tabs.JBTabsFactory
import com.intellij.ui.tabs.TabInfo
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


class RunContentUi(
    project: Project,
    nacosConfiguration: NacosConfiguration?
) : JPanel() {

    init {
        layout = BorderLayout()

        if (nacosConfiguration == null) {
            add(JLabel("无法识别到Nacos，请在设置中配置"))
        } else {
            val nacosClient = NacosClient(nacosConfiguration)

            val configurationTabInfo = TabInfo(RunContentConfigurationUi(project, nacosClient))
            configurationTabInfo.text = "配置"

            val serviceTabInfo = TabInfo(JPanel())
            serviceTabInfo.text = "服务"

            val jbTabs = JBTabsFactory.createTabs(project).also {
                it.addTab(configurationTabInfo, 1)
                it.addTab(serviceTabInfo, 2)
            }
            add(jbTabs as JComponent, BorderLayout.CENTER)
        }
    }
}
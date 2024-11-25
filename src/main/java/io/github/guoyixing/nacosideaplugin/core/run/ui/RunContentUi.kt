package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.nacos.config.NacosClient
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.JLabel
import javax.swing.JPanel


class RunContentUi(
    private val project: Project,
    private val nacosConfiguration: NacosConfiguration?
) : JPanel() {

    private var nacosClient:NacosClient? = null


    init {
        layout = BorderLayout()

        if (nacosConfiguration == null) {
            add(JLabel("无法识别到Nacos，请在设置中配置"))
        } else {
            nacosClient = NacosClient(nacosConfiguration)
            val configs = nacosClient?.getConfigs()
            add(RunContentTabsUi(project), BorderLayout.CENTER)
        }
    }
}
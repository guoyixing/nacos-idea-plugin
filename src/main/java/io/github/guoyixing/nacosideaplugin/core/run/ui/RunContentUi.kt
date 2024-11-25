package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.nacos.config.NacosClient
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import java.awt.BorderLayout
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
            add(RunContentTabsUi(project, nacosClient), BorderLayout.CENTER)
        }
    }
}
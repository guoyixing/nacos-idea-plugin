package io.github.guoyixing.nacosideaplugin.run.ui

import com.intellij.execution.configurations.ConfigurationType
import javax.swing.JLabel
import javax.swing.JPanel


class RunContentUi(
    private val runConfigurationType: ConfigurationType?
) : JPanel() {

    init {
        if (runConfigurationType != null && runConfigurationType.id != "Spring Boot") {
            add(JLabel("无法识别到Nacos，请在设置中配置"))
        }else{
            add(JLabel("Nacos"))
        }
    }
}
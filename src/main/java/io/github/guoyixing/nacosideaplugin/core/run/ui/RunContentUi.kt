package io.github.guoyixing.nacosideaplugin.core.run.ui

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import javax.swing.JLabel
import javax.swing.JPanel


class RunContentUi(
    private val nacosConfiguration: NacosConfiguration?
) : JPanel() {

    init {
        if (nacosConfiguration == null) {
            add(JLabel("无法识别到Nacos，请在设置中配置"))
        } else {
            add(JLabel(nacosConfiguration.toString()))
        }
    }
}
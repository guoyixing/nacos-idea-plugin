package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import io.github.guoyixing.nacosideaplugin.nacos.config.NacosClient
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 *
 *
 * @author 郭一行
 * @date 2024/11/25 15:43
 */
class RunContentConfigurationUi(
    private val project: Project,
    nacosClient: NacosClient
) :JPanel() {

    init {
        layout = BorderLayout()

        val onePixelSplitter = OnePixelSplitter(false, 0.2f)
        onePixelSplitter.firstComponent = RunContentConfigurationListUi(project,nacosClient)
        onePixelSplitter.secondComponent = RunContentConfigurationContentUi(project,nacosClient)

        add(onePixelSplitter, BorderLayout.CENTER)
    }


}
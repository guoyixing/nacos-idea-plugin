package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.common.NotifyUtil
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationActivity
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationContext
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

/**
 * 用来展示Nacos的配置内容
 *
 * @author 敲代码的旺财
 * @date 2024/11/25 21:49
 */
class RunContentConfigurationContentUi(
    private val project: Project,
    private val context: RunContentConfigurationContext,
    private val activity: RunContentConfigurationActivity
) : JPanel() {

    init {
        layout = BorderLayout()

        val editor = context.editor
        add(editor.component, BorderLayout.CENTER)
        val buttonPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        val saveButton = JButton("保存")
        saveButton.addActionListener(saveAction())
        val refreshButton = JButton("刷新")
        refreshButton.addActionListener(refreshAction())

        buttonPanel.add(refreshButton)
        buttonPanel.add(saveButton)
        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun saveAction(): ActionListener {
        return ActionListener {
            val selectConfiguration = context.selectConfiguration
            val configData = context.editor.document.text
            if (selectConfiguration != null) {
                context.nacosClient.updateConfigData(selectConfiguration, configData)
                NotifyUtil.notify(project, "Nacos", "保存成功")
            }
        }
    }

    private fun refreshAction(): ActionListener {
        return ActionListener {
            activity.updateEditor(context.selectConfiguration)
        }
    }
}
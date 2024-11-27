package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import io.github.guoyixing.nacosideaplugin.common.ButtonUtil
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationActivity
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationContext
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JPanel

/**
 * 用来展示Nacos的配置列表
 *
 * @author 敲代码的旺财
 * @date 2024/11/25 20:58
 */
class RunContentConfigurationListUi(
    private val project: Project,
    private val context: RunContentConfigurationContext,
    private val activity: RunContentConfigurationActivity
) : JPanel() {

    init {
        layout = BorderLayout()
        val configs = context.nacosClient.getConfigs()

        val jbList = JBList(configs)
        jbList.cellRenderer = ConfigurationListCellRenderer()
        jbList.addMouseListener()




        val jbScrollPane = JBScrollPane(jbList)

        val refreshButton = JButton("刷新")
        refreshButton.addActionListener(refreshAction(jbList))

        add(jbScrollPane, BorderLayout.CENTER)
        add(refreshButton, BorderLayout.SOUTH)
    }

    private fun JBList<NacosConfigsResp>.addMouseListener() {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (selectedValue == null) {
                    return
                }
                context.selectConfiguration = selectedValue
                activity.updateEditor(selectedValue)
            }
        })
    }

    private fun refreshAction(jbList:JBList<NacosConfigsResp>): ActionListener {
        return ActionListener {
            val configs = activity.getConfigs()
            jbList.model = DefaultListModel<NacosConfigsResp>().apply {
                configs.forEach { addElement(it) }
            }
        }
    }
}
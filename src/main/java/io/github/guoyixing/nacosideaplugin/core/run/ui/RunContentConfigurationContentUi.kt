package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.nacos.config.NacosClient
import java.awt.BorderLayout
import java.awt.FlowLayout
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
    private val nacosClient: NacosClient
) : JPanel() {

    init {
        layout = BorderLayout()

        val yamlFile = """
            server:
              port: 8080
            spring:
              application:
                name: nacos-idea-plugin
            nacos:
              config:
                server-addr: http://
        """.trimIndent()
        val editor = createEditorFromContent(yamlFile)
        add(editor.component, BorderLayout.CENTER)
        val buttonPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        buttonPanel.add(JButton("还原"))
        buttonPanel.add(JButton("保存"))
        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun createEditorFromContent(content: String): Editor {
        val document: Document = EditorFactory.getInstance().createDocument(content)
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("yaml")
        return EditorFactory.getInstance().createEditor(document, project, fileType, false)
    }
}
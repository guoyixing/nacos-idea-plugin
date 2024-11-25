package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JPanel

/**
 *
 *
 * @author 郭一行
 * @date 2024/11/25 15:43
 */
class RunContentConfigurationUi(
    private val project: Project
) :JPanel() {

    init {
        layout = BorderLayout()

        val jbList = JBList("auth.yaml", "gateway.yaml", "modules-job.yaml")

        val jbScrollPane = JBScrollPane(jbList)

        val onePixelSplitter = OnePixelSplitter(false, 0.2f)
        val jPanel = JPanel(BorderLayout())
        jPanel.add(jbScrollPane, BorderLayout.CENTER)

        onePixelSplitter.firstComponent = jPanel

        val jPanel2 = JPanel(BorderLayout())
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
        jPanel2.add(editor.component, BorderLayout.CENTER)
        val buttonPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        buttonPanel.add(JButton("还原"))
        buttonPanel.add(JButton("保存"))
        jPanel2.add(buttonPanel, BorderLayout.SOUTH)
        onePixelSplitter.secondComponent = jPanel2

        add(onePixelSplitter, BorderLayout.CENTER)
    }

    private fun createEditorFromContent(content: String): Editor {
        val document: Document = EditorFactory.getInstance().createDocument(content)
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("yaml") ?: PlainTextFileType.INSTANCE
        return EditorFactory.getInstance().createEditor(document, project, fileType, false)
    }
}
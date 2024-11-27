package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationActivity
import io.github.guoyixing.nacosideaplugin.core.run.RunContentConfigurationContext
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
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

    private val editor:Editor = createEditorFromContent()

    private val context: RunContentConfigurationContext = RunContentConfigurationContext(
        editor=editor,
        nacosClient=nacosClient
    )

    private val activity: RunContentConfigurationActivity = RunContentConfigurationActivity(project, context)

    init {
        layout = BorderLayout()

        val onePixelSplitter = OnePixelSplitter(false, 0.2f)
        onePixelSplitter.firstComponent = RunContentConfigurationListUi(project,context,activity)
        onePixelSplitter.secondComponent = RunContentConfigurationContentUi(project,context,activity)

        add(onePixelSplitter, BorderLayout.CENTER)
    }

    private fun createEditorFromContent(): Editor {
        val document: Document = EditorFactory.getInstance().createDocument("")
        return EditorFactory.getInstance().createEditor(document, project, PlainTextFileType.INSTANCE, false)
    }

}
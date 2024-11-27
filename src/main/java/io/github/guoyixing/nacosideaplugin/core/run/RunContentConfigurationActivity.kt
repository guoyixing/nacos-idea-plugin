package io.github.guoyixing.nacosideaplugin.core.run

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp

/**
 *
 *
 * @author 郭一行
 * @date 2024/11/26 17:31
 */
class RunContentConfigurationActivity(
    private val project: Project,
    private val context: RunContentConfigurationContext,
) {

    fun updateEditor(config: NacosConfigsResp?){
        if (config == null) {
            return
        }
        val configData = context.nacosClient.getConfig(config)
        val type = config.type
        WriteAction.run<Throwable> {
            val normalizedConfigData = configData.replace("\r\n", "\n")
            context.editor.document.setText(normalizedConfigData)
        }
        if (context.editor is EditorEx) {
            val fileType = type?.let { FileTypeManager.getInstance().getFileTypeByExtension(type) }
            val editorHighlighter = EditorHighlighterFactory.getInstance()
            val highlighter = fileType?.let { editorHighlighter.createEditorHighlighter(project, it) }
                ?: editorHighlighter.createEditorHighlighter(project, PlainTextFileType.INSTANCE)
            context.editor.highlighter = highlighter
        }
    }

    fun getConfigs():List<NacosConfigsResp>{
        return context.nacosClient.getConfigs()
    }
}
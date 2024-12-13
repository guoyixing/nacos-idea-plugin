package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.AsyncFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import io.github.guoyixing.nacosideaplugin.nacos.YamlParser

/**
 * 添加监听器
 *
 * @author 敲代码的旺财
 * @date 2024/11/29 20:20
 */
class ProjectStructureAsyncFileListener(
    private val projectStructure: ProjectStructure
) : AsyncFileListener {

    override fun prepareChange(e: MutableList<out VFileEvent>): AsyncFileListener.ChangeApplier {
        return ChangeApplier(e, projectStructure)
    }

    class ChangeApplier(
        private val e: MutableList<out VFileEvent>,
        private val projectStructure: ProjectStructure
    ) : AsyncFileListener.ChangeApplier {

        override fun afterVfsChange() {
            e.forEach {
                projectStructure.moduleBootstrapPaths.forEach { (moduleName, path) ->
                    if (it.path.contains(path)) {
                        val yamlParser = YamlParser()
                        it.file?.let { file ->
                            runReadAction {
                                FileDocumentManager.getInstance().getDocument(file)?.let {
                                    val nacosConfiguration = yamlParser.parser(it,projectStructure.project,moduleName)
                                    projectStructure.moduleBootstrap[moduleName] = nacosConfiguration
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
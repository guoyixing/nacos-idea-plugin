package io.github.guoyixing.nacosideaplugin

import com.intellij.execution.ui.RunContentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.util.messages.MessageBusConnection
import io.github.guoyixing.nacosideaplugin.core.run.RunContentListener


/**
 * 启动时执行，将页签添加到idea中的RunPlugin中
 *
 * @author 敲代码的旺财
 * @date 2024/11/20 20:52
 */
class ProjectActivity : ProjectActivity {

    /**
     * 启动时执行
     */
    override suspend fun execute(project: Project) {
        //分析项目的结构
        ProjectStructureManager.init(project)
        if (ProjectStructureManager.isMaven(project)) {
            ProjectStructureManager.getMavenModules(project)
            ProjectStructureManager.getBootstrapPath(project)
            ProjectStructureManager.getBootstrap(project)
            val connection: MessageBusConnection = project.messageBus.connect()
            connection.subscribe(RunContentManager.TOPIC, RunContentListener(project))
        }
    }
}
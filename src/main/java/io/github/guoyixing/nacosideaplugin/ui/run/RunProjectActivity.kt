package io.github.guoyixing.nacosideaplugin.ui.run

import com.intellij.execution.ui.RunContentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.util.messages.MessageBusConnection


/**
 * 启动时执行，将页签添加到idea中的RunPlugin中
 *
 * @author 敲代码的旺财
 * @date 2024/11/20 20:52
 */
class RunProjectActivity : ProjectActivity {

    /**
     * 启动时执行
     */
    override suspend fun execute(project: Project) {
        val connection: MessageBusConnection = project.messageBus.connect()
        connection.subscribe(RunContentManager.TOPIC, RunContentListener())
    }
}
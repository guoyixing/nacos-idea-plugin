package io.github.guoyixing.nacosideaplugin.core.run

import com.intellij.execution.Executor
import com.intellij.execution.application.ApplicationConfiguration
import com.intellij.execution.impl.ExecutionManagerImpl
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.execution.ui.RunContentWithExecutorListener
import com.intellij.openapi.project.Project
import com.intellij.ui.content.ContentFactory
import io.github.guoyixing.nacosideaplugin.ProjectStructureManager
import io.github.guoyixing.nacosideaplugin.core.run.ui.RunContentUi

/**
 * RunPlugin展示的时候添加标签
 *
 * @author 敲代码的旺财
 * @date 2024/11/20 20:52
 */
class RunContentListener(
    private val project: Project
) : RunContentWithExecutorListener {

    /**
     * 当RunPlugin被选中时执行的逻辑
     */
    override fun contentSelected(descriptor: RunContentDescriptor?, executor: Executor) {


        descriptor?.runnerLayoutUi?.contentManager?.let {
            val executionManager = ExecutionManagerImpl.getInstance(project)
            val executionEnvironments = executionManager.getExecutionEnvironments(descriptor)
            executionEnvironments.forEach{ env ->
                val moduleName =
                    (env.runnerAndConfigurationSettings?.configuration as ApplicationConfiguration).configurationModule.moduleName
                val nacosConfiguration = ProjectStructureManager.moduleBootstrap[moduleName]
                // 创建新的 Content 实例
                val content = ContentFactory.getInstance().createContent(RunContentUi(project,nacosConfiguration), "Nacos", true)
                content.isCloseable = false
                // 将新的 Content 添加到 ContentManager
                it.addContent(content)
            }
        }
    }

    /**
     * 当RunPlugin被移除时执行的逻辑
     */
    override fun contentRemoved(descriptor: RunContentDescriptor?, executor: Executor) {
    }

}
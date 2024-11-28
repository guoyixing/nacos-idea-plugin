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

    private var runContentUiMap: MutableMap<String, RunContentUi> = mutableMapOf()

    /**
     * 当RunPlugin被选中时执行的逻辑
     */
    override fun contentSelected(descriptor: RunContentDescriptor?, executor: Executor) {
        try {
            descriptor?.runnerLayoutUi?.contentManager?.let {
                val executionManager = ExecutionManagerImpl.getInstance(project)
                val executionEnvironments = executionManager.getExecutionEnvironments(descriptor)
                executionEnvironments.forEach { env ->
                    val moduleName =
                        (env.runnerAndConfigurationSettings?.configuration as ApplicationConfiguration).configurationModule.moduleName
                    val nacosConfiguration = ProjectStructureManager.projects[project]!!.moduleBootstrap[moduleName]
                    if (nacosConfiguration != null && runContentUiMap[moduleName] == null) {
                        // 创建新的 Content 实例
                        val runContentUi = RunContentUi(project, nacosConfiguration)
                        runContentUiMap[moduleName] = runContentUi

                        val content = ContentFactory.getInstance().createContent(runContentUi, "Nacos", true)
                        content.isCloseable = false
                        // 将新的 Content 添加到 ContentManager
                        it.addContent(content)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 当RunPlugin被移除时执行的逻辑
     */
    override fun contentRemoved(descriptor: RunContentDescriptor?, executor: Executor) {
        descriptor?.runnerLayoutUi?.contentManager?.let {
            val executionManager = ExecutionManagerImpl.getInstance(project)
            val executionEnvironments = executionManager.getExecutionEnvironments(descriptor)
            executionEnvironments.forEach { env ->
                val moduleName =
                    (env.runnerAndConfigurationSettings?.configuration as ApplicationConfiguration).configurationModule.moduleName
                runContentUiMap.remove(moduleName)
            }
        }
    }

}
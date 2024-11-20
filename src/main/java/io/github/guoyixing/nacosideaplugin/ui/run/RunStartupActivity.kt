package io.github.guoyixing.nacosideaplugin.ui.run

import com.intellij.execution.ui.RunContentManager
import com.intellij.execution.ui.layout.impl.RunnerContentUi
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.ui.content.ContentFactory
import javax.swing.JLabel
import javax.swing.JPanel


/**
 * 启动时执行，将页签添加到idea中的RunPlugin中
 *
 * @author 敲代码的旺财
 * @date 2024/11/20 20:52
 */
class RunStartupActivity : StartupActivity{

    /**
     * 启动时执行
     */
    override fun runActivity(project: Project) {
        // 获取 RunContentManager 实例
        val runContentManager = RunContentManager.getInstance(project)

        // 获取 ContentFactory 实例
        val contentFactory = ContentFactory.getInstance()

        runContentManager.allDescriptors.forEach {

            val runnerLayoutUi = it.runnerLayoutUi

            if (runnerLayoutUi != null) {
                // 获取 RunnerContentUi 实例
                val newTabPanel = JPanel()
                newTabPanel.add(JLabel("This is a new tab"))

                // 创建新的 Content 实例

                val newContent = contentFactory.createContent(newTabPanel, "New Tab", false)

                // 将新的 Content 添加到 ContentManager
                runnerLayoutUi.contentManager.addContent(newContent)
            }
        }
    }
}
package io.github.guoyixing.nacosideaplugin.ui.run

import com.intellij.execution.Executor
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.execution.ui.RunContentManager
import com.intellij.execution.ui.RunContentWithExecutorListener
import com.intellij.ui.content.ContentFactory
import org.jetbrains.annotations.Nullable
import javax.swing.JLabel
import javax.swing.JPanel

class RunContentListener :RunContentWithExecutorListener {
    override fun contentSelected(descriptor: RunContentDescriptor?, executor: Executor) {
        // 当运行内容被选中时执行的逻辑
        println("Run content selected: ${descriptor?.displayName}")
//        // 获取 RunContentManager 实例
//        val runContentManager = RunContentManager.getInstance(project)
//
//        // 获取 ContentFactory 实例
//        val contentFactory = ContentFactory.getInstance()
//
//        runContentManager.allDescriptors.forEach {
//
//            val runnerLayoutUi = it.runnerLayoutUi
//
//            if (runnerLayoutUi != null) {
//                // 获取 RunnerContentUi 实例
//                val newTabPanel = JPanel()
//                newTabPanel.add(JLabel("This is a new tab"))
//
//                // 创建新的 Content 实例
//
//                val newContent = contentFactory.createContent(newTabPanel, "New Tab", false)
//
//                // 将新的 Content 添加到 ContentManager
//                runnerLayoutUi.contentManager.addContent(newContent)
//            }
//        }
    }

    override fun contentRemoved(descriptor: RunContentDescriptor?, executor: Executor) {
        // 当运行内容被移除时执行的逻辑
        println("Run content removed: ${descriptor?.displayName}")
    }

}
package io.github.guoyixing.nacosideaplugin.core.run.ui

import com.intellij.ui.components.JBList
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * 用来展示Nacos的服务内容
 *
 * @author 郭一行
 * @date 2024/11/28 13:22
 */
class RunContentServiceInstanceUi :JPanel(){
    init {
        layout = BorderLayout()


        val jbList = JBList("111")

        add(jbList, BorderLayout.CENTER)
    }
}
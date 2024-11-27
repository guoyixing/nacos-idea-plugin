package io.github.guoyixing.nacosideaplugin.common

import com.intellij.ui.JBColor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton

object ButtonUtil {

    fun setClickEffect(button: JButton){
        button.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                // 改变按钮的背景颜色以显示按下效果
                button.background = JBColor.BLUE
            }

            override fun mouseReleased(e: MouseEvent?) {
                // 恢复按钮的背景颜色以显示释放效果
                button.background = JBColor.LIGHT_GRAY
            }
        })
    }

}
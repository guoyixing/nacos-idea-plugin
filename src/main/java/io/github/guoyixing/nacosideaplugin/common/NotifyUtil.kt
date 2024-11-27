package io.github.guoyixing.nacosideaplugin.common

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

/**
 * 通知工具类
 *
 * @author 郭一行
 * @date 2024/11/26 16:50
 */
object NotifyUtil {
    fun notify(project: Project,group:String, content: String) {
        val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup(group)
        val notification = notificationGroup.createNotification(content, NotificationType.INFORMATION)
        notification.notify(project)
    }
}
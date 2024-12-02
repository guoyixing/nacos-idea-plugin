package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project


/**
 * 用来释放资源
 *
 * @author 敲代码的旺财
 * @date 2024/11/29 20:52
 */
@Service(Service.Level.APP, Service.Level.PROJECT)
object NacosPluginDisposable: Disposable {

    fun getInstance(): Disposable {
        return ApplicationManager.getApplication().getService(NacosPluginDisposable::class.java)
    }

    fun getInstance(project: Project): Disposable {
        return project.getService(NacosPluginDisposable::class.java)
    }

    override fun dispose() {
        // do something
    }
}
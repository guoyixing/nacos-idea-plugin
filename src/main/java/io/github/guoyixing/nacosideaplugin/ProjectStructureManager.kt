package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import io.github.guoyixing.nacosideaplugin.nacos.YamlParser
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import org.jetbrains.idea.maven.project.MavenProjectsManager
import java.io.File

/**
 * 用来分析项目结构
 */
object ProjectStructureManager {
    var project: Project? = null

    var mavenProjectsManager: MavenProjectsManager? = null

    var modulePaths: MutableMap<String, String> = mutableMapOf()

    var moduleBootstrapPaths: MutableMap<String, String> = mutableMapOf()

    var moduleBootstrap: MutableMap<String, NacosConfiguration> = mutableMapOf()

    fun init(project: Project) {
        this.project = project
        mavenProjectsManager = MavenProjectsManager.getInstance(project)
    }

    /**
     * 判断是否是Maven项目
     */
    fun isMaven(): Boolean {
        return mavenProjectsManager?.isMavenizedProject == true
    }

    /**
     * 获取Maven项目的模块
     */
    fun getMavenModules() {
        mavenProjectsManager?.projects?.forEach {
            val pathIndex = it.file.path.indexOfLast { it == '/' }
            val modulePath = it.file.path.substring(0, pathIndex)
            modulePaths[it.displayName] = modulePath
        }
    }

    /**
     * 获取bootstrap的位置
     */
    fun getBootstrapPath() {
        modulePaths.forEach { (moduleName, modulePath) ->
            val bootstrapPath = "$modulePath/src/main/resources/bootstrap.yml"
            moduleBootstrapPaths[moduleName] = bootstrapPath
        }
    }

    fun getBootstrap() {
        moduleBootstrapPaths.forEach { (moduleName, bootstrapPath) ->
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(bootstrapPath), true)

            val yamlParser = YamlParser()
            virtualFile?.let {
                runReadAction {
                    val document = FileDocumentManager.getInstance().getDocument(virtualFile)
                    document?.let {
                        val nacosConfiguration = yamlParser.parser(document)
                        moduleBootstrap[moduleName] = nacosConfiguration
                        println(nacosConfiguration)
                    }
                }
            }
        }
    }


}
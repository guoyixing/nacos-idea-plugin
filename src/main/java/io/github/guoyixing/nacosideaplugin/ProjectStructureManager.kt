package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import io.github.guoyixing.nacosideaplugin.nacos.YamlParser
import org.jetbrains.idea.maven.project.MavenProjectsManager
import java.io.File


/**
 * 用来分析项目结构
 */
object ProjectStructureManager {

    val projects: MutableMap<Project, ProjectStructure> = mutableMapOf()

    fun init(project: Project) {
        ProjectStructure(
            mavenProjectsManager = MavenProjectsManager.getInstance(project)
        ).let {
            projects[project] = it
            VirtualFileManager.getInstance()
                .addAsyncFileListener(ProjectStructureAsyncFileListener(it), NacosPluginDisposable.getInstance(project))
        }

    }

    /**
     * 判断是否是Maven项目
     */
    fun isMaven(project: Project): Boolean {
        return MavenProjectsManager.getInstance(project).isMavenizedProject
    }

    /**
     * 获取Maven项目的模块
     */
    fun getMavenModules(project: Project) {
        MavenProjectsManager.getInstance(project).projects.forEach {
            val pathIndex = it.file.path.indexOfLast { it == '/' }
            val modulePath = it.file.path.substring(0, pathIndex)
            projects[project]!!.modulePaths[it.displayName] = modulePath
        }
    }

    /**
     * 获取bootstrap的位置
     */
    fun getBootstrapPath(project: Project) {
        projects[project]!!.modulePaths.forEach { (moduleName, modulePath) ->
            val bootstrapPath = "$modulePath/src/main/resources/bootstrap.yml"
            projects[project]!!.moduleBootstrapPaths[moduleName] = bootstrapPath
        }
    }

    fun getBootstrap(project: Project) {
        projects[project]!!.moduleBootstrapPaths.forEach { (moduleName, bootstrapPath) ->
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(bootstrapPath), true)

            val yamlParser = YamlParser()
            virtualFile?.let {
                runReadAction {
                    val document = FileDocumentManager.getInstance().getDocument(virtualFile)
                    document?.let {
                        val nacosConfiguration = yamlParser.parser(document)
                        projects[project]!!.moduleBootstrap[moduleName] = nacosConfiguration
                    }
                }
            }
        }
    }


}
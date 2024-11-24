package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import org.gradle.internal.impldep.org.testng.internal.YamlParser
import org.jetbrains.idea.maven.project.MavenProjectsManager
import org.jetbrains.yaml.YAMLUtil
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream

/**
 * 用来分析项目结构
 */
class ProjectStructureManager(
    private val project: Project
) {
    private val mavenProjectsManager = MavenProjectsManager.getInstance(project)

    private val modulePaths: MutableMap<String,String> = mutableMapOf()

    private val moduleBootstrapPaths: MutableMap<String,String> = mutableMapOf()


    /**
     * 判断是否是Maven项目
     */
    fun isMaven(): Boolean {
        return mavenProjectsManager != null && mavenProjectsManager.isMavenizedProject
    }

    /**
     * 获取Maven项目的模块
     */
    fun getMavenModules(){
        mavenProjectsManager.projects.forEach {
            val pathIndex = it.file.path.indexOfLast { it == '/' }
            val modulePath = it.file.path.substring(0,pathIndex)
            modulePaths[it.displayName] = modulePath

            println(it.displayName)
            println(modulePath)
        }
    }

    /**
     * 获取bootstrap的位置
     */
    fun getBootstrapPath() {
        modulePaths.forEach { (moduleName, modulePath) ->
            val bootstrapPath = "$modulePath/src/main/resources/bootstrap.yml"
            moduleBootstrapPaths[moduleName] = bootstrapPath


            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(java.io.File(bootstrapPath), true)
            val yaml = Yaml()
            val file = File(bootstrapPath)
            if(file.exists()) {


                val bootstrap = yaml.load<Map<String, Any>>(file.inputStream())

                println(bootstrap)
            }
        }
    }


}
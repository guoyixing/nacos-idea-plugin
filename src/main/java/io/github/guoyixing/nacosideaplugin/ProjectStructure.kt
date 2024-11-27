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
data class ProjectStructure (
    val mavenProjectsManager: MavenProjectsManager,

    val modulePaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrapPaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrap: MutableMap<String, NacosConfiguration> = mutableMapOf(),

)
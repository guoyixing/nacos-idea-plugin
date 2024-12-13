package io.github.guoyixing.nacosideaplugin

import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import org.jetbrains.idea.maven.project.MavenProjectsManager

/**
 * 用来分析项目结构
 */
data class ProjectStructure(
    val project : Project,

    val mavenProjectsManager: MavenProjectsManager,

    val modulePaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrapPaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrap: MutableMap<String, NacosConfiguration> = mutableMapOf(),

    val moduleNacosVersion: MutableMap<String, String> = mutableMapOf()

)
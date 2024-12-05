package io.github.guoyixing.nacosideaplugin

import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import org.jetbrains.idea.maven.project.MavenProjectsManager

/**
 * 用来分析项目结构
 */
data class ProjectStructure(
    val mavenProjectsManager: MavenProjectsManager,

    val modulePaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrapPaths: MutableMap<String, String> = mutableMapOf(),

    val moduleBootstrap: MutableMap<String, NacosConfiguration> = mutableMapOf(),

    )
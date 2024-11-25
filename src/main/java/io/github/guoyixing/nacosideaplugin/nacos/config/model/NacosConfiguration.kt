package io.github.guoyixing.nacosideaplugin.nacos.config.model

/**
 * Nacos配置
 */
data class NacosConfiguration(
    val applicationName: String,
    val discoveryServer: String,
    val configServer: String,
    val namespaceId: String = "public",
    val userName: String = "nacos",
    val password: String = "nacos"
)
package io.github.guoyixing.nacosideaplugin.nacos.config.model

/**
 * Nacos配置
 */
class NacosConfiguration(
    private val applicationName: String,
    private val discoveryServer: String,
    private val configServer: String,
    private val userName: String = "nacos",
    private val password: String = "nacos"
){
    override fun toString(): String {
        return "NacosConfiguration(applicationName='$applicationName', discoveryServer='$discoveryServer', configServer='$configServer')"
    }
}
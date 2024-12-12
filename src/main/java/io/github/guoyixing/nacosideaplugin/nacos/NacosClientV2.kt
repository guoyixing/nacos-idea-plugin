package io.github.guoyixing.nacosideaplugin.nacos

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosBaseResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosServiceResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

/**
 * Nacos客户端
 *
 * @author 敲代码的旺财
 * @date 2024/11/24 21:51
 */
class NacosClientV2(
    override val nacosConfiguration: NacosConfiguration
) : NacosClient {

    override var accessToken: String? = null

    override var ttl: Long = 18000

    override var tokenGenerationTime: Long? = null

    override val json = Json {
        ignoreUnknownKeys = true
    }

    override fun getConfigList(): List<NacosConfigsResp> {
        val config = nacosConfiguration.spring.cloud.nacos.config
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${config.serverAddr}/nacos/v2/cs/history/configs") {
                    parameter("namespaceId", config.namespace)
                    if (!config.username.isNullOrBlank() && !config.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<List<NacosConfigsResp>>>(resp.bodyAsText())
                configsResp.data.filter {
                    config.prefix?.let { prefix -> prefix.isNotBlank() && it.dataId.startsWith(prefix) } == true
                            || it.dataId.startsWith(config.name)
                            || it.dataId.startsWith(nacosConfiguration.spring.application.name)
                            || config.extConfig.any { ext -> ext.dataId == it.dataId }
                            || config.extensionConfigs.any { ext -> ext.dataId == it.dataId }
                            || config.sharedConfigs.any { ext -> ext.dataId == it.dataId }
                            || config.sharedDataids?.contains(it.dataId) == true
                            || config.refreshableDataids?.contains(it.dataId) == true
                }
                configsResp.data
            }
        }
    }

    override fun getConfigData(config: NacosConfigsResp): String {
        val nacosConfig = nacosConfiguration.spring.cloud.nacos.config
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfig.serverAddr}/nacos/v2/cs/config") {
                    parameter("namespaceId", nacosConfig.namespace)
                    parameter("dataId", config.dataId)
                    parameter("group", config.group)
                    if (!nacosConfig.username.isNullOrBlank() && !nacosConfig.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<String>>(resp.bodyAsText())
                configsResp.data
            }
        }
    }

    override fun updateConfigData(config: NacosConfigsResp, content: String): Boolean {
        val nacosConfig = nacosConfiguration.spring.cloud.nacos.config
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.post("http://${nacosConfig.serverAddr}/nacos/v2/cs/config") {
                    parameter("namespaceId", nacosConfig.namespace)
                    parameter("dataId", config.dataId)
                    parameter("group", config.group)
                    parameter("content", content.replace("\n", "\r\n"))
                    parameter("type", config.type)
                    if (!nacosConfig.username.isNullOrBlank() && !nacosConfig.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<Boolean>>(resp.bodyAsText())
                configsResp.data
            }
        }
    }

    override fun getServiceByDefaultConfig(): NacosServiceResp {
        val config = nacosConfiguration.spring.cloud.nacos.config
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${config.serverAddr}/nacos/v2/ns/instance/list") {
                    parameter("namespaceId", config.namespace)
                    parameter("serviceName", config.name)
                    if (!config.username.isNullOrBlank() && !config.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }
                val servicesResp = json.decodeFromString<NacosBaseResp<NacosServiceResp>>(resp.bodyAsText())
                servicesResp.data.hosts.forEach {
                    it.groupName = servicesResp.data.groupName
                }

                servicesResp.data
            }
        }
    }

}
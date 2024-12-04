package io.github.guoyixing.nacosideaplugin.nacos

import io.github.guoyixing.nacosideaplugin.nacos.config.model.*
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
class NacosClient(
    private val nacosConfiguration: NacosConfiguration
) {

    private var accessToken: String? = null

    private var ttl: Long = 18000

    private var tokenGenerationTime: Long? = null

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun getAccessToken(): String {
        if (accessToken == null || System.currentTimeMillis() - tokenGenerationTime!! > ttl) {
            runBlocking {
                // 重新获取token
                HttpClient().use { client ->
                    val resp = client.post("http://${nacosConfiguration.discoveryServer}/nacos/v1/auth/login") {
                        parameter("username", nacosConfiguration.userName)
                        parameter("password", nacosConfiguration.password)
                    }

                    json.decodeFromString<NacosLoginResp>(resp.bodyAsText()).let {
                        ttl = it.tokenTtl
                        accessToken = it.accessToken
                        tokenGenerationTime = System.currentTimeMillis()
                    }
                }
            }
        }
        return accessToken!!
    }

    fun getConfigs(): List<NacosConfigsResp> {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfiguration.configServer}/nacos/v2/cs/history/configs") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<List<NacosConfigsResp>>>(resp.bodyAsText())
                configsResp.data.filter {
                    it.dataId.startsWith(nacosConfiguration.configPrefix ?: nacosConfiguration.applicationName)
                }

            }
        }
    }

    fun getConfig(config: NacosConfigsResp): String {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfiguration.configServer}/nacos/v2/cs/config") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("dataId", config.dataId)
                    parameter("group", config.group)
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<String>>(resp.bodyAsText())
                configsResp.data
            }
        }
    }

    fun updateConfig(config: NacosConfigsResp, content: String): Boolean {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.post("http://${nacosConfiguration.configServer}/nacos/v2/cs/config") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("dataId", config.dataId)
                    parameter("group", config.group)
                    parameter("content", content.replace("\n", "\r\n"))
                    parameter("type", config.type)
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                val configsResp = json.decodeFromString<NacosBaseResp<Boolean>>(resp.bodyAsText())
                configsResp.data
            }
        }
    }

    fun getServiceInstancesByApplication(): NacosServiceInstancesResp {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfiguration.discoveryServer}/nacos/v2/ns/instance/list") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("serviceName", nacosConfiguration.applicationName)
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                val servicesResp = json.decodeFromString<NacosBaseResp<NacosServiceInstancesResp>>(resp.bodyAsText())
                servicesResp.data.hosts.forEach {
                    it.groupName = servicesResp.data.groupName
                }

                servicesResp.data
            }
        }
    }

    fun nsCatalogInstancesByApplication(service: NacosServiceInstancesResp): List<NacosServiceInstancesResp.Host> {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfiguration.discoveryServer}/nacos/v1/ns/catalog/instances") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("serviceName", nacosConfiguration.applicationName)
                    parameter("groupName", service.groupName)
                    parameter("pageSize", 200)
                    parameter("pageNo", 1)
                    parameter("clusterName", service.clusters.isBlank().let { "DEFAULT" })
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                val bodyAsText = resp.bodyAsText()
                println(bodyAsText)
                if(bodyAsText.startsWith("{")){
                    val servicesResp = json.decodeFromString<NacosServiceInstancesResp.Catalog>(bodyAsText)
                    servicesResp.list.forEach {
                        it.groupName = service.groupName
                    }
                    servicesResp.list
                }else{
                    emptyList()
                }

            }
        }
    }

    fun nsServiceInstances(host: NacosServiceInstancesResp.Host): Boolean {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.put("http://${nacosConfiguration.discoveryServer}/nacos/v1/ns/instance") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("serviceName", nacosConfiguration.applicationName)
                    parameter("ip", host.ip)
                    parameter("port", host.port)
                    parameter("clusterName", host.clusterName.isBlank().let { "DEFAULT" })
                    parameter("ephemeral", host.ephemeral)
                    parameter("weight", host.weight)
                    parameter("enabled", host.enabled)
                    parameter("groupName", host.groupName)
                    if (nacosConfiguration.auth) {
                        parameter("accessToken", getAccessToken())
                    }
                }
                resp.bodyAsText() == "ok"
            }
        }
    }
}
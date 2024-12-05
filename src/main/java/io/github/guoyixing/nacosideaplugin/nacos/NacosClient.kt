package io.github.guoyixing.nacosideaplugin.nacos

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosLoginResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosServiceResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

/**
 * Nacos客户端-接口
 *
 * @author 郭一行
 * @date 2024/12/5 13:23
 */
interface NacosClient {

    /**
     * nacos配置
     */
    val nacosConfiguration: NacosConfiguration

    /**
     * token
     */
    var accessToken: String?

    /**
     * token生成时间
     */
    var tokenGenerationTime: Long?

    /**
     * token有效时间
     */
    var ttl: Long

    /**
     * json解析
     */
    val json: Json

    /**
     * 获取token
     */
    fun fetchAccessToken(): String {
        if (accessToken.isNullOrBlank() || System.currentTimeMillis() - tokenGenerationTime!! > ttl) {
            val discovery = nacosConfiguration.spring.application.nacos.discovery
            runBlocking {
                // 重新获取token
                HttpClient().use { client ->
                    val resp = client.post("http://${discovery.serverAddr}/nacos/v1/auth/login") {
                        parameter("username", discovery.username)
                        parameter("password", discovery.password)
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

    fun updateInstance(host: NacosServiceResp.Host): Boolean {
        val config = nacosConfiguration.spring.application.nacos.config
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.put("http://${config.serverAddr}/nacos/v1/ns/instance") {
                    parameter("namespaceId", config.namespace)
                    parameter("serviceName", config.name)
                    parameter("ip", host.ip)
                    parameter("port", host.port)
                    parameter("clusterName", host.clusterName.isBlank().let { "DEFAULT" })
                    parameter("ephemeral", host.ephemeral)
                    parameter("weight", host.weight)
                    parameter("enabled", host.enabled)
                    parameter("groupName", host.groupName)
                    if (!config.username.isNullOrBlank() && !config.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }
                resp.bodyAsText() == "ok"
            }
        }
    }

    fun getServiceInstanceList(service: NacosServiceResp): List<NacosServiceResp.Host> {
        val discovery = nacosConfiguration.spring.application.nacos.discovery
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${discovery.serverAddr}/nacos/v1/ns/catalog/instances") {
                    parameter("namespaceId", discovery.namespace)
                    parameter("serviceName", discovery.service)
                    parameter("groupName", service.groupName)
                    parameter("pageSize", 200)
                    parameter("pageNo", 1)
                    parameter("clusterName", service.clusters.isBlank().let { "DEFAULT" })
                    if (!discovery.username.isNullOrBlank() && !discovery.password.isNullOrBlank()) {
                        parameter("accessToken", fetchAccessToken())
                    }
                }

                val respBody = resp.bodyAsText()
                if (respBody.startsWith("{")) {
                    val servicesResp = json.decodeFromString<NacosServiceResp.InstanceList>(respBody)
                    servicesResp.list.forEach {
                        it.groupName = service.groupName
                    }
                    servicesResp.list
                } else {
                    emptyList()
                }

            }
        }
    }

    /**
     * 获取配置列表
     * @return 配置列表
     */
    fun getConfigList(): List<NacosConfigsResp>

    /**
     * 获取配置数据
     * @param config 配置
     * @return 配置数据
     */
    fun getConfigData(config: NacosConfigsResp): String

    /**
     * 更新配置数据
     * @param config 配置
     * @param content 配置数据
     * @return 是否更新成功
     */
    fun updateConfigData(config: NacosConfigsResp, content: String): Boolean

    /**
     * 获取服务信息
     * @return 服务信息
     */
    fun getServiceByDefaultConfig(): NacosServiceResp
}
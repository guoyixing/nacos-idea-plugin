package io.github.guoyixing.nacosideaplugin.nacos

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosBaseResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosLoginResp
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
                    println(resp.bodyAsText())
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

    fun getConfig(config:NacosConfigsResp):String {
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

    fun updateConfig(config:NacosConfigsResp, content:String):Boolean {
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

}
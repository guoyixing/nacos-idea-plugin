package io.github.guoyixing.nacosideaplugin.nacos.config

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

    fun getConfigs(): NacosBaseResp<List<NacosConfigsResp>> {
        return runBlocking {
            HttpClient().use { client ->
                val resp = client.get("http://${nacosConfiguration.configServer}/nacos/v2/cs/history/configs") {
                    parameter("namespaceId", nacosConfiguration.namespaceId)
                    parameter("accessToken", getAccessToken())
                }
                json.decodeFromString<NacosBaseResp<List<NacosConfigsResp>>>(resp.bodyAsText())
            }
        }

    }

}
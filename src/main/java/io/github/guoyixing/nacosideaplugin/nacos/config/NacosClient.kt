package io.github.guoyixing.nacosideaplugin.nacos.config

import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration

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

    fun getAccessToken(): String {
        if (accessToken == null || System.currentTimeMillis() - tokenGenerationTime!! > ttl) {
            // 重新获取token
        }
        return accessToken!!
    }


}
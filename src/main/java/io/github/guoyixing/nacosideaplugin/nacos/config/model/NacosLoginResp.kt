package io.github.guoyixing.nacosideaplugin.nacos.config.model

import kotlinx.serialization.Serializable

/**
 * 登录返回
 *
 * @author 郭一行
 * @date 2024/11/25 13:01
 */
@Serializable
data class NacosLoginResp(
    val accessToken: String,

    val tokenTtl: Long
)
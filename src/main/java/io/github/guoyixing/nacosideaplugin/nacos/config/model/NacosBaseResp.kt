package io.github.guoyixing.nacosideaplugin.nacos.config.model

import kotlinx.serialization.Serializable

/**
 * Nacos基础返回
 *
 * @author 郭一行
 * @date 2024/11/25 14:05
 */
@Serializable
data class NacosBaseResp<T>(
    val code: Int,
    val message: String,
    val data: T
)
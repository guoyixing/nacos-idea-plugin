package io.github.guoyixing.nacosideaplugin.nacos.config.model

import kotlinx.serialization.Serializable

/**
 * Nacos配置返回
 *
 * @author 郭一行
 * @date 2024/11/25 14:05
 */
@Serializable
data class NacosConfigsResp(
    val id: String,

    val dataId: String,

    val group: String?,

    val content: String?,

    val md5: String?,

    val encryptedDataKey: String?,

    val tenant: String?,

    val appName: String?,

    val type: String?,

    val lastModified: Long
)

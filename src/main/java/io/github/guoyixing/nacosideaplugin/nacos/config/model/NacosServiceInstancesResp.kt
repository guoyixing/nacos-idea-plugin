package io.github.guoyixing.nacosideaplugin.nacos.config.model

import kotlinx.serialization.Serializable

/**
 * Nacos服务实例返回
 *
 * @author 郭一行
 * @date 2024/11/28 13:43
 */
@Serializable
data class NacosServiceInstancesResp(
    val name: String,
    val groupName: String,
    val clusters : String?,
    val cacheMillis : Int,
    val lastRefTime : Int,
    val checksum : Int,
    val allIPs : Boolean,
    val reachProtectionThreshold : Boolean,
    val valid : Boolean,

)
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
    val lastRefTime : Long,
    val checksum : String?,
    val allIPs : Boolean,
    val reachProtectionThreshold : Boolean,
    val valid : Boolean,
    val hosts : List<Host>

){
    @Serializable
    data class Host(
        val ip : String,
        val port : Int,
        val weight : Double,
        val healthy : Boolean,
        var enabled : Boolean,
        val ephemeral : Boolean,
        val clusterName : String,
        val serviceName : String,
        val metadata : Map<String, String>,
        val instanceHeartBeatTimeOut : Int,
        val ipDeleteTimeout : Int,
        val instanceHeartBeatInterval : Int
    )
}
package io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration

/**
 * Nacos配置
 *
 * @author 郭一行
 * @date 2024/12/2 13:13
 */
data class NacosConfiguration(
    val spring: Spring
) {
    data class Discovery(
        /**
         * 服务器地址
         */
        var serverAddr: String,

        /**
         * 服务名称
         */
        var service: String? = null,

        /**
         * 权重
         */
        var weight: Double = 1.0,

        /**
         * 网卡名称
         */
        var networkInterface: String? = null,

        /**
         * 注册IP地址
         */
        var ip: String? = null,

        /**
         * 注册IP类型
         */
        var ipType: String = "IPv4",

        /**
         * 注册端口
         */
        var port: Int = -1,

        /**
         * 命名空间
         */
        var namespace: String = "public",

        /**
         * 元数据
         */
        var metadata: String? = null,

        /**
         * 日志文件名
         */
        var logName: String? = null,

        /**
         * 集群名称
         */
        var clusterName: String = "DEFAULT",

        /**
         * 端点
         */
        var enpoint: String? = null,
    )

    data class Config(
        /**
         * 服务器地址
         */
        var serverAddr: String,

        /**
         *  nacos配置的dataId
         *  先取前缀，再去取名字，最后取applicationName
         */
        var name: String? = null,

        /**
         *  nacos配置的dataId前缀
         *  先取前缀，再去取名字，最后取
         */
        var prefix: String? = null,

        /**
         * 对nacos配置内容进行编码
         */
        var encode: String? = null,

        /**
         * nacos配置的GROUP
         */
        var group: String = "DEFAULT_GROUP",

        /**
         * nacos配置dataId的后缀，也是配置内容的文件扩展名
         * 目前支持properties或者yaml(yml)
         */
        var fileExtension: String = "properties",

        /**
         * 从 nacos 获取配置超时
         */
        var timeout: Long = 3000,

        /**
         * 端点
         */
        var endpoint: String? = null,

        /**
         * 命名空间
         */
        var namespace: String = "public",

        /**
         * 集群名
         */
        var clusterName: String? = null,

        /**
         * 共享配置的dataId
         * 以“，”分隔
         * 旧版
         */
        var sharedDataids: String? = null,

        /**
         * 共享配置的动态刷新dataId
         * 以“，”分隔
         * 旧版
         */
        var refreshableDataids: String? = null,

        /**
         * 共享配置
         * 新版
         */
        var sharedConfigs: List<ExtConfig> = emptyList(),

        /**
         * 扩展配置
         * 旧版
         */
        var extConfig: List<ExtConfig> = emptyList(),

        /**
         * 扩展配置
         * 新版
         */
        var extensionConfigs: List<ExtConfig> = emptyList()

    )

    data class ExtConfig(
        val dataId: String,
        val group: String,
        val refresh: Boolean
    )

    data class Spring(
        val application: Application
    )

    data class Application(
        val name: String,
        val nacos: Nacos
    )

    data class Nacos(
        val config: Config,
        val discovery: Discovery
    )


}
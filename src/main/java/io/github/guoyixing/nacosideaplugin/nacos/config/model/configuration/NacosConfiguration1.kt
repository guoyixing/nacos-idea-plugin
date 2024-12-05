package io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration

/**
 * Nacos配置
 *
 * @author 郭一行
 * @date 2024/12/2 13:13
 */
class NacosConfiguration1 {
    private var _spring: Spring = Spring()

    var spring: Spring
        get() = _spring
        set(varue) {
            _spring = varue
        }


    class Discovery {
        /**
         * 服务器地址
         */
        var serverAddr: String = ""

        /**
         * 用户名
         */
        var username: String? = null

        /**
         * 密码
         */
        var password: String? = null

        /**
         * 服务名称
         */
        var service: String? = null

        /**
         * 权重
         */
        var weight: Double = 1.0

        /**
         * 网卡名称
         */
        var networkInterface: String? = null

        /**
         * 注册IP地址
         */
        var ip: String? = null

        /**
         * 注册IP类型
         */
        var ipType: String = "IPv4"

        /**
         * 注册端口
         */
        var port: Int = -1

        /**
         * 命名空间
         */
        var namespace: String = "public"

        /**
         * 元数据
         */
        var metadata: String? = null

        /**
         * 日志文件名
         */
        var logName: String? = null

        /**
         * 集群名称
         */
        var clusterName: String = "DEFAULT"

        /**
         * 端点
         */
        var enpoint: String? = null
    }

    class Config {

        /**
         * 服务器地址
         */
        var serverAddr: String = ""

        /**
         * 用户名
         */
        var username: String? = null

        /**
         * 密码
         */
        var password: String? = null

        /**
         *  nacos配置的dataId
         *  先取前缀，再去取名字，最后取applicationName
         */
        var name: String = ""

        /**
         *  nacos配置的dataId前缀
         *  先取前缀，再去取名字，最后取
         */
        var prefix: String? = null

        /**
         * 对nacos配置内容进行编码
         */
        var encode: String? = null

        /**
         * nacos配置的GROUP
         */
        var group: String = "DEFAULT_GROUP"

        /**
         * nacos配置dataId的后缀，也是配置内容的文件扩展名
         * 目前支持properties或者yaml(yml)
         */
        var fileExtension: String = "properties"

        /**
         * 从 nacos 获取配置超时
         */
        var timeout: Long = 3000

        /**
         * 端点
         */
        var endpoint: String? = null

        /**
         * 命名空间
         */
        var namespace: String = "public"

        /**
         * 集群名
         */
        var clusterName: String? = null

        /**
         * 共享配置的dataId
         * 以“，”分隔
         * 旧版
         */
        var sharedDataids: String? = null

        /**
         * 共享配置的动态刷新dataId
         * 以“，”分隔
         * 旧版
         */
        var refreshableDataids: String? = null

        /**
         * 共享配置
         * 新版
         */
        var sharedConfigs: List<ExtConfig> = emptyList()

        /**
         * 扩展配置
         * 旧版
         */
        var extConfig: List<ExtConfig> = emptyList()

        /**
         * 扩展配置
         * 新版
         */
        var extensionConfigs: List<ExtConfig> = emptyList()

    }

    class ExtConfig {
        var dataId: String = ""
        var group: String = ""
        var refresh: Boolean = false
    }

    class Spring {
        var application: Application = Application()
    }

    class Application {
        var name: String = ""
        var nacos: Nacos = Nacos()
    }

    class Nacos {
        var config: Config = Config()
        var discovery: Discovery = Discovery()
    }


}
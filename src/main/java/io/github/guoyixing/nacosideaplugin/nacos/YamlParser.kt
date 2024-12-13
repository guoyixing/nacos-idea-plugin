package io.github.guoyixing.nacosideaplugin.nacos

import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import io.github.guoyixing.nacosideaplugin.ProjectStructureManager
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration.ExtConfig
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.constructor.SafeConstructor

/**
 * 用来解析Yaml文件
 *
 * @author 敲代码的旺财
 * @date 2024/11/24 21:01
 */
class YamlParser {


    fun parser(yaml: Document, project: Project, moduleName: String): NacosConfiguration {
        val discoveryDescriptor = TypeDescription(NacosConfiguration.Discovery::class.java).apply {
            substituteProperty("server-addr", String::class.java, "getServerAddr", "setServerAddr")
            substituteProperty("network-interface", String::class.java, "getNetworkInterface", "setNetworkInterface")
            substituteProperty("ip-type", String::class.java, "getIpType", "setIpType")
            substituteProperty("log-name", String::class.java, "getLogName", "setLogName")
            substituteProperty("cluster-name", String::class.java, "getClusterName", "setClusterName")
        }


        val configDescriptor = TypeDescription(NacosConfiguration.Config::class.java).apply {
            substituteProperty("server-addr", String::class.java, "getServerAddr", "setServerAddr")
            substituteProperty("file-extension", String::class.java, "getFileExtension", "setFileExtension")
            substituteProperty("cluster-name", String::class.java, "getClusterName", "setClusterName")
            substituteProperty("shared-dataids", String::class.java, "getSharedDataids", "setSharedDataids")
            substituteProperty("refreshable-dataids", String::class.java, "getRefreshableDataids", "setRefreshableDataids")
            substituteProperty("shared-configs", List::class.java, "getSharedConfigs", "setSharedConfigs")
            substituteProperty("ext-config", List::class.java, "getExtConfig", "setExtConfig")
            substituteProperty("extension-configs", List::class.java, "getExtensionConfigs", "setExtensionConfigs")
            addPropertyParameters("shared-configs", ExtConfig::class.java)
            addPropertyParameters("ext-config", ExtConfig::class.java)
            addPropertyParameters("extension-configs", ExtConfig::class.java)
            addPropertyParameters("sharedConfigs", ExtConfig::class.java)
            addPropertyParameters("extConfig", ExtConfig::class.java)
            addPropertyParameters("extensionConfigs", ExtConfig::class.java)
        }

        val extConfigDescriptor = TypeDescription(ExtConfig::class.java).apply {
            substituteProperty("data-id", String::class.java, "getDataId", "setDataId")
        }

        val options = LoaderOptions()
        val isSkipMissing = SafeConstructor(options).propertyUtils.apply {
            isSkipMissingProperties = true
        }
        val constructor = Constructor(NacosConfiguration::class.java, options).apply {
            propertyUtils = isSkipMissing
            addTypeDescription(discoveryDescriptor)
            addTypeDescription(configDescriptor)
            addTypeDescription(extConfigDescriptor)
        }

        val parser = Yaml(constructor);
        val configuration = parser.load<NacosConfiguration>(yaml.text)

        //设置默认值
        val discovery = configuration.spring.cloud.nacos.discovery
        discovery.namespace.isBlank().let { discovery.namespace = "public" }
        discovery.ipType.isBlank().let { discovery.ipType = "IPv4" }
        discovery.clusterName.isBlank().let { discovery.clusterName = "DEFAULT" }
        discovery.service.isNullOrBlank().let { discovery.service = configuration.spring.application.name }

        val config = configuration.spring.cloud.nacos.config
        config.namespace.isBlank().let { config.namespace = "public" }
        config.group.isBlank().let { config.group = "DEFAULT_GROUP" }
        config.fileExtension.isBlank().let { config.fileExtension = "properties" }
        config.name.isBlank().let { config.name = configuration.spring.application.name }

        val nacosVersion = ProjectStructureManager.projects[project]!!.moduleNacosVersion[moduleName]

        //2.3版本开始，不在需要用户名密码
        if (!(nacosVersion?.startsWith("2.") == true && nacosVersion.substring(2,3).toInt() > 3)) {
            discovery.username.isNullOrBlank().let { discovery.username = "nacos" }
            discovery.password.isNullOrBlank().let { discovery.password = "nacos" }
            config.username.isNullOrBlank().let { config.username = "nacos" }
            config.password.isNullOrBlank().let { config.password = "nacos" }
        }
        return configuration
    }

}
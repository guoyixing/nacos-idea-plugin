package io.github.guoyixing.nacosideaplugin.nacos

import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.openapi.editor.Document
import io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration.NacosConfiguration
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
/**
 * 用来解析Yaml文件
 *
 * @author 敲代码的旺财
 * @date 2024/11/24 21:01
 */
class YamlParser {

    private val parser = Yaml(Constructor(NacosConfiguration::class.java, LoaderOptions()));

    fun parser(yaml: Document): NacosConfiguration {
        val configuration = parser.load<NacosConfiguration>(yaml.text)

        val discovery = configuration.spring.application.nacos.discovery
        discovery.namespace.isBlank().let { discovery.namespace = "public" }
        discovery.ipType.isBlank().let { discovery.ipType = "IPv4" }
        discovery.clusterName.isBlank().let { discovery.clusterName = "DEFAULT" }
        discovery.service.isNullOrBlank().let { discovery.service = configuration.spring.application.name }

        val config = configuration.spring.application.nacos.config
        config.namespace.isBlank().let { config.namespace = "public" }
        config.group.isBlank().let { config.group = "DEFAULT_GROUP" }
        config.fileExtension.isBlank().let { config.fileExtension = "properties" }
        config.name.isNullOrBlank().let { config.name = configuration.spring.application.name }
        return configuration
    }

}
package io.github.guoyixing.nacosideaplugin.nacos.config

import com.intellij.openapi.editor.Document
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfiguration
import org.yaml.snakeyaml.Yaml

/**
 * 用来解析Yaml文件
 *
 * @author 敲代码的旺财
 * @date 2024/11/24 21:01
 */
class YamlParser {

    private val parser = Yaml()

    fun parser(yaml: Document): NacosConfiguration {
        val bootstrap = parser.load<Map<String, Any>>(yaml.text)
        val spring = bootstrap["spring"] as Map<*, *>

        val application = spring["application"] as Map<*, *>
        val applicationName = application["name"] as String

        val cloud = spring["cloud"] as Map<*, *>
        val nacos = cloud["nacos"] as Map<*, *>
        val discovery = nacos["discovery"] as Map<*, *>
        val config = nacos["config"] as Map<*, *>
        val discoveryServerAdd= discovery["server-addr"] as String
        val configServerAdd = config["server-addr"] as String

        return NacosConfiguration(
            applicationName = applicationName,
            discoveryServer = discoveryServerAdd,
            configServer = configServerAdd
        )
    }

}
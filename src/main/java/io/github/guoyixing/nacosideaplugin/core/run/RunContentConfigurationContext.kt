package io.github.guoyixing.nacosideaplugin.core.run

import com.intellij.openapi.editor.Editor
import io.github.guoyixing.nacosideaplugin.nacos.NacosClient
import io.github.guoyixing.nacosideaplugin.nacos.config.model.NacosConfigsResp

/**
 * 用来展示Nacos配置的上下文
 *
 * @author 郭一行
 * @date 2024/11/26 14:24
 */
data class RunContentConfigurationContext(
    val editor: Editor,
    val nacosClient: NacosClient,
    /**
     * 当前选择的配置
     */
    var selectConfiguration: NacosConfigsResp? = null
)
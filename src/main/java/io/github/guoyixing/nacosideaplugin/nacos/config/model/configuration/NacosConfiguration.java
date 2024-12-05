package io.github.guoyixing.nacosideaplugin.nacos.config.model.configuration;

import org.gradle.internal.impldep.org.testng.internal.Yaml;

import java.util.List;

/**
 * Nacos配置
 *
 * @author 郭一行
 * @date 2024/12/2 13:13
 */
public class NacosConfiguration {


    private Spring spring;

    public NacosConfiguration() {
    }

    public Spring getSpring() {
        return spring;
    }

    public void setSpring(Spring spring) {
        this.spring = spring;
    }

    public class Spring {
        private Application application;

        public Application getApplication() {
            return application;
        }

        public void setApplication(Application application) {
            this.application = application;
        }
    }

    public class Application {
        private String name;
        private Nacos nacos;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Nacos getNacos() {
            return nacos;
        }

        public void setNacos(Nacos nacos) {
            this.nacos = nacos;
        }
    }

    public class Nacos {
        private Config config;
        private Discovery discovery;

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        public Discovery getDiscovery() {
            return discovery;
        }

        public void setDiscovery(Discovery discovery) {
            this.discovery = discovery;
        }
    }

    public class Discovery {
        /**
         * 服务器地址
         */
        private String serverAddr;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 服务名称
         */
        private String service;

        /**
         * 权重
         */
        private Double weight;

        /**
         * 网卡名称
         */
        private String networkInterface;

        /**
         * 注册IP地址
         */
        private String ip;

        /**
         * 注册IP类型
         */
        private String ipType;

        /**
         * 注册端口
         */
        private Integer port;

        /**
         * 命名空间
         */
        private String namespace;

        /**
         * 元数据
         */
        private String metadata;

        /**
         * 日志文件名
         */
        private String logName;

        /**
         * 集群名称
         */
        private String clusterName;

        /**
         * 端点
         */
        private String enpoint;

        public String getServerAddr() {
            return serverAddr;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getNetworkInterface() {
            return networkInterface;
        }

        public void setNetworkInterface(String networkInterface) {
            this.networkInterface = networkInterface;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getIpType() {
            return ipType;
        }

        public void setIpType(String ipType) {
            this.ipType = ipType;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getMetadata() {
            return metadata;
        }

        public void setMetadata(String metadata) {
            this.metadata = metadata;
        }

        public String getLogName() {
            return logName;
        }

        public void setLogName(String logName) {
            this.logName = logName;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public String getEnpoint() {
            return enpoint;
        }

        public void setEnpoint(String enpoint) {
            this.enpoint = enpoint;
        }
    }

    public class Config {

        /**
         * 服务器地址
         */
        private String serverAddr;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * nacos配置的dataId
         * 先取前缀，再去取名字，最后取applicationName
         */
        private String name;

        /**
         * nacos配置的dataId前缀
         * 先取前缀，再去取名字，最后取
         */
        private String prefix;

        /**
         * 对nacos配置内容进行编码
         */
        private String encode;

        /**
         * nacos配置的GROUP
         */
        private String group;

        /**
         * nacos配置dataId的后缀，也是配置内容的文件扩展名
         * 目前支持properties或者yaml(yml)
         */
        private String fileExtension;

        /**
         * 从 nacos 获取配置超时
         */
        private Long timeout;

        /**
         * 端点
         */
        private String endpoint;

        /**
         * 命名空间
         */
        private String namespace;

        /**
         * 集群名
         */
        private String clusterName;

        /**
         * 共享配置的dataId
         * 以“，”分隔
         * 旧版
         */
        private String sharedDataids;

        /**
         * 共享配置的动态刷新dataId
         * 以“，”分隔
         * 旧版
         */
        private String refreshableDataids;

        /**
         * 共享配置
         * 新版
         */
        private List<ExtConfig> sharedConfigs;

        /**
         * 扩展配置
         * 旧版
         */
        private List<ExtConfig> extConfig;

        /**
         * 扩展配置
         * 新版
         */
        private List<ExtConfig> extensionConfigs;

        public String getServerAddr() {
            return serverAddr;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getEncode() {
            return encode;
        }

        public void setEncode(String encode) {
            this.encode = encode;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public Long getTimeout() {
            return timeout;
        }

        public void setTimeout(Long timeout) {
            this.timeout = timeout;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public String getSharedDataids() {
            return sharedDataids;
        }

        public void setSharedDataids(String sharedDataids) {
            this.sharedDataids = sharedDataids;
        }

        public String getRefreshableDataids() {
            return refreshableDataids;
        }

        public void setRefreshableDataids(String refreshableDataids) {
            this.refreshableDataids = refreshableDataids;
        }

        public List<ExtConfig> getSharedConfigs() {
            return sharedConfigs;
        }

        public void setSharedConfigs(List<ExtConfig> sharedConfigs) {
            this.sharedConfigs = sharedConfigs;
        }

        public List<ExtConfig> getExtConfig() {
            return extConfig;
        }

        public void setExtConfig(List<ExtConfig> extConfig) {
            this.extConfig = extConfig;
        }

        public List<ExtConfig> getExtensionConfigs() {
            return extensionConfigs;
        }

        public void setExtensionConfigs(List<ExtConfig> extensionConfigs) {
            this.extensionConfigs = extensionConfigs;
        }
    }

    public class ExtConfig {
        private String dataId;
        private String group;
        private Boolean refresh;

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public Boolean getRefresh() {
            return refresh;
        }

        public void setRefresh(Boolean refresh) {
            this.refresh = refresh;
        }
    }
}
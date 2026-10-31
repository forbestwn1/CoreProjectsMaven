package com.nosliw.core.application.entity.configure.global;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "global.application")
public class HAPConfigureGlobalApplication {

	@Name("static")
	private ServiceConfig staticConfig;
	@Name("core")
	private ServiceConfig coreConfig;
	@Name("datasource")
	private ServiceConfig datasourceConfig;
	@Name("story")
	private ServiceConfig storyConfig;

	public ServiceConfig getStaticConfig() { return staticConfig; }
	public void setStaticConfig(ServiceConfig staticConfig) { this.staticConfig = staticConfig; }
	public String getStaticPort() { return this.staticConfig != null ? this.staticConfig.getPort() : null; }
	public void setStaticPort(String staticPort) { if(this.staticConfig == null) {
		this.staticConfig = new ServiceConfig();
	} this.staticConfig.setPort(staticPort); }
	public String getStaticDomain() { return this.staticConfig != null ? this.staticConfig.getDomain() : null; }
	public void setStaticDomain(String staticDomain) { if(this.staticConfig == null) {
		this.staticConfig = new ServiceConfig();
	} this.staticConfig.setDomain(staticDomain); }
	public String getStaticAppName() { return this.staticConfig != null ? this.staticConfig.getName() : null; }
	public void setStaticAppName(String staticAppName) { if(this.staticConfig == null) {
		this.staticConfig = new ServiceConfig();
	} this.staticConfig.setName(staticAppName); }
	public String getStaticBaseurl() { return this.staticConfig != null ? this.staticConfig.getBaseurl() : null; }
	public void setStaticBaseurl(String staticBaseurl) { if(this.staticConfig == null) {
		this.staticConfig = new ServiceConfig();
	} this.staticConfig.setBaseurl(staticBaseurl); }

	public ServiceConfig getCoreConfig() { return coreConfig; }
	public void setCoreConfig(ServiceConfig coreConfig) { this.coreConfig = coreConfig; }
	public String getCorePort() { return this.coreConfig != null ? this.coreConfig.getPort() : null; }
	public void setCorePort(String corePort) { if(this.coreConfig == null) {
		this.coreConfig = new ServiceConfig();
	} this.coreConfig.setPort(corePort); }
	public String getCoreDomain() { return this.coreConfig != null ? this.coreConfig.getDomain() : null; }
	public void setCoreDomain(String coreDomain) { if(this.coreConfig == null) {
		this.coreConfig = new ServiceConfig();
	} this.coreConfig.setDomain(coreDomain); }
	public String getCoreAppName() { return this.coreConfig != null ? this.coreConfig.getName() : null; }
	public void setCoreAppName(String coreAppName) { if(this.coreConfig == null) {
		this.coreConfig = new ServiceConfig();
	} this.coreConfig.setName(coreAppName); }
	public String getCoreBaseurl() { return this.coreConfig != null ? this.coreConfig.getBaseurl() : null; }
	public void setCoreBaseurl(String coreBaseurl) { if(this.coreConfig == null) {
		this.coreConfig = new ServiceConfig();
	} this.coreConfig.setBaseurl(coreBaseurl); }

	public ServiceConfig getdatasource() { return datasourceConfig; }
	public void setdatasource(ServiceConfig datasource) { this.datasourceConfig = datasource; }
	public String getDatasourcePort() { return this.datasourceConfig != null ? this.datasourceConfig.getPort() : null; }
	public void setDatasourcePort(String datasourcePort) { if(this.datasourceConfig == null) {
		this.datasourceConfig = new ServiceConfig();
	} this.datasourceConfig.setPort(datasourcePort); }
	public String getDatasourceDomain() { return this.datasourceConfig != null ? this.datasourceConfig.getDomain() : null; }
	public void setDatasourceDomain(String datasourceDomain) { if(this.datasourceConfig == null) {
		this.datasourceConfig = new ServiceConfig();
	} this.datasourceConfig.setDomain(datasourceDomain); }
	public String getDatasourceAppName() { return this.datasourceConfig != null ? this.datasourceConfig.getName() : null; }
	public void setDatasourceAppName(String datasourceAppName) { if(this.datasourceConfig == null) {
		this.datasourceConfig = new ServiceConfig();
	} this.datasourceConfig.setName(datasourceAppName); }
	public String getDatasourceBaseurl() { return this.datasourceConfig != null ? this.datasourceConfig.getBaseurl() : null; }
	public void setDatasourceBaseurl(String datasourceBaseurl) { if(this.datasourceConfig == null) {
		this.datasourceConfig = new ServiceConfig();
	} this.datasourceConfig.setBaseurl(datasourceBaseurl); }

	public ServiceConfig getStoryConfig() { return storyConfig; }
	public void setStoryConfig(ServiceConfig storyConfig) { this.storyConfig = storyConfig; }
	public String getStoryPort() { return this.storyConfig != null ? this.storyConfig.getPort() : null; }
	public void setStoryPort(String storyPort) { if(this.storyConfig == null) {
		this.storyConfig = new ServiceConfig();
	} this.storyConfig.setPort(storyPort); }
	public String getStoryDomain() { return this.storyConfig != null ? this.storyConfig.getDomain() : null; }
	public void setStoryDomain(String storyDomain) { if(this.storyConfig == null) {
		this.storyConfig = new ServiceConfig();
	} this.storyConfig.setDomain(storyDomain); }
	public String getStoryAppName() { return this.storyConfig != null ? this.storyConfig.getName() : null; }
	public void setStoryAppName(String storyAppName) { if(this.storyConfig == null) {
		this.storyConfig = new ServiceConfig();
	} this.storyConfig.setName(storyAppName); }
	public String getStoryBaseurl() { return this.storyConfig != null ? this.storyConfig.getBaseurl() : null; }
	public void setStoryBaseurl(String storyBaseurl) { if(this.storyConfig == null) {
		this.storyConfig = new ServiceConfig();
	} this.storyConfig.setBaseurl(storyBaseurl); }

	public static class ServiceConfig {
		private String port;
		private String domain;
		private String name;
		private String baseurl;

		public String getPort() { return port; }
		public void setPort(String port) { this.port = port; }
		public String getDomain() { return domain; }
		public void setDomain(String domain) { this.domain = domain; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getBaseurl() { return baseurl; }
		public void setBaseurl(String baseurl) { this.baseurl = baseurl; }
	}
}
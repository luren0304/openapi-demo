package com.excel.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sftp")
public class SftpConfig {

	private String host;
	private String port;
	private String user;
	private String password;
	private String remoteInpath;
	private String localInpath;
	private String remoteOutpath;
	private String localOutpath;
	
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemoteInpath() {
		return remoteInpath;
	}
	public void setRemoteInpath(String remoteInpath) {
		this.remoteInpath = remoteInpath;
	}
	public String getLocalInpath() {
		return localInpath;
	}
	public void setLocalInpath(String localInpath) {
		this.localInpath = localInpath;
	}
	public String getRemoteOutpath() {
		return remoteOutpath;
	}
	public void setRemoteOutpath(String remoteOutpath) {
		this.remoteOutpath = remoteOutpath;
	}
	public String getLocalOutpath() {
		return localOutpath;
	}
	public void setLocalOutpath(String localOutpath) {
		this.localOutpath = localOutpath;
	}
}

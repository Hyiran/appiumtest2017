package com.oribo.common;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Capabilities {
	private final String URL ="http://127.0.0.1:";
	private final String ENDURL ="/wd/hub";
	private DesiredCapabilities   capability;//appium 的属性
	private String port;
	public DesiredCapabilities getCapability() {
		return capability;
	}
	public void setCapability(DesiredCapabilities capability) {
		this.capability = capability;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = URL +port+ENDURL;
	}
	
	
}

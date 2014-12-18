package com.pss.poc.web.bean;


public class ClientDetailsBean {
	

	private String clientid;
	
	private String clientScrt;
	
	private String clientAppName;
	
	private String clientUri;
	
	private String clientRedirectUri;

	
	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getClientScrt() {
		return clientScrt;
	}

	public void setClientScrt(String clientScrt) {
		this.clientScrt = clientScrt;
	}

	public String getClientAppName() {
		return clientAppName;
	}

	public void setClientAppName(String clientAppName) {
		this.clientAppName = clientAppName;
	}

	public String getClientUri() {
		return clientUri;
	}

	public void setClientUri(String clientUri) {
		this.clientUri = clientUri;
	}

	public String getClientRedirectUri() {
		return clientRedirectUri;
	}

	public void setClientRedirectUri(String clientRedirectUri) {
		this.clientRedirectUri = clientRedirectUri;
	}

}

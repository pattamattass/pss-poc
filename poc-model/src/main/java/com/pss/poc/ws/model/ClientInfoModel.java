package com.pss.poc.ws.model;

public class ClientInfoModel {

	private String clientId;

	private String clientScrt;

	private Integer clientStatuscode;

	private String clientStatusInfo;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientScrt() {
		return clientScrt;
	}

	public void setClientScrt(String clientScrt) {
		this.clientScrt = clientScrt;
	}

	public Integer getClientStatuscode() {
		return clientStatuscode;
	}

	public void setClientStatuscode(Integer clientStatuscode) {
		this.clientStatuscode = clientStatuscode;
	}

	public String getClientStatusInfo() {
		return clientStatusInfo;
	}

	public void setClientStatusInfo(String clientStatusInfo) {
		this.clientStatusInfo = clientStatusInfo;
	}
}

package com.pss.poc.orm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_details", catalog = "pocdb")
public class ClientDetails implements java.io.Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 3532796380909158039L;
	
	private String idclientdetails;

	private String clientid;
	
	private String clientScrt;
	
	private String clientAppName;
	
	private String clientUri;
	
	private String clientRedirectUri;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idclient_details", unique = true, nullable = false, length = 36)
	public String getIdclientdetails() {
		return idclientdetails;
	}

	public void setIdclientdetails(String idclientdetails) {
		this.idclientdetails = idclientdetails;
	}

	@Column(name = "client_id")
	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	@Column(name = "client_scrt")
	public String getClientScrt() {
		return clientScrt;
	}

	public void setClientScrt(String clientScrt) {
		this.clientScrt = clientScrt;
	}

	@Column(name = "client_app_name")
	public String getClientAppName() {
		return clientAppName;
	}

	public void setClientAppName(String clientAppName) {
		this.clientAppName = clientAppName;
	}

	@Column(name = "client_app_uri")
	public String getClientUri() {
		return clientUri;
	}

	public void setClientUri(String clientUri) {
		this.clientUri = clientUri;
	}
	@Column(name = "client_app_redirect_uri")
	public String getClientRedirectUri() {
		return clientRedirectUri;
	}

	public void setClientRedirectUri(String clientRedirectUri) {
		this.clientRedirectUri = clientRedirectUri;
	}
	

	
	 
	
	
	

}

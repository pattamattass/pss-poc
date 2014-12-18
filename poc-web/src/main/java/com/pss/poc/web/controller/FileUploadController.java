package com.pss.poc.web.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.form.Form;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.rs.security.oauth2.client.OAuthClientUtils;
import org.apache.cxf.rs.security.oauth2.common.OAuthAuthorizationData;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.pss.poc.web.util.PocWebHelper;

@ManagedBean(name = "fileUploadView")
@SessionScoped
@SuppressWarnings({ "unchecked", "unused" })
public class FileUploadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7366365010692356971L;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("poc-web");
	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
	private static final String BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/poc-ws/pocupload/FileUploadService/";

	public void fileUpload(FileUploadEvent event) {
		LOGGER.info("Class :: " + this.getClass() + " :: Method :: fileUpload");
		try {
			
			
			 WebClient client=WebClient.create(BASE_URL+"addfile");
			/*String scope = OAuthConstants.AUTHORIZATION_CODE_GRANT;
			URI uri =OAuthClientUtils.getAuthorizationURI(BASE_AUTH_URL, 
                    "123456789",
                    BASE_URL+"/complete",
                    1+"",
                    scope);*/
			
//			Response.seeOther(uri).build();
	    	//OAuthAuthorizationData data = client.get(OAuthAuthorizationData.class);  
		/*	
			OAuthAuthorizationData data = client.get(OAuthAuthorizationData.class);    	
	    	Object authenticityCookie = client.getResponse().getMetadata().getFirst("Set-Cookie");
	    	System.out.println(authenticityCookie);
	    	    	
	    	Form authorizationResult = getAuthorizationResult(data);*/
			
			client.type("multipart/form-data");
			client.replaceHeader("clientId", BUNDLE.getString("ws.clientid"));
			client.replaceHeader("clientscrt", BUNDLE.getString("ws.clientsecret"));
			List<Attachment> attachments = new ArrayList<Attachment>();
			ContentDisposition cd = new ContentDisposition("attachment;filename=" + event.getFile().getFileName() + ";filetype=" + event.getFile().getContentType());
			Attachment attachment = new Attachment("id", event.getFile().getInputstream(), cd);
			attachments.add(attachment);
			Response response = client.post(new MultipartBody(attachments));
			client.close();
			 if (response.getStatus() == 200) {
				PocWebHelper.addMessage("File Uploaded successfully", FacesMessage.SEVERITY_INFO);

			} else {
				PocWebHelper.addMessage("File Uploading error :: " + response.getStatusInfo(), FacesMessage.SEVERITY_ERROR);
			}
 
		} catch (Exception e) {
			LOGGER.error(e, e);
			PocWebHelper.addMessage("File Uploading error :: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}

	}
	
	
	  private WebClient createClient(String address, String userName, String password) {
	    	
		  
		  JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
	    	bean.setAddress(address);
	    	bean.setUsername(userName);
	    	bean.setPassword(password);
	    	
	    	bean.getOutInterceptors().add(new LoggingOutInterceptor());
	    	bean.getInInterceptors().add(new LoggingInInterceptor());
	    	return bean.createWebClient();
	    	 
	    	
	    	 
	    }
	  
	  
	  private Form getAuthorizationResult(OAuthAuthorizationData data) {
	        Form form = new Form();
	        form.set("client_id", data.getClientId());
	        form.set("state", data.getState());
	        form.set("scope", data.getProposedScope());
	        form.set("redirect_uri", data.getRedirectUri());
	        // TODO: get the user confirmation, using a popup window or a blocking cmd input
	        form.set("oauthDecision", "allow");
	        form.set("session_authenticity_token", data.getAuthenticityToken());
	        return form;
	    }
	

	public void viewFiles() {
		try {
			LOGGER.info("Class :: " + this.getClass() + " :: Method :: viewFiles");
			WebClient client = WebClient.create(BASE_URL + "downloadfile");
			client.type("multipart/form-data").accept(MediaType.MULTIPART_FORM_DATA);
			List<Attachment> attachments = new ArrayList<Attachment>();
			ContentDisposition cd = new ContentDisposition("attachment");
			// Attachment attachment = new Attachment("id", stream, cd);
			// attachments.add(attachment);
			new MultipartBody(true);
			attachments = (List<Attachment>) client.postAndGetCollection(new MultipartBody(true), Attachment.class);

			client.close();

		} catch (Exception e) {

		}

	}

}

package com.pss.poc.web.controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.pss.poc.web.util.PocWebHelper;
import com.pss.poc.ws.model.ClientDetailsModel;
import com.pss.poc.ws.model.ClientInfoModel;

@ManagedBean(name = "appRegistrationController")
@SessionScoped
public class AppRegistrationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5091531073649698128L;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("poc-web");
	private static final Logger LOGGER = Logger.getLogger(AppRegistrationController.class);
	private static final String BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/poc-ws/oauth/registerProvider/";

	private String clientAppName;

	private String clientUri;

	private String clientRedirectUri;

	public void addClientDetails() {
		LOGGER.info("Class :: " + this.getClass() + " :: Method :: fileUpload");
		try {

			WebClient client = PocWebHelper.createCustomClient(BASE_URL + "addclientdetails");

			ClientDetailsModel model = new ClientDetailsModel();
			model.setClientAppName(clientAppName);
			model.setClientRedirectUri(clientRedirectUri);
			model.setClientUri(clientUri);
			ClientInfoModel clientInfoModel = client.accept(MediaType.APPLICATION_JSON).post(model, ClientInfoModel.class);
			client.close();
			if (clientInfoModel.getClientStatuscode() == 0) {

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registerd Succesfully", "Client Id :" + clientInfoModel.getClientId() + "</br> Client Secret :" + clientInfoModel.getClientScrt());

				RequestContext.getCurrentInstance().showMessageInDialog(message);

			} else {
				PocWebHelper.addMessage("Client Appplication Registration Failed" + clientInfoModel.getClientStatusInfo(), FacesMessage.SEVERITY_ERROR);
			}

		} catch (Exception e) {
			LOGGER.error(e, e);
			PocWebHelper.addMessage("Client Appplication Registration error :: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}

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

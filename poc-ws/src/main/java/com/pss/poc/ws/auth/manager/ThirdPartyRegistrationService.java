package com.pss.poc.ws.auth.manager;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.rs.security.oauth2.common.Client;

import com.pss.poc.orm.bean.ClientDetails;
import com.pss.poc.orm.dao.ClientDetailsDAO;
import com.pss.poc.ws.model.ClientDetailsModel;
import com.pss.poc.ws.model.ClientInfoModel;

@Path(value = "/registerProvider")
public class ThirdPartyRegistrationService {

	@Context
	private UriInfo uriInfo;
	private OAuthManager manager;
	private ClientDetailsDAO clientDetailsDAO;

	@POST
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@Path("/addclientdetails")
	public ClientInfoModel register(ClientDetailsModel model) {

		try {
			String clientId = generateClientId(model.getClientAppName(), model.getClientUri());
			String clientSecret = generateClientSecret();
			model.setClientScrt(clientSecret);
			model.setClientid(clientId);
			Client newClient = new Client(clientId, clientSecret, true, model.getClientAppName(), model.getClientUri());
			newClient.setRedirectUris(Collections.singletonList(model.getClientRedirectUri()));
			clientDetailsDAO.save(createClientDetails(model));
			manager.registerClient(newClient);

			ClientInfoModel infoModel = new ClientInfoModel();
			infoModel.setClientId(clientId);
			infoModel.setClientScrt(clientSecret);
			infoModel.setClientStatuscode(Integer.valueOf(0));
			infoModel.setClientStatusInfo("Success");

			return infoModel;

		} catch (Exception e) {
			ClientInfoModel infoModel = new ClientInfoModel();
			infoModel.setClientStatuscode(Integer.valueOf(1));
			infoModel.setClientStatusInfo(e.getMessage());

			return infoModel;
		}

	}

	public String generateClientId(String appName, String appURI) {
		return System.currentTimeMillis() + "";
	}

	public ClientDetails createClientDetails(ClientDetailsModel model) {
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setClientAppName(model.getClientAppName());
		clientDetails.setClientid(model.getClientid());
		clientDetails.setClientUri(model.getClientUri());
		clientDetails.setClientScrt(model.getClientScrt());
		clientDetails.setClientRedirectUri(model.getClientRedirectUri());

		return clientDetails;
	}

	public String generateClientSecret() {
		String asB64 = "";
		try {
			asB64 = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return asB64;
	}

	public void setDataProvider(OAuthManager manager) {
		this.manager = manager;
	}

	public ClientDetailsDAO getClientDetailsDAO() {
		return clientDetailsDAO;
	}

	public void setClientDetailsDAO(ClientDetailsDAO clientDetailsDAO) {
		this.clientDetailsDAO = clientDetailsDAO;
	}
}

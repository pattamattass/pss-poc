package com.pss.poc.ws.thirdparty;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.SecurityContext;
import org.springframework.http.HttpRequest;

import com.pss.poc.orm.bean.ClientDetails;
import com.pss.poc.orm.dao.ClientDetailsDAO;

@Provider
public class SecurityContextFilter implements RequestHandler {

	@Context
	private HttpHeaders headers;
	private ClientDetailsDAO clientDetailsDAO;
	
	public ClientDetailsDAO getClientDetailsDAO() {
		return clientDetailsDAO;
	}

	public void setClientDetailsDAO(ClientDetailsDAO clientDetailsDAO) {
		this.clientDetailsDAO = clientDetailsDAO;
	}


	private String realm;
	 
	
	
	public Response handleRequest(Message message, ClassResourceInfo cri) {
		
		
		
		HttpServletRequest request = (HttpServletRequest)message.get("HTTP.REQUEST");
		String clientId= request.getHeader("clientid");
		String clientScrt= request.getHeader("clientscrt");
		List  clientDetailsList = clientDetailsDAO.findByProperty("clientid", clientId);
		if(clientDetailsList!=null && clientDetailsList.size()>0)
		{
			if(clientDetailsList.get(0)!=null&& ((ClientDetails)clientDetailsList.get(0)).getClientScrt()!=null && ((ClientDetails)clientDetailsList.get(0)).getClientScrt().equalsIgnoreCase(clientScrt))
			{
				
			}else{
				return createFaultResponse();
			}
		}else{
			return createFaultResponse();
		}
		SecurityContext sc = message.get(SecurityContext.class);
		if (sc != null) {
		    Principal principal  = sc.getUserPrincipal();
		   /* if (principal != null && users.containsKey(principal.getName())) {
			    return null;
		    }*/
		}
		
		
		/*List<String> authValues = headers.getRequestHeader("Authorization");
		if (authValues.size() != 1) {
			return createFaultResponse();
		}
		String[] values = authValues.get(0).split(" ");
		if (values.length != 2 || !"Basic".equals(values[0])) {
			return createFaultResponse();
		}
		
		String decodedValue = null;
		try {
			decodedValue = new String(Base64Utility.decode(values[1]));
		} catch (Base64Exception ex) {
			return createFaultResponse();
		}
		final String[] namePassword = decodedValue.split(":");
		if (namePassword.length != 2) {
			return createFaultResponse();
		}*/
		/*String password = users.get(namePassword[0]); 
		if (password == null || !password.equals(namePassword[1])) {
			return createFaultResponse();
		}*/
		final SecurityContext newSc = new SecurityContext() {

			public Principal getUserPrincipal() {
				return new SimplePrincipal(clientId);
			}

			public boolean isUserInRole(String arg0) {
				return false;
			}
			
		};
		message.put(SecurityContext.class, newSc);
		return null;
	}

	private Response createFaultResponse() {
		return Response.ok("Service Authentication failed").build();
	}


	public String getRealm() {
		return realm;
	}


	public void setRealm(String realm) {
		this.realm = realm;
	}
}

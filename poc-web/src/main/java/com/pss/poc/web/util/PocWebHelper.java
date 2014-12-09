package com.pss.poc.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class PocWebHelper {
	

	public static void addMessage(String desc, Severity sev) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(sev, desc, desc));
	}

	public static void addObjectIntoSession(String key, Object obj) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(key, obj);
	}

	public static void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
	}

	public static Object getObjectIntoSession(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getSessionMap().get(key);
	}
}
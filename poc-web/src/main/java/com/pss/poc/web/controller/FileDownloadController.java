package com.pss.poc.web.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.pss.poc.web.util.PocWebHelper;

@ManagedBean(name = "fileDownloadView")
@SessionScoped
public class FileDownloadController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 936148808349484797L;
	private StreamedContent file;
	private String fileId;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("poc-web");
	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
	private static final String BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/pss-ws/pocupload/FileUploadService/";
	
	 public void fileDownloadViewListener() {        
	        InputStream stream=null;
			try {
				stream = new ByteArrayInputStream( fileId.getBytes());
				fileId="";
				WebClient client = WebClient.create(BASE_URL + "downloadfile");
				client.type("multipart/form-data").accept(MediaType.MULTIPART_FORM_DATA);
				List<Attachment> attachments = new ArrayList<Attachment>();
				ContentDisposition cd = new ContentDisposition("attachment");
				Attachment attachment = new Attachment("id", stream, cd);
				attachments.add(attachment);
				attachments= (List<Attachment>) client.postAndGetCollection(new MultipartBody(attachments),Attachment.class);
				file = new DefaultStreamedContent(attachments.get(0).getDataHandler().getInputStream(),attachments.get(0).getContentDisposition().getParameter("filetype"), attachments.get(0).getContentDisposition().getParameter("filename"));
				
				client.close();
				PocWebHelper.addMessage("File Downloaded successfully", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				PocWebHelper.addMessage("File Downloading error :: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
			}
	       
	    }
	 
	 public StreamedContent getFile() {
			return file;
		}

		public void setFile(StreamedContent file) {
			this.file = file;
		}

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
	    

}

package com.pss.poc.web.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
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

import com.pss.poc.web.bean.FileUploadBean;
import com.pss.poc.web.util.PocWebHelper;
import com.pss.poc.ws.model.FileUploadModel;

@ManagedBean(name = "fileDownloadView")
@SessionScoped
@SuppressWarnings("unchecked")
public class FileDownloadController implements Serializable {

	private static final long serialVersionUID = 936148808349484797L;
	private StreamedContent file;
	private String fileId;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("poc-web");
	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
	private static final String BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/poc-ws/pocupload/FileUploadService/";
	private static final String BASE_URL_VIEW = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/poc-ws/pocView/FileViewService/";
	private List<FileUploadBean> beans;
	private FileUploadBean bean;

	@PostConstruct
	public void loadData() {

	}

	public void fileDownloadViewListener(FileUploadBean bean) {
		InputStream stream = null;
		try {
			if (fileId != null && !fileId.trim().equalsIgnoreCase("")) {
				stream = new ByteArrayInputStream(fileId.getBytes());
				fileId = "";
			} else {
				stream = new ByteArrayInputStream(bean.getFileId().getBytes());
			}

			
			WebClient client = WebClient.create(BASE_URL + "downloadfile");
			client.type("multipart/form-data").accept(MediaType.MULTIPART_FORM_DATA);
			client.replaceHeader("clientId", BUNDLE.getString("ws.clientid"));
			client.replaceHeader("clientscrt", BUNDLE.getString("ws.clientsecret"));
			List<Attachment> attachments = new ArrayList<Attachment>();
			ContentDisposition cd = new ContentDisposition("attachment");
			Attachment attachment = new Attachment("id", stream, cd);
			attachments.add(attachment);
			attachments = (List<Attachment>) client.postAndGetCollection(new MultipartBody(attachments), Attachment.class);
			file = new DefaultStreamedContent(attachments.get(0).getDataHandler().getInputStream(), attachments.get(0).getContentDisposition().getParameter("filetype"), attachments.get(0).getContentDisposition().getParameter("filename"));

			client.close();
			PocWebHelper.addMessage("File Downloaded successfully", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			LOGGER.error(e, e);
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

	public List<FileUploadBean> getBeans() {

		WebClient client = PocWebHelper.createCustomClient(BASE_URL_VIEW + "list");
		client.replaceHeader("clientId", BUNDLE.getString("ws.clientid"));
		client.replaceHeader("clientscrt", BUNDLE.getString("ws.clientsecret"));
		Collection<? extends FileUploadModel> models = new ArrayList<FileUploadModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(FileUploadModel.class));
		client.close();
		beans = new ArrayList<FileUploadBean>();
		for (FileUploadModel model : models) {
			FileUploadBean bean = new FileUploadBean();

			bean.setFileDate(model.getFileDate());
			bean.setFileId(model.getFileId());
			bean.setFileName(model.getFileName());
			bean.setFileSize(model.getFileSize());
			bean.setFileType(model.getFileType());

			beans.add(bean);
		}

		return beans;
	}

	public void setBeans(List<FileUploadBean> beans) {
		this.beans = beans;
	}

	public FileUploadBean getBean() {
		return bean;
	}

	public void setBean(FileUploadBean bean) {
		this.bean = bean;
	}

}

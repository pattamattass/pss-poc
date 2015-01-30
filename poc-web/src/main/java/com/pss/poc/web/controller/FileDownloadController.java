package com.pss.poc.web.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.pss.innoms.ws.model.InnomsChallengeModel;
import com.pss.innoms.ws.model.InnomsClaimModel;
import com.pss.innoms.ws.model.InnomsIdeaModel;
import com.pss.innoms.ws.model.InnomsSolutionModel;
import com.pss.innoms.ws.util.CustomObjectMapper;
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
	
	private static final String CH_BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/innoms-ws/innoms/InnomsChallengeService/";
	private static final String CL_BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/innoms-ws/innoms/InnomsClaimService/";
	private static final String S_BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/innoms-ws/innoms/InnomsSolutionService/";
	private static final String I_BASE_URL = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/innoms-ws/innoms/InnomsIdeaService/";
	private static final String BASE_URL_VIEW = "http://" + BUNDLE.getString("ws.host") + ":" + BUNDLE.getString("ws.port") + "/poc-ws/pocView/FileViewService/";
	private List<FileUploadBean> beans;
	private FileUploadBean bean;
	 private JasperPrint jasperPrint;

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
	
	public void getPdf()
	{
		 
		
		
		
		
		
	}
	
	public void reportBuilder() throws JRException {

		try{
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beans);

			String report = "/home/sairamd/git/pss-poc 17 12/pss-poc/poc-web/Blank_A4.jasper";
			InputStream inputStream = new FileInputStream("/home/sairamd/git/pss-poc 17 12/pss-poc/poc-web/Blank_A4.jrxml");
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	
	public void exportToPdf(ActionEvent actionEvent) throws JRException, IOException {

		init();  
	       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	       httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
	       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	       FacesContext.getCurrentInstance().responseComplete();  
	}
	
	
	public void init() throws JRException {
		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beans);
			String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/Blank_A4.jasper"); 
			InputStream inputStream = new FileInputStream("/home/sairamd/git/pss-poc 17 12/pss-poc/poc-web/Blank_A4.jasper");
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void exportIdeaToPdf(ActionEvent actionEvent) throws JRException, IOException {

		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getIdeaBeans());
			String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/idea.jasper"); 
			InputStream inputStream = new FileInputStream(reportPath);
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	       httpServletResponse.addHeader("Content-disposition", "attachment; filename=idea.pdf");  
	       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	       FacesContext.getCurrentInstance().responseComplete();  
	}
	
	
	public void exportChallengeToPdf(ActionEvent actionEvent) throws JRException, IOException {

		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getChallengeBeans());
			String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/challenge.jasper"); 
			InputStream inputStream = new FileInputStream(reportPath);
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	       httpServletResponse.addHeader("Content-disposition", "attachment; filename=challenge.pdf");  
	       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	       FacesContext.getCurrentInstance().responseComplete();  
	}
	
	
	
	public void exportSolutionToPdf(ActionEvent actionEvent) throws JRException, IOException {
		
		


		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getSolutionBeans());
			String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/solution.jasper"); 
			InputStream inputStream = new FileInputStream(reportPath);
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	       httpServletResponse.addHeader("Content-disposition", "attachment; filename=solution.pdf");  
	       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	       FacesContext.getCurrentInstance().responseComplete();  
	}
	
	
	public void exportClaimToPdf(ActionEvent actionEvent) throws JRException, IOException {

		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getClaimBeans());
			String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/claim.jasper"); 
			InputStream inputStream = new FileInputStream(reportPath);
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap(), beanCollectionDataSource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	       httpServletResponse.addHeader("Content-disposition", "attachment; filename=claim.pdf");  
	       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	       FacesContext.getCurrentInstance().responseComplete();  
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
	
	public static void main(String[] args) {
		WebClient client = PocWebHelper.createCustomClient(BASE_URL_VIEW + "list");
		client.replaceHeader("clientId", BUNDLE.getString("ws.clientid"));
		client.replaceHeader("clientscrt", BUNDLE.getString("ws.clientsecret"));
		Collection<? extends FileUploadModel> models = new ArrayList<FileUploadModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(FileUploadModel.class));
		client.close();
		ArrayList<FileUploadBean> beansd = new ArrayList<FileUploadBean>();
		for (FileUploadModel model : models) {
			FileUploadBean bean = new FileUploadBean();

			bean.setFileDate(model.getFileDate());
			bean.setFileId(model.getFileId());
			bean.setFileName(model.getFileName());
			bean.setFileSize(model.getFileSize());
			bean.setFileType(model.getFileType());

			beansd.add(bean);
		}

		try {
			InputStream inputStream = new FileInputStream("/home/sairamd/git/pss-poc 17 12/pss-poc/poc-web/Blank_A4.jrxml");
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beansd);

			Map parameters = new HashMap();

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint,"test_jasper.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<InnomsChallengeModel> getChallengeBeans() {
		WebClient client = createCustomClient(CH_BASE_URL + "/get/user/"+ 0);
		Collection<? extends InnomsChallengeModel> models = new ArrayList<InnomsChallengeModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(InnomsChallengeModel.class));
		client.close();
		 
		return (List<InnomsChallengeModel>) models;
	}
	
	public List<InnomsSolutionModel> getSolutionBeans() {
		WebClient client = createCustomClient(CH_BASE_URL + "/get/user/"+ 0);
		Collection<? extends InnomsSolutionModel> models = new ArrayList<InnomsSolutionModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(InnomsSolutionModel.class));
		client.close();
		 
		return (List<InnomsSolutionModel>) models;
	}
	
	public List<InnomsIdeaModel> getIdeaBeans() {
		WebClient client = createCustomClient(I_BASE_URL + "/get/user/"+ 0);
		Collection<? extends InnomsIdeaModel> models = new ArrayList<InnomsIdeaModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(InnomsIdeaModel.class));
		client.close();
		 
		return (List<InnomsIdeaModel>) models;
	}
	
	public List<InnomsClaimModel> getClaimBeans() {
		WebClient client = createCustomClient(CL_BASE_URL + "/get/user/"+ 0);
		Collection<? extends InnomsClaimModel> models = new ArrayList<InnomsClaimModel>(client.accept(MediaType.APPLICATION_JSON).getCollection(InnomsClaimModel.class));
		client.close();
		 
		return (List<InnomsClaimModel>) models;
	}
	
	public static WebClient createCustomClient(String url) {
		WebClient client = WebClient.create(url, Collections.singletonList(new JacksonJsonProvider(new CustomObjectMapper())));
		client.header("Content-Type", "application/json");
		client.header("Accept", "application/json");
		return client;
	}

}

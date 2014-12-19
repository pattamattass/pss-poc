package com.pss.poc.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.pss.poc.orm.bean.FileUpload;
import com.pss.poc.orm.dao.FileUploadDAO;
import com.pss.poc.ws.model.FileUploadModel;

@Path(value="FileViewService")
public class FileViewService {

	
	private static final Logger LOGGER = Logger.getLogger(FileViewService.class);

	private FileUploadDAO fileUploadDAO;
	@Context
	private SecurityContext sc;
	@Context
	private UriInfo ui;
	
	
	@GET
	@Path("/list")
	@Produces({ "application/json" })
	@Transactional
	public <T extends FileUploadModel> List<T> list() {
		List<T> ret = new ArrayList();
		try {
			List batmod = this.fileUploadDAO.findAll();
			for (Object object : batmod) {
				FileUpload fileuload = (FileUpload) object;
				FileUploadModel model = new FileUploadModel();
				model.setFileDate(fileuload.getFileDate());
				model.setFileId(fileuload.getFileId());
				model.setFileName(fileuload.getFileName());
				model.setFileSize(fileuload.getFileSize());
				model.setFileType(fileuload.getFileType());
				
				ret.add((T) model);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
		}
		return ret;
	}
	public FileUploadDAO getFileUploadDAO() {
		return fileUploadDAO;
	}
	public void setFileUploadDAO(FileUploadDAO fileUploadDAO) {
		this.fileUploadDAO = fileUploadDAO;
	}
	public SecurityContext getSc() {
		return sc;
	}
	public void setSc(SecurityContext sc) {
		this.sc = sc;
	}
	public UriInfo getUi() {
		return ui;
	}
	public void setUi(UriInfo ui) {
		this.ui = ui;
	}
}

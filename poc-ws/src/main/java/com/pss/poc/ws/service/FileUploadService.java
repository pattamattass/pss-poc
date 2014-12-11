package com.pss.poc.ws.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.pss.poc.orm.bean.FileUpload;
import com.pss.poc.orm.dao.FileUploadDAO;

@Path(value = "/FileUploadService")
public class FileUploadService {

	private static final Logger LOGGER = Logger.getLogger(FileUploadService.class);

	private FileUploadDAO fileUploadDAO;

	@POST
	@Path("/addfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Transactional
	public Response uploadFile(MultipartBody body) {

		try {
			List<Attachment> attachments = body.getAllAttachments();
			for (Attachment attachment : attachments) {
				DataHandler handler = attachment.getDataHandler();
				InputStream stream = handler.getInputStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFileId(UUID.randomUUID().toString());
				fileUpload.setFileBlob((bytes));
				fileUpload.setFileDate(new Timestamp(System.currentTimeMillis()));
				fileUpload.setFileName(attachment.getContentDisposition().getParameter("filename"));
				fileUpload.setFileSize((long)bytes.length);
				fileUpload.setFileType(attachment.getContentDisposition().getParameter("filetype"));
				fileUploadDAO.save(fileUpload);
			}
			Response response = Response.ok("SUCCESS").build();
			return response;
		} catch (Exception e) {
			LOGGER.error(e, e);
			Response response = Response.ok(e.getMessage()).build();
			return response;
		}
	}

	@POST
	@Path("/downloadfile")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Transactional
	public List<Attachment> downloadFile(MultipartBody body) {
		List<Attachment> attachments = new ArrayList<Attachment>();
		List<Attachment> attachemtnsWithFieldId = body.getAllAttachments();

		try {
			String fieldId = "";
			if (attachemtnsWithFieldId != null && attachemtnsWithFieldId.size() > 0)
				fieldId = IOUtils.toString(attachemtnsWithFieldId.get(0).getDataHandler().getInputStream(), "UTF-8");
			FileUpload fileUpload = fileUploadDAO.findById(fieldId);
			ContentDisposition cd = new ContentDisposition("attachment;filename=" + fileUpload.getFileName() + ";filetype=" + fileUpload.getFileType());
			InputStream in = new ByteArrayInputStream(fileUpload.getFileBlob());
			Attachment attachment = new Attachment("id", in, cd);
			attachments.add(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return attachments;
	}

	public FileUploadDAO getFileUploadDAO() {
		return fileUploadDAO;
	}

	public void setFileUploadDAO(FileUploadDAO fileUploadDAO) {
		this.fileUploadDAO = fileUploadDAO;
	}
}

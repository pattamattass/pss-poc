package com.pss.poc.ws.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FileUploadDetailsModel")
public class FileUploadModel {
	

	private String fileId;
	
	private String fileType;
	
	private String fileName;
	
	private Timestamp fileDate;
	
	private Long fileSize;

	public String getFileId() {
		return fileId;
	}

	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getFileDate() {
		return fileDate;
	}

	public void setFileDate(Timestamp fileDate) {
		this.fileDate = fileDate;
	}

	
	 

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}


}

package com.pss.poc.orm.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mysql.jdbc.Blob;

@Entity
@Table(name="innoms_fileupload")
public class PocFileUpload implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fileId;
	
	private String fileType;
	
	private String fileName;
	
	private Timestamp fileDate;
	
	private byte[] fileBlob;
	
	private Double fileSize;

	@Id
	@Column(name = "file_id", unique = true, nullable = false, length = 36)
	public String getFileId() {
		return fileId;
	}

	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "file_type", length = 999)
	public String getFileType() {
		return fileType;
	}

	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_name", length = 999)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_date")
	public Timestamp getFileDate() {
		return fileDate;
	}

	public void setFileDate(Timestamp fileDate) {
		this.fileDate = fileDate;
	}

	
	 

	@Column(name = "file_size", length = 20)
	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "file_blob")
	public byte[] getFileBlob() {
		return fileBlob;
	}


	public void setFileBlob(byte[] fileBlob) {
		this.fileBlob = fileBlob;
	}
	
	
	
	
	

}

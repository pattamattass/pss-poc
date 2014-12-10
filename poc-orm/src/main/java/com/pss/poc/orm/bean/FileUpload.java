package com.pss.poc.orm.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FileUpload entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "file_upload", catalog = "pocdb")
public class FileUpload implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -7546461283742893951L;
	private String fileId;
	private String fileName;
	private String fileType;
	private Long fileSize;
	private String fileBlob;
	private Timestamp fileDate;

	// Constructors

	/** default constructor */
	public FileUpload() {
	}

	/** minimal constructor */
	public FileUpload(String fileId) {
		this.fileId = fileId;
	}

	/** full constructor */
	public FileUpload(String fileId, String fileName, String fileType, Long fileSize, String fileBlob, Timestamp fileDate) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileBlob = fileBlob;
		this.fileDate = fileDate;
	}

	// Property accessors
	@Id
	@Column(name = "file_id", unique = true, nullable = false, length = 36)
	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "file_name", length = 999)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_type", length = 999)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_size")
	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "file_blob")
	public String getFileBlob() {
		return this.fileBlob;
	}

	public void setFileBlob(String fileBlob) {
		this.fileBlob = fileBlob;
	}

	@Column(name = "file_date", length = 19)
	public Timestamp getFileDate() {
		return this.fileDate;
	}

	public void setFileDate(Timestamp fileDate) {
		this.fileDate = fileDate;
	}

}
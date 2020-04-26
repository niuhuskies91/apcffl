package org.apcffl.api.comm.dto;

import java.sql.Date;

import org.apcffl.api.dto.ApiRequest;

public class MessageBoard extends ApiRequest {

	private String message;
	private Date createDate;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}

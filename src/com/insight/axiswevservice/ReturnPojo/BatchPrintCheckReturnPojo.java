package com.insight.axiswevservice.ReturnPojo;

import java.util.List;

public class BatchPrintCheckReturnPojo {

	private String status; 
	
	private String message;
	
	private List<String> data;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	



}

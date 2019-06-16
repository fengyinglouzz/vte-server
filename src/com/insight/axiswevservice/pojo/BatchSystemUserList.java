package com.insight.axiswevservice.pojo;

import java.io.Serializable;


public class BatchSystemUserList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private  java.lang.String  customerPublicKeyCode;
	
	private java.util.List<BatchSystemUsryPojo> systemUserPojoList;

	public java.lang.String getCustomerPublicKeyCode() {
		return customerPublicKeyCode;
	}

	public void setCustomerPublicKeyCode(java.lang.String customerPublicKeyCode) {
		this.customerPublicKeyCode = customerPublicKeyCode;
	}

	public java.util.List<BatchSystemUsryPojo> getSystemUserPojoList() {
		return systemUserPojoList;
	}

	public void setSystemUserPojoList(
			java.util.List<BatchSystemUsryPojo> systemUserPojoList) {
		this.systemUserPojoList = systemUserPojoList;
	}
	

}

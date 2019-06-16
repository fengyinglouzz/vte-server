package com.insight.axiswevservice.pojo;

import java.io.Serializable;

import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.pojo.VtePatientPojo;

public class VtePatientHospitInfoList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private java.lang.String  customerPublicKeyCode;
	
	private java.util.List<VteBatchPatientPojo> patientPojoList;

	public java.lang.String getCustomerPublicKeyCode() {
		return customerPublicKeyCode;
	}

	public void setCustomerPublicKeyCode(java.lang.String customerPublicKeyCode) {
		this.customerPublicKeyCode = customerPublicKeyCode;
	}

	public java.util.List<VteBatchPatientPojo> getPatientPojoList() {
		return patientPojoList;
	}

	public void setPatientPojoList(
			java.util.List<VteBatchPatientPojo> patientPojoList) {
		this.patientPojoList = patientPojoList;
	}
	
	
	
}

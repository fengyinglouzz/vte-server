package com.insight.wisehealth.vte.persistencepojo;


import com.insight.wisehealth.vte.persistence.TbSystemUser;
/**
 * 
 * 描述:用户实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemUserPojo extends TbSystemUser{
	private static final long serialVersionUID = 1L;
	//columns START

	/**
	 *  医院名称
	 */
	private java.lang.String hospitalName;
	
	/**
	 *  科室名称
	 */
	private java.lang.String orgName;

	public java.lang.String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(java.lang.String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}
	
	//columns END



	//setter and getter END
}

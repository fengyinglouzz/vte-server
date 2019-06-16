package com.insight.wisehealth.vte.persistencepojo;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
/**
 * 
 * 描述:角色实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemRolePojo extends TbSystemRole implements Serializable{
	//columns START
	/**
	 *  角色数据权限（字典）
	 */
	private java.lang.String rolePowerExplain;
	//columns END


	//setter and getter START
	public java.lang.String getRolePowerExplain() {
		return rolePowerExplain;
	}

	public void setRolePowerExplain(java.lang.String rolePowerExplain) {
		this.rolePowerExplain = rolePowerExplain;
	}

	//setter and getter END
}

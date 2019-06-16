package com.insight.core.config;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportConfig {
	public static String templateFilePath;
	public static String templateFileInnerPath;
	public static String preventionStrategyType;
	public static String assessmentTypeCls;
	public static String assessmentTypeValCls;
	public static String nextVisitUrl;
	public static String orgParentCode;
	public static String patientLastRisk;
	
	static{
		ResourceBundle rb= ResourceBundle.getBundle("exportconfig");
		templateFilePath = rb.getString("templateFilePath");
		templateFileInnerPath = rb.getString("templateFileInnerPath");
		preventionStrategyType = rb.getString("preventionStrategyType");
		assessmentTypeCls = rb.getString("assessmentTypeCls");
		assessmentTypeValCls = rb.getString("assessmentTypeValCls");
		nextVisitUrl = rb.getString("nextVisitUrl");
		orgParentCode = rb.getString("orgParentCode");
		patientLastRisk = rb.getString("patientLastRisk");
	}
	
}

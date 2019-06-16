package com.insight.wisehealth.vte.strategyUtil;

import com.insight.core.config.ExportConfig;

public class PreventionStrategyFactory {

public static PreventionStrategy getPreventionStrategy(){
	
		if("1".equals(ExportConfig.preventionStrategyType)){//算法一
			return new PreventionStrategyOne();
		}else if("2".equals(ExportConfig.preventionStrategyType)){//算法2
			return new PreventionStrategyTwo();
		}
		
		return null;
	}
}

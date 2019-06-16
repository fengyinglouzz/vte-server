package com.insight.core.common;

import java.util.List;


/**
 * 
 * 
 * 描述:基于JSON的返回格式
 * 
 * Copyright © 2015 Frank All rights reserved
 * 
 * 
 * 用法：
 * 返回{totalProperty:总数 ,root:[{属性1：值1},{属性2：值2},{属性3：值3},...,{属性N：值N}]}
 * Ext.grid.GridPanel 只能接收这个格式
 * 
 * 
 * 
 * @author Frank
 * @version 1.0
 */
public class JsonBean {

		/** 总记录数 */
		private int totalProperty;

		/** 分页结果 */
		private List<?> root;

		public int getTotalProperty() {
			return totalProperty;
		}

		public void setTotalProperty(int totalProperty) {
			this.totalProperty = totalProperty;
		}

		public List<?> getRoot() {
			return root;
		}

		public void setRoot(List<?> root) {
			this.root = root;
		}
}

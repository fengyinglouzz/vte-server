package com.insight.core.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 
 * 
 * 描述: salt md5 工具类
 * 
 * Copyright (c) 2013 by . Frank
 * 
 * @author Frank
 * @version 1.0
 */
public final class MD5Encrypt {

	private static final String MD5_PREFIX = "wisehealthvteMD5";

	private static final ThreadLocal<MD5Encrypt> local = new ThreadLocal<MD5Encrypt>();

	private MD5Encrypt() {
		super();
	}

	/**
	 * 获取 MD5Encrypt
	 * @author Frank
	 * @return MD5Encrypt
	 */
	public static MD5Encrypt getEncrypt() {
		MD5Encrypt encrypt = local.get();
		if (encrypt == null) {
			encrypt = new MD5Encrypt();
			local.set(encrypt);
		}
		return encrypt;
	}
	
	/**
	 * MD5加密
	 * @author Frank
	 * @param s 需要加密字符串
	 * @return String
	 */
	public static String encode(String s) {
		if (s == null) {
			return null;
		}
		return DigestUtils.md5Hex(MD5_PREFIX + s);
	}

	public static void main(String[] args){
		String passwd =  "123";
		System.out.println(passwd + " 加密后为： " + encode(passwd)) ;
		
	}
	
}


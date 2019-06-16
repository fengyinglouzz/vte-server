package com.insight.core.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *  
 * 描述: 字符串处理
 * 
 * Copyright © 2015 Goodwill All rights reserved
 * 
 * @author Frank
 * @version 1.0
 */
public class StringUtil {

	/**
	 *  描述：根据分隔符把字符串转化成数组
	 *  
	 * @param str 字符串
	 * @param separatorChar 分隔符 
	 * @return List
	 */
	public static List<String> convStrToList(String str, String separatorChar) {
		String[] strs = StringUtils.split(str, separatorChar);
		return Arrays.asList(strs);
	}
	
	/**
	 * 
	 *  描述：判断是否为空字符串
	 *	 
	 * @author Frank
	 * @param param 需要判断的字符串
	 * @return String
	 */
	public static boolean isEmpty(String param) {
		return param == null || param.trim().length() == 0;
	}


	
	/**
	 * 
	 *  描述：把ISO-8859-1编码的字符串转换成GBK编码字符
	 *	 
     * @author Frank
	 * @param srcString 需要转化的字符串
	 * @return String
	 */
	public static String ISO8859ToGBK(String srcString) {
		try {
			return new String(srcString.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}

	
	/**
	 * 
	 *  描述：把GBK编码的字符串转换成ISO-8859-1编码字符
	 *	 
	 * @author Frank
	 * @param srcString 需要转化的字符串
	 * @return String
	 */
	public static String GBKToISO8859(String srcString) {
		try {
			return new String(srcString.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}


	
	/**
	 * 
	 *  描述：把ISO-8859-1编码的字符串转换成UTF-8编码字符
	 *	 
	 * @author Frank
	 * @param srcString 需要转化的字符串
	 * @return String
	 */
	public static String ISO8859ToUTF8(String srcString) {
		try {
			return new String(srcString.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}


	/**
	 * 
	 *  描述：把UTF-8编码的字符串转换成ISO-8859-1编码字符
	 *	 
	 * @author Frank
	 * @param srcString 需要转化的字符串
	 * @return String
	 */
	public static String UTF8ToISO8859(String srcString) {
		try {
			return new String(srcString.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}

	
	/**
	 * 
	 *  描述：使用 Character.isDigit方法判断字符串是否为数字方法
	 *	 
	 * @author Frank
	 * @param str 需要判断的字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 *  描述：用正则表达式判断字符串是否为数字
	 *	 
	 * @author Frank
	 * @param str 需要判断的字符串
	 * @return boolean
	 */
	public static boolean isNumericByPattern(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	
	/**
	 * 
	 *  描述：判断src是否是数字
	 *	 
	 * @author Frank
	 * @param src 需要判断的字符串
	 * @return boolean
	 */
	public static boolean isDigit(String src) {
		if (src == null || "".equals(src))
			return false;
		char first = src.charAt(0);
		if (!(Character.isDigit(first) && first > '0'))
			return false;
		for (int i = 1; i < src.length(); i++) {
			if (!Character.isDigit(src.charAt(i)))
				return false;
		}
		return true;
	}
	
		
	/**
	 * 
	 *  描述：利用ascii码判断字符串是否为数字
	 *	 
	 * @author Frank
	 * @param str 需要判断的字符串
	 * @return boolean
	 */
	public static boolean isNumericByAsc(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 将字符串srcStr按照字符splitChar划分为字符串数组
	 * 
	 * @author Frank
	 * @param srcStr 为源字符串
	 * @param splitChar 为划分间隔字符
	 * @return String[]
	 */
	
	public static String[] split(String srcStr, String splitChar) {
		if ((srcStr == null) || (srcStr.equals("")))
			return null;
		if (srcStr.indexOf(splitChar) < 0)
			return new String[] { srcStr };

		int i = -1;
		ArrayList<String> arrayList = new ArrayList<String>();
		while ((i = srcStr.indexOf(splitChar)) != -1) {
			arrayList.add(srcStr.substring(0, i));
			srcStr = srcStr.substring(i + splitChar.length(), srcStr.length());
		}
		arrayList.add(srcStr);
		String[] arrayString = new String[arrayList.size()];
		for (int j = 0; j < arrayList.size(); ++j)
			arrayString[j] = ((String) arrayList.get(j)).trim();
		return arrayString;
	}
 
	
	/**
	 * 
	 *  描述：删除字符串中的回车和换行转义字符
	 *	 
	 * @author Frank
	 * @param src 地址
	 * @return String
	 */
	public static String removeRN(String src) {
		String s = "";
		s = replace(src, "\r", "");
		s = replace(s, "\n", "");
		return s;
	}


	
	/**
	 * 
	 *  描述：以参数newStr替换字符串src中存在的oldStr
	 *	 
	 * @author Frank
	 * @param src 字符串
	 * @param oldStr  旧字符串
	 * @param newStr 新字符串
	 * @return String
	 */
	public static String replace(String src, String oldStr, String newStr) {
		int j;
		StringBuffer buffer = new StringBuffer();
		int i = oldStr.length();
		while ((j = src.indexOf(oldStr)) != -1) {
			buffer.append(src.substring(0, j));
			buffer.append(newStr);
			src = src.substring(j + i);
		}
		buffer.append(src);
		return buffer.toString();
	}


	
	/**
	 * 
	 *  描述：将字符串转化成boolean类型
	 *  	 判断一个字符串是否是true或false
	 *	 
	 * @author Frank
	 * @param src 字符串
	 * @return boolean
	 */
	public static boolean isBoolean(String src) {
		if (src == null)
			return false;
		if (src.equalsIgnoreCase("true") || src.equalsIgnoreCase("false"))
			return true;
		return false;
	}

	
	/**
	 * 
	 *  描述：获取请求url中最后请求地址
	 *	 
	 *  
	 * @author Frank 
	 * @param url 访问路径
	 * @return String
	 */
	public static String getSchema(String url) {
		String schema = "";
		if (url != null && !"".equals(url)) {
			if (url.contains("?")) {
				schema = url.substring(url.lastIndexOf("/") + 1,
						url.indexOf("?"));
			} else {
				schema = url.substring(url.lastIndexOf("/") + 1);
			}

		}
		return schema;
	}
	/**
	 * 描述：字符串转化，去掉下划线并且把下划线后的第一个字符小写。 
	 * 	最后，首字母小写
	 * 
	 * 例如：tb_system_dict 转化成 tbSystemDict
	 *
	 * @author Frank  
	 * @param tableName 需要转化的字符串
	 * @return String
	 */
	public static String tableNameToLowerFirst(String tableName) {

		String[] words = tableName.split("_");

		StringBuffer sb = new StringBuffer();

		sb.append(words[0]);

		for (int i = 1; i < words.length; i++) {

			sb.append(StringUtils.capitalize(words[i]));

		}

		return sb.toString();

	}
	
	/**
	 * 描述：字符串转化，字符大写替换成下划线加小写字母。 
	 * 	最后，首字母小写
	 * 
	 * 例如：tbSystemDict 转化成 tb_system_dict
	 *
	 * @author Frank  
	 * @param tableName 需要转化的字符串
	 * @return String
	 */
	public static String toTableNameLowerFirst(String tableName) {
		StringBuffer sb = new StringBuffer();
		if (tableName != null && tableName.length() > 0) {
			// 循环处理其余字符
			for (int i = 0; i < tableName.length(); i++) {
				String s = tableName.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (!s.equals(s.toLowerCase())) {
					sb.append("_");
				}
				// 其他字符直接转成小写
				sb.append(s.toLowerCase());
			}
		}
		return sb.toString();
	}
	

	/**
 	 * 描述：字符串转化，去掉下划线并且把下划线后的第一个字符大写。 
	 * 	最后，首字母大写
	 * 
	 * 例如：tb_system_dict 转化成 TbSystemDict
	 * 
	 * @author Frank   
	 * @param tableName 需要转化的字符串
	 * @return String
	 */
	public static String tableNameToUpperFirst(String tableName) {

		String[] words = tableName.split("_");

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < words.length; i++) {

			sb.append(StringUtils.capitalize(words[i]));

		}

		return sb.toString();

	}

	/**
	 * 
	 *  描述：去除字符串尾部所有0 
	 *	 
	 *  例子：<br>
	 *
	 *  （trim0("32650000")=="3265"）
	 * @author Frank    
	 * @param str 需要转化的字符串
	 * @return String
	 */
	public static String trim0(String str) {
		String returnStr = str;
		for (int i = str.length(); i > 0; i--) {
			if (str.substring(i - 1, i).equals("0"))
				returnStr = str.substring(0, i - 1);
			else
				break;
		}
		return returnStr;
	}


	
	/**
	 * 
	 *  描述：身份证15位转18位
	 *	 
	 * @author Frank  
	 * @param sfhm 需要转化的字符串
	 * @return String
	 */
	public static String getSfhm18(String sfhm) {
		final int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		final String[] A = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
				"2" };
		int i, j, s = 0;
		String newsfhm;
		newsfhm = sfhm;
		newsfhm = newsfhm.substring(0, 6) + "19"
				+ newsfhm.substring(6, sfhm.length());
		for (i = 0; i < newsfhm.length(); i++) {
			j = Integer.parseInt(newsfhm.substring(i, i + 1)) * W[i];
			s = s + j;
		}
		s = s % 11;
		newsfhm = newsfhm + A[s];
		return newsfhm;
	}
	
	/**
	 * 把obj 转化成字符串
	 * @author Frank   
	 * @param obj 需要转化的对象
	 * @return String
	 */
	public static String filterNull(Object obj){
		return obj==null?"":obj.toString();
	}
	
	/**
	 * 首字母大写
	 * @author Frank
	 * @param str 需要转化的对象
	 * @return String
	 */
	public static String firstChar2UpperCase(String str) {
		char[] cs = str.toCharArray();
		if(Character.isLowerCase(cs[0])) {
			cs[0]-= 32;
		}
		return String.valueOf(cs);
	}
	
	/**
	 * 首字母小写
	 * @author Frank
	 * @param str 需要转化的对象
	 * @return String
	 */
	public static String firstChar2LowerCase(String str) {
		char[] cs = str.toCharArray();
		if(Character.isUpperCase(cs[0])) {
			cs[0]+= 32;
		}
		return String.valueOf(cs);
	}
	
	/**
	 *  字符串转换unicode
	 * @author wangzhuzhu 
	 * @param string 字符串
	 * @return String unicode编码
	 */
	public static String string2Unicode(String string) {
	 
	    StringBuffer unicode = new StringBuffer();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	 
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
	
	
	/**
	 * unicode 转字符串
	 * @author wangzhuzhu 
	 * @param unicode unicode字符串
	 * @return String 字符串
	 */
	public static String unicode2String(String unicode) {
	 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
	
	
	/**
	 *  字符串转换unicode 去掉
	 * @author wangzhuzhu 
	 * @param string 字符串
	 * @return String unicode编码
	 */
	public static String enstring2Unicode(String string) {
	 
	    StringBuffer unicode = new StringBuffer();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	        String ucode = Integer.toHexString(c).toString();
	        switch (ucode.length()) {
			case 1:
				unicode.append("000");
				unicode.append(ucode);
				break;
			case 2:
				unicode.append("00");
				unicode.append(ucode);
				break;
			case 3:
				unicode.append("0");
				unicode.append(ucode);
				break;
			case 4:
				unicode.append(ucode);
				break;	
			default:
		        unicode.append(ucode);
				break;
			}
	    }
	    return unicode.toString();
	}
	
	
	/**
	 * unicode 转字符串
	 * @author wangzhuzhu 
	 * @param unicode unicode字符串 不包含 
	 * @return String
	 */
	public static String deunicode2String(String unicode) {
	    int length = unicode.length();
	    StringBuffer string = new StringBuffer();
	 
	    int i = 0;
	    while (i+4<=length){
	    	// 转换出每一个代码点
	        int data = Integer.parseInt(unicode.substring(i, i+4), 16);
	        // 追加成string
	        string.append((char) data);
	        i = i+4;
	    }
	    
	    return string.toString();
	}
	
	
	public static void main(String[] args) {
	    String test = "最代码网站地址:www.zuidaima.com";
	 
	    String unicode = enstring2Unicode(test);
	     
	    String string = deunicode2String(unicode) ;
	     
	    System.out.println(unicode);
	    
	    System.out.println("1583de499ec".length());
	    
	    System.out.println("tb_baseline".length());
	 
	}
	
	
}

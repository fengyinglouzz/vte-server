package com.insight.axiswevservice;

import java.util.Random;

import com.insight.core.util.SymmetricEncoder;


public class AuthenticationCodeUtil {
	
	//生成 authenticationCode
	public static String creatAuthenticationCode(String orgCode){
		String authenticationCode = "";
		//生成十位随机数
		int flag1 = new Random().nextInt(99999);
		int flag2 = new Random().nextInt(99999);
		//将十位位随机数 和 orgCode 拼接在一起
		authenticationCode = flag1 + "" + flag2 + "-" + orgCode;
		//将拼接好的字符串 进行加密算法加密 
		return SymmetricEncoder.AESEncode(authenticationCode);
	}

	public static void main(String[] args) {
		String word = creatAuthenticationCode("001");
		System.out.println(word);
		System.out.println(SymmetricEncoder.AESDncode(word));
	}

}

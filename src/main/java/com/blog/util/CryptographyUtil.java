package com.blog.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
	
	/**
	 * md5加密
	 * @param args
	 */
	public static String md5(String str , String salt) {
		//用md5进行盐值加密后转换为字符串
		return new Md5Hash(str,salt).toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(md5("123","java1234"));
	}

}

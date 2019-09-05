package com.blog.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	/**
	 * 在字符串前加后加%
	 * @param str
	 * @return
	 */
	public static String formatLike(String str) {
		if(isNotEmpty(str)) {
			return "%"+str+"%";
		}
		return null;
	}
	
	/**
	 * 判断字符串是否不为空
	 */
	public static Boolean isNotEmpty(String str) {
		if(str != null&& !"".equals(str.trim())){
			return true;
		}
		return false;
	}
	/**
	 * 判断字符串是否为空
	 */
	public static Boolean isEmpty(String str) {
		if(str == null|| "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 对传入的字符串数组去除空格
	 */
	public static List<String> filterWhite(List<String> list){
		List<String> resultList = new ArrayList<String>();
		for(String l:list) {
			if(isNotEmpty(l)) {
				resultList.add(l);
			}
		}
		return resultList;
	}
	
}

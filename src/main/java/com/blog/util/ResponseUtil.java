package com.blog.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 写入respons 的工具类
 * @author JYS
 *
 */
public class ResponseUtil {
	
	public static void write(HttpServletResponse response,Object o ) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}

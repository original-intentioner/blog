package com.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

import com.blog.util.Const;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER,null);
		Object loginSession1 =SecurityUtils.getSubject().getSession().getAttribute(Const.CURRENT_USER);
		Object loginSession = request.getSession().getAttribute(Const.CURRENT_USER);
		System.out.println(loginSession1 == loginSession);
		System.out.println(loginSession == null);
		if(loginSession != null) {
			response.sendRedirect("/Blog/admin/main.jsp");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}

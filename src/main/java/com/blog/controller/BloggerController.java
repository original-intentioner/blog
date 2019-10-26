package com.blog.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import com.blog.util.CryptographyUtil;
/*
 * 博主登录相关
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {
	
	@Resource
	private BloggerService bloggerService;
	
	@RequestMapping("/login")
	public String login(Blogger blogger,HttpServletRequest request,HttpSession session) {
		HttpSession loginSession = request.getSession();
		
		String username = blogger.getUsername();
		String password = blogger.getPassword();
		//根据密码及对应的加密算法获取到加密后的值
		String pw = CryptographyUtil.md5(password, "java1234");
		/**从shiro中获取subject，得到一个主体对象*/
		Subject subject = SecurityUtils.getSubject();
		/**根据用户名和加密后的密码密码生成token*/
		UsernamePasswordToken token = new UsernamePasswordToken(username, pw);
		try {
			//执行登录，这步会被委托给securityManager对象去，他实现了authentictor接口进行身份验证
			//authentictor会委托给相应的authenticationStrategy进行多realm身份验证
			//authenticator会把相应的token传入realm，从realm处获取身份信息。如果没有获取到，就会登录失败
			//此处可以配置多个realm，将按照相应的顺序及策略进行访问
			subject.login(token);
			//请求后端数据库得到一个blogger对象
			blogger = bloggerService.getByUsername(username);
			SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER, blogger);
			return "redirect:/admin/main.jsp";
		}catch(Exception e) {
			e.printStackTrace();
			//此时blogger对象密码为空，用于前台数据的回显
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "用户名或密码错误");
		}
		return "login";
	}
	
	/**
	 * 关于博主
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("blogger",bloggerService.find());
		mav.addObject("mainPage","foreground/blogger/info.jsp");
		mav.addObject("pageTitle","关于博主个人博客系统");
		//设置视图名称index
		mav.setViewName("index");
		return mav;
	}
}

package com.blog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
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
	public String login(Blogger blogger,HttpServletRequest request) {
		String username = blogger.getUsername();
		String password = blogger.getPassword();
		String pw = CryptographyUtil.md5(password, "java1234");
		/**从shiro中获取subject*/
		Subject subject = SecurityUtils.getSubject();
		/**根据用户名和密码生成token*/
		UsernamePasswordToken token = new UsernamePasswordToken(username, pw);
		try {
			//传递token给shiro的realm
			subject.login(token);
			return "redirect:/admin/main.jsp";
		}catch(Exception e) {
			e.printStackTrace();
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
		mav.setViewName("index");
		return mav;
	}
}

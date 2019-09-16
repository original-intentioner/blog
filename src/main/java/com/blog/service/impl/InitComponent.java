package com.blog.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.util.Const;

@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	/**contextServlet创建之后执行此方法*/
	public void contextInitialized(ServletContextEvent sce) {
		//通过此事件对象取得取得上下文对象
		ServletContext application = sce.getServletContext();
		//博客类别
		//此处通过getBean的方式获得而不直接声明是因为在servlet创建的时候可能可能spring还没初始化完毕
		BlogTypeService blogTypeService = (BlogTypeService)applicationContext.getBean("blogTypeService");
		
		List<BlogType> blogTypeList = blogTypeService.countList();
		//将博客类型表放到全局的上下文对象中
		application.setAttribute(Const.BLOG_TYPE_COUNT_LIST, blogTypeList);
	
		//博主信息
		BloggerService bloggerService = (BloggerService)applicationContext.getBean("bloggerService");
		Blogger blogger = bloggerService.find();
		blogger.setPassword(null);
		//将博主信息放到全局的上下文对象
		application.setAttribute(Const.CURRENT_USER, blogger);
		
		//按年月分类的博客数量：
		BlogService blogService = (BlogService)applicationContext.getBean("blogService");
		List<Blog> blogCountList = blogService.countList();
		//将年月分类的博客信息放到全局上下文对象中
		application.setAttribute(Const.BLOG_COUNT_LIST, blogCountList);
		
		//友情链接
		LinkService linkService = (LinkService)applicationContext.getBean("linkService");
		List<Link> linkList = linkService.list(null);
		//将友情链接列表放到全局的上下文对象中
		application.setAttribute(Const.LINK_LIST, linkList);
	
	}

	public void contextDestroyed(ServletContextEvent sce) {
	
	}
	
	
	
}

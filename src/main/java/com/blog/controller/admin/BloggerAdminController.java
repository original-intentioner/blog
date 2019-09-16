package com.blog.controller.admin;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import com.blog.util.CryptographyUtil;
import com.blog.util.DateUtil;
import com.blog.util.ResponseUtil;

import net.sf.json.JSONObject;
/**
 * 博主相关
 * @author JYS
 *
 */
@Controller
@RequestMapping({"/admin/blogger"})
public class BloggerAdminController {
	
	@Resource
	private BloggerService bloggerService;
	/**
	 * 
	 * @param imageFile 从前端传过来的图片
	 * @param blogger  收集到的博主信息
	
	 */
	@RequestMapping({"/save"})
	public String save(@RequestParam("imageFile")MultipartFile imageFile,Blogger blogger,
			HttpServletResponse response,HttpServletRequest request) throws IllegalStateException, IOException {
		if(!imageFile.isEmpty()) {
			String filePath = request.getServletContext().getRealPath("/");
			String path = request.getServletContext().getContextPath();
			
			String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			
			blogger.setImageName(imageName);
		}
		int resultTotal = bloggerService.update(blogger);
		
		StringBuffer result = new StringBuffer();
		if(resultTotal>0) {
			result.append("<script lauguage='javascript'>alert('修改成功');</script>");

		}else {
			result.append("<script lauguage='javascript'>alert('修改失败');</script>");

		}
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 获取博主的json格式,在修改博主信息的时候用于editer编辑器中数据的回显。在初始阶段用于找到admin博主
	 * 
	 * */
	@RequestMapping("/find")
	public String find(HttpServletResponse response) throws IOException {
		Blogger blogger = (Blogger)SecurityUtils.getSubject().getSession().getAttribute(Const.CURRENT_USER);
		JSONObject  jsonObject = JSONObject.fromObject(blogger);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	/**
	 * 修改密码
	 * @throws IOException 
	 * */
	@RequestMapping({"/modifyPassword"})
	public String modifyPassword(@RequestParam("newPassword")String newPassword,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Blogger blogger = new Blogger();
		Blogger oldBlogger = (Blogger)SecurityUtils.getSubject().getSession().getAttribute(Const.CURRENT_USER);
		
		blogger.setId(oldBlogger.getId());
		//设置md5加密
		blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
		int resultTotal = bloggerService.update(blogger);
		JSONObject result = new JSONObject();
		if(resultTotal>0) {
			result.put("success", Boolean.TRUE);
			SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER,null);
		}else {
			result.put("success", Boolean.FALSE);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**用户退出*/
	@RequestMapping({"/logout"})
	public String logout(HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER, null);
		return "redirect:/login.jsp";
	}
	
}

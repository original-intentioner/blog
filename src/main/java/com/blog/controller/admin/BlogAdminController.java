package com.blog.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.util.DateJsonValueProcessor;
import com.blog.util.ResponseUtil;
import com.blog.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 博客信息管理
 * @author JYS
 *
 */
@Controller
@RequestMapping({"/admin/blog"})
public class BlogAdminController {
	
	@Resource
	private BlogService blogService;
	
	private BlogIndex blogIndex = new BlogIndex();
	
	/**
	 * 保存一条博客信息
	 * @throws IOException 
	 */
	@RequestMapping({"/save"})
	public String save(Blog blog,HttpServletResponse response) throws IOException {
		Integer resultTotal = 0;
		if(blog.getId() == null) {	//判断是新发布还是更新
			resultTotal = blogService.add(blog);
			//将博客添加到lucene中
			blogIndex.addIndex(blog);
		}else {
			resultTotal = blogService.update(blog);
			//将博客在lucene中更新
			blogIndex.updateIndex(blog);
		}
		
		JSONObject result = new JSONObject();
		
		if(resultTotal > 0) {	//改变了数据库说明添加或者修改完毕
			result.put("success",Boolean.valueOf(true));
		}else {
			result.put("success",Boolean.valueOf(false));
		}
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 查询博客信息列表
	 * page,rows，Blog是通过easyUI传过来的，博客标题由搜索框传过来
	 * @throws IOException 
	 */
	@RequestMapping({"/list"})
	public String list(@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,Blog blog,
			HttpServletResponse response) throws IOException {
		//传入当前页面与每个页面显示的个数
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size",pageBean.getPageSize());
		//获得标题后，对其前后添加%，便于接下来的查询
		map.put("title", StringUtil.formatLike(blog.getTitle()));
		//分页查询博客信息列表
		List<Blog> list = blogService.list(map);
		//获取共有多少条博客信息
		Long total = blogService.getTotal(map);
		//封装到json中
		JSONObject result = new JSONObject();
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list,config);
		//封装的记录
		result.put("rows", jsonArray);
		//easyUI中显示共多少条记录，如果没有total值的话会显示NaN。
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	/**
	 * 根据主键查询一条博客信息
	 * @throws IOException 
	 * */
	@RequestMapping({"/findById"})
	public String findById(@RequestParam("id")String id,HttpServletResponse response) throws IOException {
		Blog blog = blogService.findById(Integer.parseInt(id));
		JSONObject jsonObject = JSONObject.fromObject(blog);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	/**
	 * 删除博客信息
	 * @throws Exception 
	 * */
	@RequestMapping({"/delete"})
	public String delete(@RequestParam("ids")String ids,HttpServletResponse response) throws Exception {
		//前台传过来的数组格式的字符串，每个id之间用逗号隔开
		String[] idsStr = ids.split(",");
		for(int i=0;i<idsStr.length;i++) {
			//根据主键删除对应的博客
			blogService.delete(Integer.parseInt(idsStr[i]));
			//从lucene中删除对应的博客
			blogIndex.deleteIndex(idsStr[i]);
		}
		JSONObject result = new JSONObject();
		result.put("success", Boolean.valueOf(true));
		ResponseUtil.write(response, result);
		return null;
	}
	
	
}

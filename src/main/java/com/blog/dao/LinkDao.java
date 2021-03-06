package com.blog.dao;

import java.util.List;
import java.util.Map;

import com.blog.entity.Link;

public interface LinkDao {
	
	/**根据id查询一条友情链接*/
	public Link findById(Integer id );
	
	/**不固定参数查询友情链接表*/
	public List<Link> list(Map<String,Object> paramMap);
	
	/**不固定参数查询友情链接的数量*/
	public Long getTotal(Map<String,Object> paramMap);
	
	/**添加一条友情链接,返回执行的条数*/
	public Integer add(Link link);
	
	/**删除一条友情链接*/
	public Integer delete(Integer id);
	
	/**修改一条友情链接*/
	public Integer update(Link link);
}

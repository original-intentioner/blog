package com.blog.service;



import org.apache.ibatis.annotations.Param;

import com.blog.entity.Blogger;

public interface BloggerService {
	/**根据登录名查询博主对象*/
	public Blogger getByUsername(@Param("username")String username);

	/**更新博主对象*/
	public Integer update(Blogger blogger);
	
	/**查询博主*/
	public Blogger find();
}

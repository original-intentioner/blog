package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.entity.Blog;
import com.blog.entity.Comment;

public interface CommentService {
	/**添加一条评论*/
	int add(Comment comment);
	/**更新一条评论*/
	int update(Comment comment);
	/**评论查询*/
	List<Comment> list(Map<String,Object> map);
	/**评论数量*/
	Long getTotal(Map<String,Object> map);
	/**删除评论*/
	Integer delete(Integer id);
}

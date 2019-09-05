package com.blog.dao;

import java.util.List;
import java.util.Map;

import com.blog.entity.Comment;

/**
 * 评论管理
 * @author JYS
 *
 */
public interface CommentDao {
	
	/**添加一条评论*/
	Integer add(Comment comment);
	/**更新一条评论*/
	Integer update(Comment comment);
	/**评论查询*/
	List<Comment> list(Map<String,Object> map);
	/**评论数量*/
	Long getTotal(Map<String,Object> map);
	/**删除评论*/
	Integer delete(Integer id);
	/**根据博客id删除评论*/
	Integer deleteByBlogId(Integer id);
}

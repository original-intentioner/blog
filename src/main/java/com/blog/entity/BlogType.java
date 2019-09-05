package com.blog.entity;

import java.io.Serializable;
/*博客类型*/
public class BlogType implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String typeName;
	/**博客的序号*/
	private Integer orderNo;
	/**该类型下博客的数量*/
	private Integer blogCount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String gettypeName() {
		return typeName;
	}
	public void settypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	
	
}

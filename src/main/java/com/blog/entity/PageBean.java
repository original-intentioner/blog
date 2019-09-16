package com.blog.entity;

public class PageBean {
	/**当前的页数，从1开始*/
	private int page;
	/**页面的大小，即页面可以存放的数据数*/
	private int pageSize;
	/**从第几条数据开始查询显示*/
	private int start;
	/**传入当前页面跟每个页面存放的数量*/
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStart() {
		return (this.page - 1) * (this.pageSize);
	}
	
}

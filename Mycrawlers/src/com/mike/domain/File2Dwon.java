package com.mike.domain;

public class File2Dwon {
/*	public File2Dwon(String url, String path) {
		this.url = url;
		this.path = path;
	}*/
	private String url;
	private String path;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "源url：     "+ this.getUrl()+"存如地址名：      "+this.getPath();
	}
	
	
	

}

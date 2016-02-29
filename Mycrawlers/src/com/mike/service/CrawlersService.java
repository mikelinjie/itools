package com.mike.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mike.dao.FileDownDao;
import com.mike.domain.File2Dwon;
import com.mike.utils.CrawlerUtils;

public class CrawlersService {
	// http://archive.apache.org/dist/accumulo/1.4.0/
	private static List<File2Dwon> fdList = new ArrayList<File2Dwon>();
	private static Properties prop=null;
	static{
		InputStream in = CrawlersService.class.getClassLoader().getResourceAsStream("property.properties");
		prop=new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		add2Mysql();
		
	}
	public static void downLoad(){
		List<File2Dwon> list =null;
		try {
			list=FileDownDao.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(File2Dwon fd: list){
			try {
				CrawlerUtils.downloadFile(fd.getUrl(),fd.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(fd.getUrl()+"下载成功");
		}
		
	}
	

	

	public static void add2Mysql(){
	
		String url=prop.getProperty("sourceUrl");
		System.out.println("*********"+url);
		
		CrawlersService cs=new CrawlersService();
		cs.function(url);
	}

	public void function(String url) {
		List<String> list = CrawlerUtils.getDirList(url);
	//	System.out.println(list);
		
		for (String childUrl : list) {
			if (childUrl.contains("/")) {
				function(url + childUrl);
			}
			if(!childUrl.trim().contains("/")){
			String totalUrl = url + childUrl;
		//	System.out.println("pre :    "+url+"  l:       "+childUrl);
			String path = totalUrl.replace(prop.getProperty("sourceUrl"),
					"E:/apache");
			//add
			 File2Dwon fd= new File2Dwon();
			fd.setUrl(totalUrl);
			fd.setPath(path);
			try {
				FileDownDao.add(fd);
			} catch (SQLException e) {
				System.out.println(fd.getUrl()+"加入数据库出错");
				e.printStackTrace();
			}
			System.out.println(fd.getUrl()+"加入数据库成功");
			}
		}
	}

	public void printAll() {
		for (File2Dwon fd : fdList) {
			System.out.println(fd);
		}
	}

}

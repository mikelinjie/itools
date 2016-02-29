package com.mike.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mike.domain.File2Dwon;
import com.mike.utils.CrawlerUtils;

public class FileDownDao {
	public static void down(File2Dwon fd) throws IOException {
		CrawlerUtils.downloadFile(fd.getUrl(), fd.getPath());
	}

	public static void down(List<File2Dwon> list) throws IOException {
		for (File2Dwon fd : list) {
			FileDownDao.down(fd);
		}
	}

	public static void add(File2Dwon fd) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into apache (url,path) values(?,?)";
		Object params[] = { fd.getUrl(), fd.getPath() };
		qr.update(sql, params);
	}

/*	public void find() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from employee where id=?";
		Object params[] = { 18 };
		File2Dwon emp = (File2Dwon) qr.query(sql, params, new BeanHandler(
				File2Dwon.class));

	}*/
	
	public static List getAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from apache";
		List list =(List) qr.query(sql, new BeanListHandler(File2Dwon.class));
		return list;
	}
}

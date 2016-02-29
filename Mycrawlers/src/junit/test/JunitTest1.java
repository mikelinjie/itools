package junit.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.mike.dao.FileDownDao;
import com.mike.domain.File2Dwon;
import com.mike.utils.CrawlerUtils;

public class JunitTest1 {

	@Test
	public void test1() {
		// http://archive.apache.org/dist/abdera/1.0/apache-abdera-1.0-src.tar.gz
		String url = "http://archive.apache.org/dist/abdera/1.0/apache-abdera-1.0-src.tar.gz";
		String pathname = "E:/temp/apache-abdera-1.0-src.tar.gz";
	}

	@Test
	public void test2() {
		// http://archive.apache.org/dist/abdera/1.0/apache-abdera-1.0-src.tar.gz
		String url = "http://archive.apache.org/dist/abdera/1.0/apache-abdera-1.0-src.tar.gz";
		String path = "E:/temp/wedqwe/qweqwe/apache-abdera-1.0-src.tar.gz";
		try {
			CrawlerUtils.downloadFile(url, path);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void RegexTest1() throws IOException {

		String url = "http://archive.apache.org/dist/";
		List list = CrawlerUtils.getDirList(url);
		System.out.println(list);

	}

	@Test
	public void dbDaoTestadd() throws SQLException {
		FileDownDao fdd = new FileDownDao();
		File2Dwon fd=new File2Dwon();
		fd.setUrl("sdefsefw");
		fd.setPath("wqed qwrfwetg4wrgtesdrfgws ");
		FileDownDao.add(fd);
	}
	@Test
	public void dbDaoTestquery() throws SQLException {
		FileDownDao.getAll();;
	}

}

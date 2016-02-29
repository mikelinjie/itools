package junit.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mike.utils.CrawlerUtils;

public class JunitTest {
	String Context=null;
	@Before
	public void before(){
		String url="http://archive.apache.org/dist/";
		
		
		Context=CrawlerUtils.getURLContent(url);
	}
	
	
	@Test
	public void test1() throws IOException {


		
		OutputStream out = new BufferedOutputStream( new FileOutputStream(new File("E:/1.html")));
	
		out.write(Context.getBytes());
		System.out.println(Context);
		out.flush();
		out.close();
		
	}
	@After
	public void after(){
		Context=null;
	}
	
}

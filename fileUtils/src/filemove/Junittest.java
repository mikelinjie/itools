package filemove;

import java.io.File;

import org.junit.Test;

public class Junittest {

	@Test
	public void test1() {
		String src = "E:/JFoum-test/webapps/JForum/WEB-INF/classes";
		String dist = "E:/a1/";
		File distFile = new File(dist);
		if (distFile.exists()) {
			System.out.println("yes");
		}
		if (distFile.isFile()) {
			System.out.println("yes");
		}
		FileOperator.deleteFile(dist);

	}

	public void test2() {
		String src = "E:/JFoum-test/webapps/JForum/WEB-INF/classes";
		String dist = "E:/a2";
		File distFile = new File(dist);
		if (distFile.exists()) {
			System.out.println("yes");
		}
		if (distFile.isFile()) {
			System.out.println("yes");
		}
		FileOperator.deleteFile(dist);

	}
	@Test
	public void test3() {
		String src = "E:/JFoum-test/webapps/JForum/WEB-INF/classes";
		String dist = "D:/apache-tomcat-7.0.59/webapps/JForum/WEB-INF";
		File file = new File(src);
		File dir = new File(dist);
		FileOperator.deleteFile(dist+File.separator+file.getName());
		boolean success = file.renameTo(new File(dir, file.getName()));
		System.out.println(success);
	}
	
	@Test
	public void test4(){
		String src = "E:/JFoum-test/webapps/JForum/WEB-INF/classes";
		String dist = "D:/apache-tomcat-7.0.59/webapps/JForum/WEB-INF/classes";
		FileOperator.deleteFile("D:/apache-tomcat-7.0.59/webapps/JForum/WEB-INF/classes");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileOperator.moveDirectory(src, dist);
	}
}

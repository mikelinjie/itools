package filemove;

public class Main {

	public static void main(String[] args) {
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

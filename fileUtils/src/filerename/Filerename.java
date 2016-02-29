package filerename;

import java.io.File;

public class Filerename {
	public static void main(String[] args) {
		File file = new File("F:\\spring\\尚学堂Spring\\spring");
		File files[] = file.listFiles();
		String str = "";
		for (File f : files) {
			String filename = f.getName();
			System.out.println(filename.substring(filename.lastIndexOf(".")));
			if(filename.substring(filename.lastIndexOf(".")).trim().endsWith(".avi"))
			{
				String newfilename= filename.replaceAll("尚学堂\\(中国最专业JAVA培训机构\\)_Spring_", "");
				f.renameTo(new File(f.getParentFile()+f.separator+newfilename));
			}	
		}
	}
}

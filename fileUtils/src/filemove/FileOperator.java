package filemove;

import java.io.File;

class FileOperator {
/*	public static void copyFile(String src, String dist) {
		File srcFile= new File(src);
		File distFile=new File(dist);
		if(!srcFile.exists()){
			System.out.println("复制的源文件不存在");
			return;
		}
		if(srcFile.isDirectory()){}
		if(srcFile.isFile()){}
	}
	
	public static void copySingleFile(String src, String dist){
		File srcFile= new File(src);
		File distFile=new File(dist);
		if(!srcFile.exists()){
			System.out.println("复制的源文件不存在");
			return;
		}
		if(distFile.exists()){
			deleteFile(dist);
		}
	}
	*/
	
	//可以是文件或者文件夹 
	public static void deleteFile(String filepath){
		File f=new File(filepath);
		if(!f.exists()){
			System.out.println("文件目录不存在.......");
			return ;
		}
		if(f.isDirectory()){
			String[] childFiles = f.list(); 
			for (int i = 0; i < childFiles.length; i++) {
				String childFile = filepath+File.separator+childFiles[i];
				deleteFile(childFile);
			}
			f.delete();
			
		}
		
		//if意思下
		if(f.isFile()){
			f.delete();
		}
	}
	
	/** 
	* 移动文件 
	* @param srcFileName 	源文件完整路径
	* @param destDirName 	目的目录完整路径
	* @return 文件移动成功返回true，否则返回false 
	*/  
	public static boolean moveFile(String srcFileName, String destDirName) {
		
		File srcFile = new File(srcFileName);
		if(!srcFile.exists() || !srcFile.isFile()) 
		    return false;
		
		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();
		
		return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
	}
	
	/** 
	* 移动目录 
	* @param srcDirName 	源目录完整路径
	* @param destDirName 	目的目录完整路径
	* @return 目录移动成功返回true，否则返回false 
	*/  
	public static boolean  moveDirectory(String srcDirName, String destDirName) {
		
		File srcDir = new File(srcDirName);
		if(!srcDir.exists() || !srcDir.isDirectory())  
			return false;  
	   
	   File destDir = new File(destDirName);
	   if(!destDir.exists())
		   destDir.mkdirs();
	   
	   /**
	    * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹
	    * 注意移动文件夹时保持文件夹的树状结构
	    */
	   File[] sourceFiles = srcDir.listFiles();
	   for (File sourceFile : sourceFiles) {
		   if (sourceFile.isFile())
			   moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
		   else if (sourceFile.isDirectory())
			   moveDirectory(sourceFile.getAbsolutePath(), 
					   destDir.getAbsolutePath() + File.separator + sourceFile.getName());
		   else
			   ;
	   }
	   return srcDir.delete();
	}
	
}
package md5tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String string2md5(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("md5");
		byte[] byteArray = str.getBytes("UTF-8");
		md5.update(byteArray);
		return getFormattedText(md5.digest());
	}

	public static String file2md5(File file) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("md5");
		InputStream in = new FileInputStream(file);
		byte[] buff = new byte[102400];
		int len;
		while ((len = in.read(buff)) > 0) {
			md5.update(buff, 0, len);
		}
		return getFormattedText(md5.digest());
	}

	private static String getFormattedText(byte[] byteArray) {
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
}
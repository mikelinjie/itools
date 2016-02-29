package P1;

import java.io.IOException;
import java.util.Properties;

public class propertymgr {

	static	Properties props =new Properties();
	public static String getProperty(String key) {
	
		try {
			props.load(propertymgr.class.getClassLoader().getSystemResourceAsStream("config/tanks.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return props.getProperty(key);
	}
}

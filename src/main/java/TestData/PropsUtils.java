package TestData;

import java.io.FileInputStream;
import java.util.Properties;


public class  PropsUtils{
	
	public static Properties loadProps() {
		Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream("Conf.properties");
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static int getDefaultTimeout() {
		return Integer.parseInt(loadProps().getProperty("DefaultTimeout"));
	}
	
	
}

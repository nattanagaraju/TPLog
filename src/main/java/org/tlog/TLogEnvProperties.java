package org.tlog;

import java.util.Properties;

public class TLogEnvProperties {
	private static Properties prop = new Properties();
	static{
		prop.setProperty("env", "cloud");
		try {
			prop.load(TLogEnvProperties.class.getClassLoader().getResourceAsStream("tplog.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getProp(String propName){
		return prop.getProperty(propName);
	}
}

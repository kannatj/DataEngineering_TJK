package com.kanna.football;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

	static Properties prop = new Properties();

	public static Properties loadProperties() throws IOException {

		try {
			//FileReader file = new FileReader("./config/configuration.properties");
			//InputStream stream = GetProperties.class.getResourceAsStream("FootBall-API-Data/config/configuration.properties");
			prop.load(GetProperties.class.getClass().getResourceAsStream("/configuration.properties"));

		} catch (FileNotFoundException e) {
			System.out.println("File not found in the given path");
			e.printStackTrace();
		}
		
		return prop;

	}
	
	public static String getValue(String key) {
		return prop.getProperty(key);
	}
}

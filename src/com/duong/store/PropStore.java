package com.duong.store;

import java.util.Properties;

import com.duong.annotation.PropertiesAnnotation;

public class PropStore {
	@PropertiesAnnotation(file = "app.properties",autoLoad=false)
	private static Properties appProps;
	
	public static Properties getAppProps() {
		return appProps;
	}
	public static void setAppProps(Properties appProps) {
		PropStore.appProps = appProps;
	}
	
	
}

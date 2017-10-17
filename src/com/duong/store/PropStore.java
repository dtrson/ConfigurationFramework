package com.duong.store;

import java.util.Properties;

import com.duong.annotation.ConfigData;

public class PropStore {
	@ConfigData(file = "app.properties",autoLoad=false, key = "source")
	private static Properties appProps;
	
	public static Properties getAppProps() {
		return appProps;
	}
	public static void setAppProps(Properties appProps) {
		PropStore.appProps = appProps;
	}
	
	
}

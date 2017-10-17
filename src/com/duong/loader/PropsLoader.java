package com.duong.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import com.duong.annotation.ConfigData;
import com.duong.store.PropStore;

public class PropsLoader {

	public static void loadAnnotation() throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Field[] fields = PropStore.class.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(ConfigData.class)){
				ConfigData configData = field.getAnnotation(ConfigData.class);
				loadPropertiesAndWatch(field.getName(),configData);
			}
			else{
				System.out.println("None of configuration annotation found!!!");
			}
		}
	}
	private static void loadPropertiesAndWatch(String fieldName, ConfigData configData) throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		String propsFile = configData.file();
		String key = configData.key();
		System.out.println(propsFile);
		loadProperties(configData, fieldName,propsFile, key);
		if(configData.autoLoad()){
			PropsWatcherTask.watch(fieldName,propsFile, configData);
		}
		
	}
	protected static void loadProperties(ConfigData configData, String fieldName, String propsFile, String key) throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		String setterName = "set" + Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
		Method setter = PropStore.class.getDeclaredMethod(setterName, Properties.class);
		Properties props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try(InputStream resourceStream = loader.getResourceAsStream(propsFile)) {
		    props.load(resourceStream);
		    if(props.getProperty(key)==null || props.getProperty(key)==""){
		    	props.setProperty(key, configData.defaultValue());
		    }
		    setter.invoke(null, props);
		    
		}
	}
}

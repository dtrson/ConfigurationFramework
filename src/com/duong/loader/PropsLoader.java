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

import com.duong.annotation.PropertiesAnnotation;
import com.duong.store.PropStore;

public class PropsLoader {
	private static final String SYSTEM_VAR = "APP_PROPS";
	public static void load() throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Field[] fields = PropStore.class.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(PropertiesAnnotation.class)){
				PropertiesAnnotation propsAnnotation = field.getAnnotation(PropertiesAnnotation.class);
				loadPropertiesAndWatch(field.getName(),propsAnnotation);
			}
			else{
				System.out.println("None of configuration annotation found!!!");
			}
		}
	}
	private static void loadPropertiesAndWatch(String fieldName, PropertiesAnnotation propAnnotation) throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		String propsFile = System.getenv("APP_PROPS") + File.separator + propAnnotation.file();
		System.out.println(propsFile);
		loadProperties(fieldName,propsFile);
		if(propAnnotation.autoLoad()){
			PropsWatcherTask.watch(fieldName,propsFile, propAnnotation);
		}
		
	}
	protected static void loadProperties(String fieldName, String propsFile) throws NoSuchMethodException, SecurityException, FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		String setterName = "set" + Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
		Method setter = PropStore.class.getDeclaredMethod(setterName, Properties.class);
		Properties props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try(InputStream resourceStream = loader.getResourceAsStream(propsFile)) {
		    props.load(resourceStream);
		    setter.invoke(null, props);
		}
		
//		props.load(new FileInputStream(new File(propsFile)));
		

	}
}

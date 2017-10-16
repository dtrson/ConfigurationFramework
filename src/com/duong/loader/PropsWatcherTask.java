package com.duong.loader;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;



import com.duong.annotation.PropertiesAnnotation;

public class PropsWatcherTask extends TimerTask {

	private String propsFile;
	private long lastMod;
	private String fieldName;
	private static final Timer TIMER = new Timer();
	private static final long INITIAL_DELAY = 1000 * 60 * 5;
    private static final long INTERVAL  = 1000 * 60 * 5;
	
    
    private PropsWatcherTask(long lastMod, String fieldName, String propsFileName) {
        this.propsFile = propsFileName;
        this.lastMod = lastMod;
        this.fieldName = fieldName;
    }
    
	protected static void watch(String fieldName, String propsFile, PropertiesAnnotation propAnnotation) {
		// TODO Auto-generated method stub
		File tempPropsFile = new File(propsFile);
		long tempLastMod = tempPropsFile.lastModified();
		TIMER.scheduleAtFixedRate(new PropsWatcherTask(tempLastMod, fieldName, propsFile), INITIAL_DELAY, INTERVAL);

	}

	@Override
	public void run() {
		//check modified time and set new one.
        long newModTime = new File(propsFile).lastModified();
        if( newModTime > lastMod ) {
            try {
                PropsLoader.loadProperties(fieldName, propsFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.lastMod = newModTime;
        }
		
	}

}

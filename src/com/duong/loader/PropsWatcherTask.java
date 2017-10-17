package com.duong.loader;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;



import com.duong.annotation.ConfigData;

public class PropsWatcherTask extends TimerTask {

	private String propsFile;
	private long lastMod;
	private String fieldName;
	private ConfigData configData;
	private String key;
	private static final Timer TIMER = new Timer();
	private static final long INITIAL_DELAY = 1000 * 60 * 5;
    private static final long INTERVAL  = 1000 * 60 * 5;
	
    
    private PropsWatcherTask(long lastMod, String fieldName, String propsFileName, ConfigData configData) {
        this.propsFile = propsFileName;
        this.lastMod = lastMod;
        this.fieldName = fieldName;
        this.configData = configData;
        this.key = configData.key();
    }
    
	protected static void watch(String fieldName, String propsFile, ConfigData configData) {
		// TODO Auto-generated method stub
		File tempPropsFile = new File(propsFile);
		long tempLastMod = tempPropsFile.lastModified();
		TIMER.scheduleAtFixedRate(new PropsWatcherTask(tempLastMod, fieldName, propsFile, configData), INITIAL_DELAY, INTERVAL);

	}

	@Override
	public void run() {
		//check modified time and set new one.
        long newModTime = new File(propsFile).lastModified();
        if( newModTime > lastMod ) {
            try {
                PropsLoader.loadProperties(configData, fieldName, propsFile, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.lastMod = newModTime;
        }
		
	}

}

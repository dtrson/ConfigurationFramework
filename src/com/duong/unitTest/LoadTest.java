package com.duong.unitTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.duong.loader.PropsLoader;
import com.duong.store.PropStore;

public class LoadTest {

	@Before
    public void load() {
        try {
            PropsLoader.loadAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@Test
    public void testProperties() throws Exception{
        Assert.assertEquals("default value", PropStore.getAppProps().getProperty("source"));
       
    }

}

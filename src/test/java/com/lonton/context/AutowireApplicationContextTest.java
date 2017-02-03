package com.lonton.context;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.bean.BeanA;
import com.lonton.bean.ComponentBean;
import com.lonton.bean.DependComponentBean;
import com.lonton.beans.factory.DefaultListableBeanFactory;
import com.lonton.core.io.FileSystemResource;
import com.lonton.ioc.annotation.Component;

public class AutowireApplicationContextTest {

	private static Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);
	DefaultListableBeanFactory defaultListableBeanFactory;
	static{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@Test
	public void testAutowireApplicationContext(){
		//注入一个resource
		FileSystemResource fsr=new FileSystemResource("src\\resource\\test.xml");
		try {
			 AutowireApplicationContext aac=
					new AutowireApplicationContext(fsr);
			 ComponentBean cb=aac.getBean("ComponentBean", ComponentBean.class);
			 DependComponentBean dcb=aac.getBean("DependComponentBean",DependComponentBean.class);
			 log.info(cb.toString());
			 log.info(dcb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.lonton.beans.factory;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.bean.BeanA;
import com.lonton.core.io.FileSystemResource;
import com.lonton.exception.BeansException;

public class DefaultListableBeanFactoryTest {

	private static Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);
	DefaultListableBeanFactory defaultListableBeanFactory;
	static{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@Test
	public void testDefaultListableBeanFactoryResource(){
		//注入一个resource
		FileSystemResource fsr=new FileSystemResource("src\\resource\\test.xml");
		try {
			 defaultListableBeanFactory=
					new DefaultListableBeanFactory(fsr);
			BeanA a=(BeanA)defaultListableBeanFactory.getBean("beana");
			log.info(a.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDefaultListableBeanFactoryString() {
		try {
			 defaultListableBeanFactory=
					new DefaultListableBeanFactory("src\\resource\\test.xml");
			BeanA a=(BeanA)defaultListableBeanFactory.getBean("beana");
			log.debug(a.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testRemoveBeanDefinition() throws Exception{
		defaultListableBeanFactory.removeBeanDefinition("beana");
		BeanA a = defaultListableBeanFactory.getBean("beana",BeanA.class);
		log.info("Bean已经被移除："+(a==null));
	}
	
	@Test
	//TODO
	public void testGetBean() {
		try {
			BeanA a=defaultListableBeanFactory.getBean("beana",BeanA.class);
			log.debug(a.toString());
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
}

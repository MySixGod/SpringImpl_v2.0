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
	@Test
	@Before
	public void testDefaultListableBeanFactoryResource(){
		PropertyConfigurator.configure("log4j.properties");
		//注入一个resource
		FileSystemResource fsr=new FileSystemResource("E:\\公司文档\\test.xml");
		try {
			 defaultListableBeanFactory=
					new DefaultListableBeanFactory(fsr);
			BeanA a=(BeanA)defaultListableBeanFactory.getBean("beana");
			log.debug(a.toString());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Test
	public void testDefaultListableBeanFactoryString() {
		try {
			 defaultListableBeanFactory=
					new DefaultListableBeanFactory("E:\\公司文档\\test.xml");
			BeanA a=(BeanA)defaultListableBeanFactory.getBean("beana");
			log.debug(a.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	
	@Test
	public void testRemoveBeanDefinition() {
		
	}
	
	@Test
	//TODO
	public void testGetBean() {
		try {
			BeanA a=defaultListableBeanFactory.getBean("beana",BeanA.class);
			log.debug(a.toString());
		} catch (BeansException e) {
			// 
			e.printStackTrace();
		}
	}
	
	

}

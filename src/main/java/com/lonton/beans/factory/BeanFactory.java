package com.lonton.beans.factory;

import com.lonton.exception.BeansException;
import com.lonton.exception.NoSuchBeanDefinitionException;
/*
 * @author chenwentao
 * @since  2017-01-25
 * @description  定义容器的基本雏形
 */
public interface BeanFactory {
	
	 Object getBean(String name) throws BeansException;
	 
	 <T> T getBean(String name,Class <T> requiredType) throws BeansException;
	 
	 boolean containsBean(String name);
	 
	 boolean isSingleton(String name) throws NoSuchBeanDefinitionException;
	 
}

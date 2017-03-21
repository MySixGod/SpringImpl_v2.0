package com.lonton.beans.factory;

import com.lonton.beans.config.BeanDefinition;
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
	 
	 boolean containsBeanDefintion(String beanDefinitionName);
	 
	 boolean isSingleton(String beanDefinitionName) throws NoSuchBeanDefinitionException;
	 
	 BeanDefinition getBeanDefinition(String beanDefinitionName);
}

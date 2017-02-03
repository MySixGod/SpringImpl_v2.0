package com.lonton.beans.factory;

import com.lonton.exception.BeansException;
import com.lonton.exception.NoSuchBeanDefinitionException;
/*
 * @author chenwentao
 * @since  2017-01-25
 */
public abstract class AbstractBeanFactory implements BeanFactory {

	@Override
	public Object getBean(String name) throws BeansException {
		return null;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType)
			throws BeansException {
		return null;
	}

	@Override
	public boolean containsBean(String name){
		try {
			if(getBean(name)!=null){
				return true;
			}
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
	  
		return false;
	}

}

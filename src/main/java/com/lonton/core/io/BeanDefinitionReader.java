package com.lonton.core.io;

import com.lonton.beans.factory.support.BeanDefinitionRegistry;

public interface BeanDefinitionReader {
	
	BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	ResourceLoader getResourceLoader();
	
	int loadBeanDefinitions(Resource... resources) throws Exception;
	
	int loadBeanDefinitions(Resource resource) throws  Exception;
	
}

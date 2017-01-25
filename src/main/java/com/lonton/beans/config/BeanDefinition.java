package com.lonton.beans.config;

import com.lonton.enums.ConfigurableBeanFactory;

public interface BeanDefinition {
	
	String SCOPE_SINGLETON =ConfigurableBeanFactory.SCOPE_SINGLETON.getBeanScope();
	String SCOPE_PROTOTYPE=ConfigurableBeanFactory.SCOPE_PROTOTYPE.getBeanScope();
	Object object=null;
	
	String getScope();
	void setScope(String scope);
	
	boolean isSingleton();
	String getDescription();
	
	Object getObject();	
}

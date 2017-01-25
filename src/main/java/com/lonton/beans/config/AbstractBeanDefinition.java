package com.lonton.beans.config;

public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	private final String SCOPE_DEFAULT="";
	private String scope = SCOPE_DEFAULT;
	@Override
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String getScope() {
		return this.scope;
	}


	@Override
	public boolean isSingleton() {
		
		return false;
	}

	@Override
	public String getDescription() {	
		return null;
	}
	
}

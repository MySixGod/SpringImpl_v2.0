package com.lonton.beans.config;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	private final String SCOPE_DEFAULT="";
	private String scope = SCOPE_DEFAULT;
	/*
	 * (non-Javadoc)
	 * @see com.lonton.beans.config.BeanDefinition#setScope(java.lang.String)
	 * @param bean的作用域
	 */
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

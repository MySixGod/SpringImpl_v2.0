package com.lonton.beans.config;

import java.util.List;

/*
 * @author cwt
 * @since  2017-01-25
 */
public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	private final String SCOPE_DEFAULT="single";
	private String scope = SCOPE_DEFAULT;
	List<BeanDefinition> dependentBeanDefinitions;
	
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
	
	//获取依赖的beanDefinition
	@Override
    public List<BeanDefinition> getDependentBeanDefinitions() {
        return dependentBeanDefinitions;
    }
	
	//添加beanDefinition依赖
    public void addDependentBeanDefinition(BeanDefinition beanDefinition) {
        dependentBeanDefinitions.add(beanDefinition);
    }
	  
}

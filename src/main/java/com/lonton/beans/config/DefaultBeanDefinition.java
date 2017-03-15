package com.lonton.beans.config;
/*
 * @author chenwentao
 * @since  2017-01-25
 */
public class DefaultBeanDefinition extends AbstractBeanDefinition{
	
	//給他加一個BeanName
	private String BeanName;
	protected Object object;

	/* (non-Javadoc)
	 * @see com.lonton.beans.config.AbstractBeanDefinition#getDescription()
	 */
	@Override
	public String getDescription() {
		return BeanName;
	}
	
	public DefaultBeanDefinition(String BeanName,Object object)
	{
		this.BeanName=BeanName;
		this.object=object;
	}
	
	public DefaultBeanDefinition()
	{
		
	}
	
	public String getBeanName() {
		return BeanName;
	}
	
	/**
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		BeanName = beanName;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

}

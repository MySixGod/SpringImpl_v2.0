package com.lonton.beans.factory;

/*
 * @author chenwentao
 * @since  2017-01-25
 * 带有自动注入功能的beanfactory
 */
public interface AutowireCapableBeanFactory extends BeanFactory{
	
	//拥有自动注入bean的功能，检查Map集合，如果有@Autowire注解，进行自动注入
	 void AutowireBean();
}

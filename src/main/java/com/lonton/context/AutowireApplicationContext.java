package com.lonton.context;

import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.anntotion.handle.AutowiredHandle;
import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.factory.AutowireCapableBeanFactory;
import com.lonton.beans.factory.DefaultListableBeanFactory;
import com.lonton.beans.factory.support.BeanDefinitionRegistry;
import com.lonton.core.io.AnnotationBeanDefinitionReader;
import com.lonton.core.io.Resource;

/*
 * @author chenwentao
 * @since  2017-01-25
 * 给AutowireApplicationContext增加自动注入的功能
 */
public class AutowireApplicationContext extends DefaultListableBeanFactory implements
AutowireCapableBeanFactory{

	private static Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);
	static{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	public AutowireApplicationContext(Resource resource) throws Exception
	{
		super(resource);
		refresh();
	}

	public AutowireApplicationContext(String location) throws Exception
	{
		super(location);
		refresh();
	}
	
	/*
	 * 继承ComponentHandle，拥有解析@component注解的能力
	 */
	private class AutowireAnnotationBeanDefinition extends AnnotationBeanDefinitionReader{
		public AutowireAnnotationBeanDefinition(BeanDefinitionRegistry registry)
		{
			super(registry);
		}
	}
	@Override
	protected void refresh() throws Exception {
		int count=new AutowireAnnotationBeanDefinition(this).doLoadBeanDefinitions(resource);
		AutowireBean();
		log.info("一共初始化了:"+count+"个bean");
	}

	//在这里将带有@autowired注解的方法注入属性值
	@Override
	public void AutowireBean() {
		//遍历所有的Bean
		for(Entry<String, BeanDefinition> bean:beanDefinitionMap.entrySet()){
			String BeanName=bean.getKey();
			//ERROR
		    Class<?> BeanClass=bean.getValue().getBeanClass();
			try {
				AutowiredHandle.AutowiredHandleMethod(BeanClass, this, BeanName);
			} catch (Exception e) {
				log.error("自动注入异常");
			}
		}	
	}
}

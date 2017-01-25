package com.lonton.context;

import com.lonton.anntotion.handle.ComponentHandle;
import com.lonton.beans.factory.AutowireCapableBeanFactory;
import com.lonton.beans.factory.DefaultListableBeanFactory;
import com.lonton.core.io.Resource;

/*
 * @author
 * 暂时只给AutowireApplicationContext增加自动注入的功能
 */
public class AutowireApplicationContext extends DefaultListableBeanFactory implements
AutowireCapableBeanFactory{

	public AutowireApplicationContext(Resource resource) throws Exception
	{
		super(resource);
		
	}

	public AutowireApplicationContext(String location) throws Exception
	{
		super(location);
	}
	
	/*
	 * 继承ComponentHandle，拥有解析@component注解的能力
	 */
	private class ResolveAnnotation extends ComponentHandle{
		//给定一个包名数组，将自动解析包下所有带有@Component注解的类
		public void ResolvePackage(String... PackageName){
			for(String name:PackageName){
				
			}
		}
	
	}
	
}

package com.lonton.core.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lonton.anntotion.handle.ComponentHandle;
import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.factory.support.BeanDefinitionRegistry;
import com.lonton.beans.factory.support.XmlParser;
import com.lonton.tools.PackageUtil;
/*
 * @author cwt
 * @since  2017-02-03
 * @description  直接继承XmlBeanDefinitionReader类实现，不仅能读取xml配置，
 * 还能自动将注解类注入IOC容器
 */
public class AnnotationBeanDefinitionReader extends XmlBeanDefinitionReader{

	public AnnotationBeanDefinitionReader(BeanDefinitionRegistry registry)
	{
		super(registry);
	}

	@Override
	public int loadBeanDefinitions(Resource resource) throws Exception {
		return doLoadBeanDefinitions(resource);
	}
	
	//扫描所有的包
	public int loadBeanDefinitions() throws Exception {
		return doLoadBeanDefinitions(null);
	}
	@Override
	public int doLoadBeanDefinitions(Resource resource) throws Exception{
		int count=super.doLoadBeanDefinitions(resource);
		List<Class<?>> cls=new ArrayList<Class<?>>();
		if(resource==null){
			//扫描所有的包  TODO
			
		}else{
			//获得包名，将包下的类进行解析
			List<String> PackageNames=XmlParser.getComponentPackageNames();
			for (String PackageName:PackageNames) {
				//获得包下的所有类名
				List<String> ClassNames=PackageUtil.getClassName(PackageName);
				for(String ClassName:ClassNames){
					cls.add(Class.forName(ClassName));
				}
			}
			Map<String, Object> beans=ComponentHandle.getBeanMap(cls);
			for(Entry<String, Object> bean:beans.entrySet()){
		        	BeanDefinition BeforeRegisterBean=getbeanBeanDefinitionFromObject(bean.getKey(), bean.getValue());
		        	//講這個bean進行註冊,這是一個藉口方法，當某個容器需要註冊功能的時候，在繼承這個類
		        	//實現註冊的方法
		        	registry.registerBeanDefinition(bean.getKey(), BeforeRegisterBean);
		    }  
			count=count+beans.size();
		}
		return count;
	}
}

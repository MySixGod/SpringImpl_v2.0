package com.lonton.core.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom.Document;
import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.config.DefaultBeanDefinition;
import com.lonton.beans.factory.support.BeanDefinitionRegistry;
import com.lonton.beans.factory.support.XmlParser;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{
	
	private Map<String, Object> beans=new HashMap<String, Object>();
	
	//默認使用DefaultBeanDefinition
	//TODO 本來是應該講這個beanDefinition對象傳遞給xmlparser，在這裡先簡單實現
	@SuppressWarnings("unused")
	private BeanDefinition beanDefinition;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry)
	{
		super(registry);
		beanDefinition=new DefaultBeanDefinition();
	}
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry,BeanDefinition beanDefinition)
	{
		super(registry);
		this.beanDefinition=beanDefinition;
	}

	//spring有四個加載bean定義的方法，這裡只實現一個
	@Override
	public int loadBeanDefinitions(Resource resource) throws Exception{
		return doLoadBeanDefinitions(resource);
	}
	
    public int doLoadBeanDefinitions(Resource resource) throws Exception{
    	//在加載beandefinition之前，現getxml資源的Document對象
    	Document doc = doLoadDocument(resource);
    	//在這裡講doc進行解析   	
        beans=XmlParser.parser(doc); 
        //再次可以選擇注入一個什麼類型的bean
        for(Entry<String, Object> bean:beans.entrySet()){
        	BeanDefinition BeforeRegisterBean=getbeanBeanDefinitionFromObject(bean.getKey(), bean.getValue());
        	//講這個bean進行註冊,這是一個藉口方法，當某個容器需要註冊功能的時候，在繼承這個類
        	//實現註冊的方法
        	registry.registerBeanDefinition(bean.getKey(), BeforeRegisterBean);
        }  
    	return beans.size();
	}
    
    protected Document doLoadDocument(Resource resource) throws Exception{
    	return new XmlDocumentResource(resource.getFile()).getDocument();
    }
    
    //講Object轉換成Spring當中的beandefinition
    protected BeanDefinition getbeanBeanDefinitionFromObject(String BeanName,Object object){
    	//本來應該在生成beandefinition的時候，也就是XmlParser方法解析時生成對應的Beandefinition
    	//(只需要我將我想生成的beandefinition類型告訴xmlparser)
    	//TODO這裡我是有默認的beandefiition實現
        return new DefaultBeanDefinition(BeanName,object);
    }   
}

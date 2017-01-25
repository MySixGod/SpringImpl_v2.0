package com.lonton.anntotion.handle;

import java.util.HashMap;
import java.util.Map;

public class ComponentHandle{
	
	/*
	 *将带有@Componnet注解的类注入到IOC容器
	 *
	 */
	private  Map<String, Object> beanMap=new HashMap<String, Object>();
	
	public  Map<String, Object>  getBeanMap(Class<?>... ComponentClass)	throws Exception {
		
		for(Class<?> cl:ComponentClass){
			Object object=cl.newInstance();
			String BenaName=
					object.getClass().getName().split(".")[object.getClass().getName().split(".").length-1];
			if(object!=null && BenaName!=null){
				beanMap.put(BenaName, object);
			}
		}
		return beanMap;
	}	
}

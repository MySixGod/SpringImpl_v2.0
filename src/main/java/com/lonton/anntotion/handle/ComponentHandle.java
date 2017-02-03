package com.lonton.anntotion.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lonton.ioc.annotation.Component;
/*
 * @author chenwentao
 * @since  2017-01-25
 */
public class ComponentHandle{
	/*
	 *将带有@Componnet注解的类注入到IOC容器
	 */
	protected static Map<String, Object> beanMap=new HashMap<String, Object>();
	
	public static Map<String, Object>  getBeanMap(List<Class<?>> ComponentClass)	throws Exception {
		
		for(Class<?> cl:ComponentClass){
			Component c=cl.getAnnotation(Component.class);
			if(c!=null){
				Object object=cl.newInstance();
				String BenaName=
						object.getClass().getName().split("\\.")[object.getClass().getName().split("\\.").length-1];
				if(object!=null && BenaName!=null){
					beanMap.put(BenaName, object);
				}
			}
		}
		return beanMap;
	}	
}

package com.lonton.core.aop;

public abstract class AbstractProxy implements Proxy{
	
    @SuppressWarnings("unchecked")
	<T> T getProxyObjectBytype(Object obj,Aop aop){
    	return (T)getProxyObject(obj, aop);
    }
	
	<T> T getProxyObjectByClassName(String className,Aop aop) throws Exception{
		return getProxyObjectBytype(Class.forName(className).newInstance(),aop);
	}
	
	<T> T  getProxyObjectByType(Class<T> clz,Aop aop) throws Exception{
		return getProxyObjectBytype(clz.newInstance(),aop);
	}

}

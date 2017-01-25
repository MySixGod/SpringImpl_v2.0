package com.lonton.core.aop;

public abstract class AbstractProxy implements Proxy{
	
    @SuppressWarnings("unchecked")
	<T> T getProxyObjectBytype(Object obj,Aop aop){
    	return (T)getProxyObject(obj, aop);
    }
	
	<T> T getProxyObjectByClassName(String className,Aop aop){
		return null;
	}
	
	<T> T  getProxyObjectByType(Class<?> clz,Aop aop){
		return null;
	}

}

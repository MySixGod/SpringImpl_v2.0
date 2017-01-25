package com.lonton.core.aop;

public interface Proxy {
	//获得代理类的对象
	Object getProxyObject(Object obj,Aop AOP);
}

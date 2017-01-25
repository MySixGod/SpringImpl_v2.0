package com.lonton.core.aop;

import java.lang.reflect.Method;

public interface Aop{
	
	void after(Object proxy, Method method, Object[] args);
		
	void before(Object proxy, Method method, Object[] args);

}

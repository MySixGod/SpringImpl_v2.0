package com.lonton.core.aop;

import java.lang.reflect.Method;

public class DefaultAOPHandler extends AbstractAOPHandler{
	
	private Aop aop;

	public DefaultAOPHandler(Object object, Aop aop)
	{
		super(object);
		this.aop=aop;
		
	}

	/* (non-Javadoc)
	 * @see com.lonton.core.aop.AbstractAOPHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object ret=null;
		//修改的地方在这里哦
		this.aop.before(proxy, method, args);
		ret=method.invoke(object, args);
		//修改的地方在这里哦
		this.aop.after(proxy, method, args);
		return ret;
	}
	
}

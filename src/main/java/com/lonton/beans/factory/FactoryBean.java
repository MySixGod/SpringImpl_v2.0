package com.lonton.beans.factory;
/*
 * @author  chen  wentao
 */
public interface FactoryBean<T> {
	/*
	 * @return whether the exposed object is a singleton
	 */
	boolean  isSingleton();
	
}

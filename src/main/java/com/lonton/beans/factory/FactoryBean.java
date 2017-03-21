package com.lonton.beans.factory;

/*
 * @author  cwt
 */
public interface FactoryBean<T> {
	/*
	 * @return whether the exposed object is a singleton
	 */
	boolean  isSingleton();
	
	/**
     * Return an instance (possibly shared or independent) of the object
     * managed by this factory.
     */
    T getObject() throws Exception;

    /**
     * Return the type of object that this FactoryBean creates,
     * or {@code null} if not known in advance.
     * */
    Class<?> getObjectType();	
}

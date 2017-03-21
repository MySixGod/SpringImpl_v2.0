package com.lonton.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {    
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public int getSingletonCount() {
        return singletonObjects.size();
    }
}

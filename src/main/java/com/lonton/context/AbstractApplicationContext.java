package com.lonton.context;

import com.lonton.beans.factory.AutowireCapableBeanFactory;
import com.lonton.core.io.DefaultResourceLoader;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
implements ApplicationContext,AutowireCapableBeanFactory{
	

}

package com.lonton.bean;

import com.lonton.ioc.annotation.Autowired;
import com.lonton.ioc.annotation.Component;

@Component
public class DependComponentBean {
	
	private ComponentBean cb;

	@Override
	public String toString() {
		return "DependComponentBean："+cb.toString();
	}
	
	public ComponentBean getCb() {
		return cb;
	}

	/*
	 * set方法名必须要与value值相对应
	 * value的值为：IOC容器中需要注入的bean名字      set方法名必须为：set+value的值
	 */
	@Autowired(value="ComponentBean")
	public void setComponentBean(ComponentBean cb) {
		this.cb = cb;
	}

}

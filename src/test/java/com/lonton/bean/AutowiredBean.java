package com.lonton.bean;

import com.lonton.ioc.annotation.Autowired;

public class AutowiredBean {
    	
	private BeanA ab;
	
	public String say(){
	  return ab.toString();
	}

	/**
	 * @return the ab
	 */
	public BeanA getAb() {
		return ab;
	}

	/**
	 * @param ab the ab to set
	 */
	/*
	 * set方法名必须要与value值相对应
	 * value="beans" set方法名必须为：setBeana 首字母大写
	 */
	@Autowired(value="beana")
	public void setBeana(BeanA ab) {
		this.ab = ab;
	}
}

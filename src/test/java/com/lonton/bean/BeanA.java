package com.lonton.bean;

public class BeanA implements jiekou{
	
	public String name;
	
	public String toString() {
		// TODO Auto-generated method stub
		return "BeanA测试成功"+"属性名："+name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	@Override
	public String aaa() {
		System.out.println("动态代理");
		return "动态代理";
	}
}

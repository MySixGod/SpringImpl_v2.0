package com.lonton.bean;

import com.lonton.ioc.annotation.Autowired;
import com.lonton.ioc.annotation.Component;

@Component
public class AutowiredBean {
    	
    @Autowired(value="beana")
	private BeanA beana;
	
	public String say(){
	  return beana.toString();
	}

    public BeanA getBeana() {
        return beana;
    }

    public void setBeana(BeanA beana) {
        this.beana = beana;
    }

}

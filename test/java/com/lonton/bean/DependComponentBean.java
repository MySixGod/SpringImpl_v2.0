package com.lonton.bean;

import com.lonton.ioc.annotation.Autowired;
import com.lonton.ioc.annotation.Component;

@Component
public class DependComponentBean {
	
    @Autowired(value="componentbean")
	private ComponentBean componentbean;

	@Override
	public String toString() {
		return "DependComponentBean："+componentbean.toString();
	}

    public ComponentBean getComponentbean() {
        return componentbean;
    }

    public void setComponentbean(ComponentBean componentbean) {
        this.componentbean = componentbean;
    }
	
}

package com.lonton.bean;

import com.lonton.classForTest.Food;
import com.lonton.classForTest.People;

public class BeanA implements jiekou {
    
    private People people;
    
    private Food food;
    
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People p) {
        this.people = p;
    }

    public String name;

    public String toString() {
        // TODO Auto-generated method stub
        return "BeanA测试成功" + "属性名：" + name;
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

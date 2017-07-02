package com.lonton.classForTest;

public class People {

    String name;
    
    Food food;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String food() {
        
       return food.say();

    }

    @Override
    public String toString() {
        return "我是人";
    }

}

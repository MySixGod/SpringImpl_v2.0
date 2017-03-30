package com.lonton.classForTest;

public class Food {

    private String foodName;

    private int number;
    
    People people;
    
    
    public int getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public String say() {
        return "人要吃饭";
    }
}

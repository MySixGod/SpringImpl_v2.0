package com.lonton.classForTest;

public class Food {

    private String foodName;

    People people;
    
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

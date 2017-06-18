package com.nowcoder.model;

/**
 * Created by Damon on 2017/6/18.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name){
        this.name = name;
    }

    public String getDescription(){
        return "this is " + this.getName();
    }
}

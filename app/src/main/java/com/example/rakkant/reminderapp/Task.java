package com.example.rakkant.reminderapp;

/**
 * Created by rakkant on 6/3/2017 AD.
 */

public class Task {

    private  String taskName;
    private int amount;


    public Task(String taskName, int amount){
        this.taskName = taskName;
        this.amount = amount;

    }

    public int getAmount(){
        return amount;
    }

    public String getTaskName() {
        return taskName;
    }
}

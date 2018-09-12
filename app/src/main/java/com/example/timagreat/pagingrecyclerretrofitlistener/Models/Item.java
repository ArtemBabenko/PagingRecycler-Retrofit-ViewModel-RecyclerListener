package com.example.timagreat.pagingrecyclerretrofitlistener.Models;


//Клас который описывает дочерние елементы StackApiResponse

public class Item {
    private Owner owner;
    private boolean is_accepted;
    private int score;
    private long last_activity_date;
    private long creation_date;
    private long answer_id;
    private long question_id;

    public Owner getOwner() {
        return owner;
    }

    public long getQuestion_id() {
        return question_id;
    }
}

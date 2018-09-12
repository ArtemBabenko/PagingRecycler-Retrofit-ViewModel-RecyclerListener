package com.example.timagreat.pagingrecyclerretrofitlistener.Models;

//Клас который описывает дочерние елементы Item

public class Owner {
    private int reputation;
    private long user_id;
    private String user_type;
    private String profile_image;
    private String display_name;
    private String link;

    public String getDisplay_name() {
        return display_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

}

package com.example.timagreat.pagingrecyclerretrofitlistener.Models;

import java.util.List;

//Клас описывающий самую верхнюю обертку json(В него вложены Owner и Item)

public class StackApiResponse {
    private List<Item> items;
    private boolean has_more;
    private int quota_max;
    private int quota_remaining;

    public List<Item> getItems() {
        return items;
    }

    public boolean getHas_more() {
        return has_more;
    }
}

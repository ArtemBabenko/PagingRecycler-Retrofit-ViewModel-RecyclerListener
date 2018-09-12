package com.example.timagreat.pagingrecyclerretrofitlistener.Network;

import com.example.timagreat.pagingrecyclerretrofitlistener.Models.StackApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Апи описывающие нужные нам значения. Круто что в апи уже есть определенные значения с которых можно достать размер данных

//https://api.stackexchange.com/2.2/answers?page=1&pagesize=50&site=stackoverflow - вот и сама ссилка на апи

public interface Api {

    @GET("answers")
    Call<StackApiResponse> getAnswers(@Query("page") int page, @Query("pagesize") int pagesize, @Query("site") String site);
}

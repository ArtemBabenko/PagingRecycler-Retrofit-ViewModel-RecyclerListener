package com.example.timagreat.pagingrecyclerretrofitlistener.DataSource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.timagreat.pagingrecyclerretrofitlistener.Models.Item;
import com.example.timagreat.pagingrecyclerretrofitlistener.Models.StackApiResponse;
import com.example.timagreat.pagingrecyclerretrofitlistener.Network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Итак, мы хотим отобразить данные в списке. Данные могут быть откуда угодно: база данных, сервер, файл со строками и т.д.
// Т.е. любой источник, который может предоставить нам данные для отображения их в списке. Для удобства давайте называть его общим словом Storage.
//Обычно мы получаем данные из Storage и помещаем их в List в адаптер. Далее RecyclerView будет у адаптера просить View, а адаптер будет просить данные у List.
//Получается такая схема:
//RecyclerView >> Adapter >> List - где List сразу содержит все необходимые данные и ничего не надо больше подгружать.
//С Paging Library схема будет немного сложнее:
//RecyclerView >> PagedListAdapter >> PagedList > DataSource > Storage
//Т.е. обычный Adapter мы меняем на PagedListAdapter. А вместо List у нас будет связка PagedList + DataSource, которая умеет по мере необходимости подтягивать данные из Storage
//DataSource - это посредник между PagedList и Storage.

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {


    // размер страницы, которую мы хотим
    public static final int PAGE_SIZE = 50;
    // мы начнем с первой страницы, которая равна 1
    private static final int FIRST_PAGE = 1;
    // нам нужно извлечь из stackoverflow
    private static final String SITE_NAME = "stackoverflow";

    // это будет вызываться один раз для загрузки исходных данных
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getItems(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }


    // это загрузит предыдущую страницу
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {

                        // если текущая страница больше одной - мы уменьшаем номер страницы
                        // else нет предыдущей страницы

                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                             // передача загруженных данных и предыдущий ключ страницы
                            callback.onResult(response.body().getItems(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }


    // это загрузит следующую страницу
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body() != null && response.body().getHas_more()) {

                            // если ответ имеет следующую страницу -  увеличение числа следующей страницы
                            // передача загруженных данных и значения следующей страницы
                            callback.onResult(response.body().getItems(), params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }
}

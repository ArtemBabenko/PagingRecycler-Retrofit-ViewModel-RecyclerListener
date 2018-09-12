package com.example.timagreat.pagingrecyclerretrofitlistener.DataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.timagreat.pagingrecyclerretrofitlistener.Models.Item;

//Мы будем использовать MutableLiveData <> для хранения нашего PageKeyedDataSource, и для этого нам нужен DataSource.Factory.

public class ItemDataSourceFactory extends DataSource.Factory {

    // создание изменяемых данных в реальном времени
    private MutableLiveData<PageKeyedDataSource<Integer, Item>> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource<Integer, Item> create() {
        // получение нашего объекта источника данных
        ItemDataSource itemDataSource = new ItemDataSource();

        // размещение источника данных для получения значений
        itemLiveDataSource.postValue(itemDataSource);

        // возврат источника данных
        return itemDataSource;
    }

    // getter для itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Item>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}

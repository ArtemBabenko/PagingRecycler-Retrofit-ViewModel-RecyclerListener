package com.example.timagreat.pagingrecyclerretrofitlistener.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.timagreat.pagingrecyclerretrofitlistener.DataSource.ItemDataSource;
import com.example.timagreat.pagingrecyclerretrofitlistener.DataSource.ItemDataSourceFactory;
import com.example.timagreat.pagingrecyclerretrofitlistener.Models.Item;

//ViewModel - класс, позволяющий Activity и фрагментам сохранять необходимые им объекты живыми при повороте экрана.
//

public class ItemViewModel extends ViewModel {

    // создание liveata для PagedList и PagedKeyedDataSource
    public LiveData<PagedList<Item>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Item>> liveDataSource;

    public ItemViewModel() {

        // Получение нашего источника данных
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();

        // получение источника данных из источника данных
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        // Получение настроек PagedList
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE).build();

        // Создание списка выгружаемых страниц
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}

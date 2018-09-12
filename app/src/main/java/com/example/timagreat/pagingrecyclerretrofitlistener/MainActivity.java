package com.example.timagreat.pagingrecyclerretrofitlistener;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.timagreat.pagingrecyclerretrofitlistener.Adapter.ItemAdapter;
import com.example.timagreat.pagingrecyclerretrofitlistener.Models.Item;
import com.example.timagreat.pagingrecyclerretrofitlistener.ViewModel.ItemViewModel;

public class MainActivity extends AppCompatActivity {


    // получение recyclerview
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // настройка recyclerview
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // получение нашего ItemViewModel
        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        //Создаем объект интерфейса и раелизуем его. Принимаем объект с данными и реагируем на нажатие выводом тоста
        ItemAdapter.OnItemClickListener onItemClickListener = new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Toast.makeText(getApplicationContext(), item.getOwner().getDisplay_name(), Toast.LENGTH_SHORT).show();
            }
        };

        // создание адаптера
        final ItemAdapter adapter = new ItemAdapter(this, onItemClickListener);

        // наблюдение itemPagedList из модели просмотра
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(@Nullable PagedList<Item> items) {

                // в случае каких-либо изменений, отправка элементов в адаптер
                adapter.submitList(items);
            }
        });

        // установка адаптера
        recyclerView.setAdapter(adapter);
    }
}

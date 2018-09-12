package com.example.timagreat.pagingrecyclerretrofitlistener.Adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.timagreat.pagingrecyclerretrofitlistener.Models.Item;
import com.example.timagreat.pagingrecyclerretrofitlistener.R;

//Вообщем для реализации нам нужно наследоваться не от стандарного RecyclerAdapter, а от PagedListAdapter.
//Для того что бы реализовать подзагрузку данных(оеализация подключенной нами лыбы подзагрузки).
//Все как бы обычно, но требуется еще реализация Diff_Callback(Механизм предназначен для точечной замены конкретного елемента при его изминении а не всего списка)
//Нет никакого хранилища данных (List или т.п.).Нет метода getItemCount. Обусловленно ето тем тем, что адаптер внутри себя использует PagedList
// в качестве источника данных, и он сам будет заниматься хранением данных и определением их количества.
//Подробней читай в сохранненых ссылках.

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private OnItemClickListener onItemClickListener;

    //Передаем в супер калбек
    public ItemAdapter(Context mCtx, OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);

        if (item != null) {
            holder.textView.setText(item.getOwner().getDisplay_name());
            Glide.with(mCtx)
                    .load(item.getOwner().getProfile_image())
                    .into(holder.imageView);
        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    //Вот и сам калбек(так все не описать обязательно если призабыл смотреть статью сохраненную)
    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getQuestion_id() == newItem.getQuestion_id();
                }

                @Override
                public boolean areContentsTheSame(Item oldItem, Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewName);
            imageView = itemView.findViewById(R.id.imageView);

            //Обработчик нажатия на коекретный елемент который передае объект данных выше по иерархии, тоесть активити.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = getItem(getLayoutPosition());
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }

    //Интерфей который своего рода мост между Activity и Adapter(реализация патерна  Наблюдатель) который помагает реагировать на
    //нажатия на более высоком уровне  при етом оперируя данными.
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
}

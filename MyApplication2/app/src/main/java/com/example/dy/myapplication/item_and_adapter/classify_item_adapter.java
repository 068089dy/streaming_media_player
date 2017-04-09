package com.example.dy.myapplication.item_and_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.Activity.IdcardActivity;
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import java.util.List;

/**
 * Created by dy on 17-3-29.
 */

public class classify_item_adapter extends RecyclerView.Adapter<classify_item_adapter.MyViewHolder>{

    //private LayoutInflater inflater;
    private Context context;
    private List<Item> list;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item, parent, false));
        return myViewHolder;
    }

    public classify_item_adapter(Context context,List<Item> list,LoadMoreRecyclerView mRecyclerView){
        this.context = context;
        this.list = list;

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.nickname.setText(list.get(position).getRoomid());
        holder.roomSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,IdcardActivity.class);
                intent.putExtra("roomid",list.get(position).getRoomid());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(list.get(position).getImageSrc())
                .placeholder(R.drawable.douyu_loading)
                .into(holder.roomSrc);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView roomSrc;
        TextView nickname;
        public MyViewHolder(View itemView) {
            super(itemView);
            roomSrc = (ImageView) itemView.findViewById(R.id.image1);
            nickname = (TextView) itemView.findViewById(R.id.title1);
        }
    }
}

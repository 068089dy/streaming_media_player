package com.example.dy.myapplication.item_and_adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dy.myapplication.ui.ui.Activity.IdcardActivity;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

/**
 * Created by dy on 17-3-21.
 */

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.MyViewHolder> {

    //private LayoutInflater inflater;
    private Context context;
    private List<Item> list;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item, parent, false));
        return myViewHolder;
    }


    public Item_Adapter(Context context, List<Item> list, LoadMoreRecyclerView mRecyclerView){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.roomid.setText(list.get(position).getRoomid());
        holder.nickname.setText(list.get(position).getAnchor());
        holder.online.setText(Integer.toString(list.get(position).getOnline()));
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
        TextView roomid;
        TextView nickname;
        TextView online;
        public MyViewHolder(View itemView) {
            super(itemView);
            roomSrc = (ImageView) itemView.findViewById(R.id.image1);
            roomid = (TextView) itemView.findViewById(R.id.title1);
            nickname = (TextView) itemView.findViewById(R.id.anthor);
            online = (TextView) itemView.findViewById(R.id.online);
        }
    }
}

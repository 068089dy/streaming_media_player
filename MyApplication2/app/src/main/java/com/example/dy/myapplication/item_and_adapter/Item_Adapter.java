package com.example.dy.myapplication.item_and_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.dy.myapplication.IdcardActivity;
import com.example.dy.myapplication.R;

import java.util.List;

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



    public Item_Adapter(Context context,List<Item> list){
        this.context = context;
        this.list = list;
        //inflater = LayoutInflater.from(context);

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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(list.get(position).getImageSrc(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.roomSrc.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );

        requestQueue.add(imageRequest);




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

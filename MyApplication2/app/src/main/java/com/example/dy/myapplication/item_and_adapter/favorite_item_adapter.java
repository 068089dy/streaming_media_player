package com.example.dy.myapplication.item_and_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.Activity.IdcardActivity;

import java.util.List;

/**
 * Created by dy on 17-3-23.
 */

public class favorite_item_adapter extends RecyclerView.Adapter<favorite_item_adapter.MyViewHolder>{

    //private LayoutInflater inflater;
    private Context context;
    private List<favorite_item> list;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item, parent, false));
        return myViewHolder;
    }


    /*
    *
    * */
    public favorite_item_adapter(Context context,List<favorite_item> list){
        this.context = context;
        this.list = list;
        //inflater = LayoutInflater.from(context);

    }


    /*
    * 设置控件
    * */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //主播名
        holder.anthor.setText("主播:"+list.get(position).getAnchor());
        //观看人数
        holder.online.setText("人数:"+Integer.toString(list.get(position).getOnline()));
        //房间号
        holder.roomid.setText("房间号:"+list.get(position).getRoomid());
        //是否开播
        if(list.get(position).getis_start()){
            holder.is_show.setImageResource(R.drawable.show1);
        }else{

        }
        //点击事件
        holder.roomSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getis_start()){
                    Intent intent = new Intent(context,IdcardActivity.class);
                    intent.putExtra("roomid",list.get(position).getRoomid());
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"未开播",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //图片设置
        /*
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
        */
        Glide.with(context)
                .load(list.get(position).getImageSrc())
                .placeholder(R.drawable.douyu_loading)
                .into(holder.roomSrc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView roomSrc;
        TextView roomid;
        TextView anthor;
        TextView online;
        ImageView is_show;
        public MyViewHolder(View itemView) {
            super(itemView);
            roomSrc = (ImageView) itemView.findViewById(R.id.image1);
            roomid = (TextView) itemView.findViewById(R.id.title1);
            anthor = (TextView) itemView.findViewById(R.id.anthor);
            online = (TextView) itemView.findViewById(R.id.online);
            is_show = (ImageView) itemView.findViewById(R.id.im_isshow);
        }
    }
}

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

    private int mItemHeight = 0;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item, parent, false));
        return myViewHolder;
    }


    public Item_Adapter(Context context,List<Item> list,RecyclerView mRecyclerView){
        this.context = context;
        this.list = list;
        //inflater = LayoutInflater.from(context);
        /*
        mPhotoWall = mRecyclerView;
        taskCollection = new HashSet<BitmapWorkerTask>();
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        try {
            // 获取图片缓存路径
            File cacheDir = getDiskCacheDir(context, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            // 创建DiskLruCache实例，初始化缓存数据
            mDiskLruCache = DiskLruCache
                    .open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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
        //图片显示方法1（无缓存）
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

        //图片显示方法2（应该有缓存的，但是没有）
        /*
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mImageCache.put(key, value);
            }
            @Override
            public Bitmap getBitmap(String key) {
                return mImageCache.get(key);
            }
        };
        ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCache);
        // imageView是一个ImageView实例
        // ImageLoader.getImageListener的第二个参数是默认的图片resource id
        // 第三个参数是请求失败时候的资源id，可以指定为0
        ImageLoader.ImageListener listener = ImageLoader
                .getImageListener(holder.roomSrc, R.drawable.douyu_loading,
                        R.drawable.douyu_loading);
        mImageLoader.get(list.get(position).getImageSrc(), listener);
        */

        //图片缓存方法3（有缓存）
        /*
        holder.roomSrc.setTag(list.get(position).getImageSrc());
        holder.roomSrc.setImageResource(R.drawable.douyu_loading);
        loadBitmaps(holder.roomSrc,list.get(position).getImageSrc());

*/

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

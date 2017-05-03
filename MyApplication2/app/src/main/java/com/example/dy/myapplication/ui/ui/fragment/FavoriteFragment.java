package com.example.dy.myapplication.ui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.common.data;
import com.example.dy.myapplication.item_and_adapter.favorite_item;
import com.example.dy.myapplication.item_and_adapter.favorite_item_adapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dy on 17-3-23.
 */

public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private favorite_item_adapter adapter;
    private List<favorite_item> itemList = new ArrayList<favorite_item>();
    private SwipeRefreshLayout mSwiperefresh;
    View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_favorite,container,false);

        initview();
        initdata();

        return view;
    }
    //重写此方法，防止碎片覆盖
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
    }

    private void initview(){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_recycleview);
        mSwiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.sr_layout);
        mSwiperefresh.setOnRefreshListener(this);
    }

    public void onRefresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                itemList.clear();
                initdata();
                mSwiperefresh.setRefreshing(false);
            }
        },0);
    }

    private void initdata(){

        adapter = new favorite_item_adapter(view.getContext(),itemList);
        mRecyclerView.setAdapter(adapter);
        List<String> list = new ArrayList<String>();
        list = data.favoritelist;
        /*添加
        newsList.add(new News("78561","雪哥"));
        newsList.add(new News("156277","nvliu"));
        newsList.add(new News("138286","55kai"));
        newsList.add(new News("867273","电视剧"));
        newsList.add(new News("24422","pigff"));
        newsList.add(new News("666199","清羽"));
        newsList.add(new News("1275878","暴走漫画"));
        */
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        Iterator<String> it = list.iterator();
        while(it.hasNext()) {
            final String roomid1 = it.next();

            if(roomid1.startsWith("1001")){
                String douyuroomid = roomid1.substring(4);
                //获取斗鱼房间信息
                StringRequest stringRequest = new StringRequest("https://m.douyu.com/html5/live?roomId=" + douyuroomid,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String data1 = jsonObject.getString("data");
                                    JSONObject jsonObject1 = new JSONObject(data1);
                                    String vertical_src = jsonObject1.getString("vertical_src");
                                    int online = jsonObject1.getInt("online");
                                    int show_status = jsonObject1.getInt("show_status");
                                    Boolean is_show = false;
                                    if (1==show_status){
                                        is_show = true;
                                    }else{
                                        is_show = false;
                                    }
                                    String nickname = jsonObject1.getString("nickname");

                                    itemList.add(new favorite_item(vertical_src,roomid1,nickname,is_show,online));
                                    adapter.notifyDataSetChanged();
                                } catch (Exception e) {

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                requestQueue.add(stringRequest);
            }else if(roomid1.startsWith("1002")){

            }

        }

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

    }
}

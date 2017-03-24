package com.example.dy.myapplication.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.dy.myapplication.item_and_adapter.Item;
import com.example.dy.myapplication.item_and_adapter.Item_Adapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 17-3-23.
 */

public class FavoriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Item_Adapter adapter;
    private List<Item> list = new ArrayList<Item>();
    View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_favorite,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_recycleview);
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

    private void initdata(){

        adapter = new Item_Adapter(view.getContext(),list);
        mRecyclerView.setAdapter(adapter);
        /*添加新闻
        newsList.add(new News("78561","雪哥"));
        newsList.add(new News("156277","nvliu"));
        newsList.add(new News("138286","55kai"));
        newsList.add(new News("867273","电视剧"));
        newsList.add(new News("24422","pigff"));
        newsList.add(new News("666199","清羽"));
        newsList.add(new News("1275878","暴走漫画"));
        */
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest stringRequest = new StringRequest("https://m.douyu.com/html5/live?roomId=" + 67373,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String data1 = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data1);
                            String data2 = jsonObject1.getString("vertical_src");
                            list.add(new Item(data2,"67373"));
                        }catch(Exception e){

                        }
                        list.add(new Item("","67373"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(stringRequest);
        list.add(new Item("","67373"));
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);

        //第一列单独占一行
        /*
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        */

        mRecyclerView.setLayoutManager(gridLayoutManager);

    }
}

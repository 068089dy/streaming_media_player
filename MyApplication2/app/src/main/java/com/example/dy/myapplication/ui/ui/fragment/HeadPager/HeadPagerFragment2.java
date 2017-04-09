package com.example.dy.myapplication.ui.ui.fragment.HeadPager;

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
import com.example.dy.myapplication.item_and_adapter.Item;
import com.example.dy.myapplication.item_and_adapter.Item_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 17-3-31.
 */

public class HeadPagerFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private Item_Adapter adapter;
    private List<Item> list = new ArrayList<Item>();
    private SwipeRefreshLayout mSwipelayout;
    private int add_data_num = 1;
    View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_head_pager,container,false);

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.GridView1);
        adapter = new Item_Adapter(view.getContext(),list,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        mSwipelayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);
        //mSwipelayout.setRefreshing(true);
        mSwipelayout.setOnRefreshListener(this);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);

        //第一列单独占一行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void onRefresh()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                initdata();
                // 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
                mSwipelayout.setRefreshing(false);
            }
        }, 0);
    }


    private void initdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest request = new StringRequest("http://capi.douyucdn.cn/api/v1/live?&limit=80",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String data1 = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(data1);
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                System.out.println(jsonObject1.getString("room_id"));
                                list.add(new Item(jsonObject1.getString("vertical_src"),jsonObject1.getString("room_id"),jsonObject1.getString("nickname"),jsonObject1.getInt("online")));
                                adapter.notifyDataSetChanged();
                                //adapter = new Item_Adapter(view.getContext(),list,mRecyclerView);
                                //mRecyclerView.setAdapter(adapter);
                            }

                        }catch(Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("房间号："+volleyError);
            }
        });
        requestQueue.add(request);
    }








}

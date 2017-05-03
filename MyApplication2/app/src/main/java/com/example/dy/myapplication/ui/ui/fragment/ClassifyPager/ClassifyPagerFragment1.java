package com.example.dy.myapplication.ui.ui.fragment.ClassifyPager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 17-4-15.
 */

public class ClassifyPagerFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private LoadMoreRecyclerView mRecyclerView;
    private Item_Adapter adapter;
    private List<Item> list = new ArrayList<Item>();
    private SwipeRefreshLayout mSwipelayout;
    private int add_data_num = 2;
    GridLayoutManager gridLayoutManager;
    View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_classify,container,false);

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
        mRecyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.RecyclerView1);
        adapter = new Item_Adapter(view.getContext(),list,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        //上拉加载。。。
        mRecyclerView.setOnLoadingListener(new LoadMoreRecyclerView.onLoadingMoreListener() {
            @Override
            public void onLoading() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //list.clear();
                        add_data();
                        //mRecyclerView.scrollBy(0,(add_data_num-1)*20);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.loadFinished();
                        adapter.notifyItemRemoved(adapter.getItemCount());
                    }
                }, 0);
            }
        });
        mSwipelayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly1);
        //mSwipelayout.setRefreshing(true);
        mSwipelayout.setOnRefreshListener(this);

        gridLayoutManager = new GridLayoutManager(view.getContext(), 2);

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
                add_data_num = 2;
                initdata();
                // 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
                mSwipelayout.setRefreshing(false);
            }
        }, 0);
    }


    private void initdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest request = new StringRequest("http://www.panda.tv/ajax_sort?token=&pageno=1&pagenum=120&classification=overwatch&_=1492344568373",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String data1 = jsonObject.getString("data");
                            jsonObject = new JSONObject(data1);
                            String items = jsonObject.getString("items");
                            //String pictures = jsonObject.getString("pictures");

                            JSONArray jsonArray = new JSONArray(items);
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                int person_num = Integer.parseInt(jsonObject1.getString("person_num"));
                                String pictures = jsonObject1.getString("pictures");
                                JSONObject picture = new JSONObject(pictures);
                                String img = picture.getString("img");
                                list.add(new Item(img, "1002"+id, name, person_num));
                                adapter.notifyDataSetChanged();

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

    private void add_data(){

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest request = new StringRequest("http://www.panda.tv/ajax_sort?token=&pageno="+add_data_num+"&pagenum=120&classification=overwatch&_=1492344568373",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String data1 = jsonObject.getString("data");
                            jsonObject = new JSONObject(data1);
                            String items = jsonObject.getString("items");
                            //String pictures = jsonObject.getString("pictures");

                            JSONArray jsonArray = new JSONArray(items);
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                int person_num = Integer.parseInt(jsonObject1.getString("person_num"));
                                String pictures = jsonObject1.getString("pictures");
                                JSONObject picture = new JSONObject(pictures);
                                String img = picture.getString("img");

                                System.out.println(jsonObject1.getString("id"));
                                if(i<list.size()){

                                }else {
                                    list.add(new Item(img, "1002"+id, name, person_num));
                                }
                                //adapter = new Item_Adapter(view.getContext(),list,mRecyclerView);
                                //mRecyclerView.setAdapter(adapter);

                            }
                            //adapter.notifyItemRangeChanged(0, (add_data_num-1)*20);
                            adapter.notifyDataSetChanged();
                            mRecyclerView.smoothScrollBy(0,1000);


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
        add_data_num++;
    }

}
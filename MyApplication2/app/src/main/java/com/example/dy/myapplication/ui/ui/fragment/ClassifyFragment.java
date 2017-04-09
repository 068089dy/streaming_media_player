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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.item_and_adapter.Item;
import com.example.dy.myapplication.item_and_adapter.Item_Adapter;
import com.example.dy.myapplication.item_and_adapter.classify_item_adapter;
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClassifyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private LoadMoreRecyclerView mRecyclerView;
    private classify_item_adapter adapter;
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
        adapter = new classify_item_adapter(view.getContext(),list,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
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
        StringRequest request = new StringRequest("http://capi.douyucdn.cn/api/v1/live/148?&limit=20",
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
                                //mRecyclerView.scrollBy(0,(i-1)*20);
                                //mRecyclerView.scrollTo(0,(i-1)*20);
                                //adapter.notifyItemRangeChanged(i*20, list.size());
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

    private void add_data(){

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        StringRequest request = new StringRequest("http://capi.douyucdn.cn/api/v1/live/148?&limit="+add_data_num*20,
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
                                if(i<list.size()){

                                }else {
                                    list.add(new Item(jsonObject1.getString("vertical_src"), jsonObject1.getString("room_id"), jsonObject1.getString("nickname"), jsonObject1.getInt("online")));
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

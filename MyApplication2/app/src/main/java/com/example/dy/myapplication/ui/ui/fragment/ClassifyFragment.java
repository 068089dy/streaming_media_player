package com.example.dy.myapplication.ui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
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
import com.example.dy.myapplication.ui.ui.fragment.ClassifyPager.ClassifyPagerFragment;
import com.example.dy.myapplication.ui.ui.fragment.ClassifyPager.ClassifyPagerFragment1;
import com.example.dy.myapplication.ui.ui.fragment.ClassifyPager.ClassifyPagerFragment2;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment1;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment2;
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClassifyFragment extends Fragment{
    private String[] mTitle = {"推荐","守望先锋","h1z1"};
    private ContentPagerAdapter contentPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mviewPager;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_head,container,false);

        initview();

        return view;
    }

    private void initview(){
        mTabLayout = (TabLayout) view.findViewById(R.id.tl);
        mviewPager = (ViewPager) view.findViewById(R.id.viewpager);

        if(mTitle.length>5){
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else{
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        contentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        ViewCompat.setElevation(mviewPager,10);
        mTabLayout.setupWithViewPager(mviewPager);

        mviewPager.setAdapter(contentPagerAdapter);
    }
    //重写此方法，防止碎片覆盖
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ClassifyPagerFragment();

                case 1:
                    return new ClassifyPagerFragment1();

                case 2:
                    return new ClassifyPagerFragment2();

                default:
                    return null;

            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

    }


}

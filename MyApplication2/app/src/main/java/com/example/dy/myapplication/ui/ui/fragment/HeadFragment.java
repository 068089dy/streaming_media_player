package com.example.dy.myapplication.ui.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment1;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment2;

/**
 * Created by dy on 17-3-31.
 */

public class HeadFragment extends Fragment {
    /*
    * 1.lol
    * 2.how
    * 3.dota2
    * 148.ow
    * 33.cf
    * 6.csgo
    * 175.music
    *
    * */
    private String[] mTitle = {"推荐","守望先锋","音乐"};
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
                    return new HeadPagerFragment();

                case 1:
                    return new HeadPagerFragment1();

                case 2:
                    return new HeadPagerFragment2();

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

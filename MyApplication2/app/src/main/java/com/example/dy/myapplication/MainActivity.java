package com.example.dy.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dy.myapplication.ui.fragment.ClassifyFragment;
import com.example.dy.myapplication.ui.fragment.FavoriteFragment;
import com.example.dy.myapplication.ui.fragment.HeadPagerFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FrameLayout m_frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);

                    //通过fragments这个adapter还有index来替换帧布局中的内容
                    Fragment fragment = (Fragment) fragments.instantiateItem(m_frameLayout, 0);
                    //一开始将帧布局中 的内容设置为第一个
                    fragments.setPrimaryItem(m_frameLayout, 0, fragment);
                    fragments.finishUpdate(m_frameLayout);

                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    //通过fragments这个adapter还有index来替换帧布局中的内容
                    Fragment fragment1 = (Fragment) fragments.instantiateItem(m_frameLayout, 1);
                    //一开始将帧布局中 的内容设置为第一个
                    fragments.setPrimaryItem(m_frameLayout, 0, fragment1);
                    fragments.finishUpdate(m_frameLayout);
                    return true;
                case R.id.navigation_favorite:

                    //mTextMessage.setText(R.string.title_notifications);
                    //通过fragments这个adapter还有index来替换帧布局中的内容
                    Fragment fragment2 = (Fragment) fragments.instantiateItem(m_frameLayout, 2);
                    //一开始将帧布局中 的内容设置为第一个
                    fragments.setPrimaryItem(m_frameLayout, 0, fragment2);
                    fragments.finishUpdate(m_frameLayout);
                    return true;

                case R.id.navigation_notifications:

                    //mTextMessage.setText(R.string.title_notifications);
                    //通过fragments这个adapter还有index来替换帧布局中的内容
                    Fragment fragment3 = (Fragment) fragments.instantiateItem(m_frameLayout, 0);
                    //一开始将帧布局中 的内容设置为第一个
                    fragments.setPrimaryItem(m_frameLayout, 0, fragment3);
                    fragments.finishUpdate(m_frameLayout);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_frameLayout= (FrameLayout) findViewById(R.id.content);
        //mTextMessage = (TextView) findViewById(R.id.message);

        //通过fragments这个adapter还有index来替换帧布局中的内容
        Fragment fragment = (Fragment) fragments.instantiateItem(m_frameLayout, 0);
        //一开始将帧布局中 的内容设置为第一个
        fragments.setPrimaryItem(m_frameLayout, 0, fragment);
        fragments.finishUpdate(m_frameLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //用adapter来管理Fragment界面的变化。这里用的Fragment都是v4包里面的
    FragmentStatePagerAdapter fragments = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 3;
        }

        //进行Fragment的初始化
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new HeadPagerFragment();
                    break;
                case 1:
                    fragment = new ClassifyFragment();
                    break;
                case 2:
                    fragment = new FavoriteFragment();
                    break;
                default:
                    new HeadPagerFragment();
                    break;
            }
            return fragment;
        }
    };

}

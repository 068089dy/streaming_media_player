package com.example.dy.myapplication.ui.ui.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.fragment.ClassifyFragment;
import com.example.dy.myapplication.ui.ui.fragment.FavoriteFragment;
import com.example.dy.myapplication.ui.ui.fragment.HeadFragment;
import com.example.dy.myapplication.ui.ui.fragment.HeadPager.HeadPagerFragment;
import com.example.dy.myapplication.util.share;

import static com.example.dy.myapplication.common.data.FAVORITE_KEY;
import static com.example.dy.myapplication.common.data.favoritelist;

public class MainActivity extends AppCompatActivity {


    private FrameLayout m_frameLayout;

    public static MainActivity mainActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        initview();
        initdata();
    }

    private void initview(){
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

    private void initdata(){
        String data = share.getString(this,FAVORITE_KEY);
        if(data!=null&&!data.equals("")) {
            String roomid[] = data.split("|");
            int i = 0;
            while (roomid[i] != null&&roomid[i].equals("")) {
                favoritelist.add(roomid[i]);
                i++;
            }
        }
    }

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
                    fragment = new HeadFragment();
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


    /*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    */
}

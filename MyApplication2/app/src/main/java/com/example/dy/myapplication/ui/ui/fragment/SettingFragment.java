package com.example.dy.myapplication.ui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dy.myapplication.R;
import com.example.dy.myapplication.item_and_adapter.Item;
import com.example.dy.myapplication.item_and_adapter.classify_item_adapter;
import com.example.dy.myapplication.ui.ui.view.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 17-4-15.
 */

public class SettingFragment extends Fragment {

    View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_setting,container,false);
        //addPreferencesFromResource(R.xml.preferences);
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
}

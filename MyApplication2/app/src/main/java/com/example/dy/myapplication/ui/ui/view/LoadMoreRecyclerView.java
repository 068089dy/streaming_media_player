package com.example.dy.myapplication.ui.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dy.myapplication.R;

/**
 * Description  上拉加载更多RecyclerView
 * Created by chenqiao on 2015/12/31.
 */
public class LoadMoreRecyclerView extends LinearLayout implements NestedScrollingParent, NestedScrollingChild {
    private View rootLayout;
    private RecyclerView recyclerView;
    private FrameLayout footView;
    private NestedScrollingParentHelper helper;
    private NestedScrollingChildHelper childHelper;
    private boolean isBottom = false;
    private boolean changeBottom = false;
    private boolean enableLoad = true;
    private boolean isLoading = false;

    //TODO footView的内容View
    private View footContentView;

    private final int[] mScrollOffset = new int[2];

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new NestedScrollingParentHelper(this);
        childHelper = new NestedScrollingChildHelper(this);
        childHelper.setNestedScrollingEnabled(true);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_morerecycler, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_content);
        footView = (FrameLayout) findViewById(R.id.layout_footView);
        //TODO
        footContentView = LayoutInflater.from(getContext()).inflate(R.layout.footview_up, null);
        footView.addView(footContentView);
        rootLayout = getChildAt(0);
    }

    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        if (isBottom) {
            //已经到了底部，并且还在往上拉，直接返回，不处理事件
            if (rootLayout.getScrollY() + dy >= footView.getMeasuredHeight()) {
                return;
            }
            //回弹
            if (rootLayout.getScrollY() + dy <= 0) {
                dy = -rootLayout.getScrollY();
            }
            changeBottom = false;
            rootLayout.scrollBy(0, dy);
            invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        } else {
            if (!isLoading) {
                //TODO 重置视图
                ((TextView) footContentView.findViewById(R.id.status_tv)).setText("上拉加载");
            }
            //往上拉时，距离大于了footview的高度，只让它拉到那么大
            if (rootLayout.getScrollY() + dy >= footView.getMeasuredHeight()) {
                dy = footView.getMeasuredHeight() - rootLayout.getScrollY();
                changeBottom = true;
                //TODO 拉到footView最大高度时候可以做的事情,视图变化
                if (!isLoading) {
                    ((TextView) footContentView.findViewById(R.id.status_tv)).setText("松开加载更多");
                }
            } else {
                //往下拉时，滑动距离大于了当前的偏移值
                if (rootLayout.getScrollY() + dy < 0) {
                    dy = -rootLayout.getScrollY();
                }
            }
            rootLayout.scrollBy(0, dy);
            invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        }
    }

    /*==========以下为Child的方法==========*/

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    /*==========以下为Parent的方法,用来接收RecyclerView的滑动事件==========*/
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        startNestedScroll(nestedScrollAxes);
        return enableLoad;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        helper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public int getNestedScrollAxes() {
        return helper.getNestedScrollAxes();
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy < 0 && rootLayout.getScrollY() > 0) {
            //如果是手指下滑
            smoothScrollBy(dx, dy);
            consumed[1] = dy;
        } else {
            dispatchNestedPreScroll(dx, dy, consumed, mScrollOffset);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            smoothScrollBy(dxUnconsumed, dyUnconsumed);
        } else {
            //其余的未消费的事件，传给父View消费
            dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mScrollOffset);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        isBottom = changeBottom;
        if (isBottom && !isLoading && rootLayout.getScrollY() == footView.getMeasuredHeight()) {
            isLoading = true;
            //TODO LoadMore视图变化
            ((TextView) footContentView.findViewById(R.id.status_tv)).setText("正在加载");
            if (listener != null) {
                listener.onLoading();
            }
        } else {
            isBottom = false;
            if (rootLayout.getScrollY() > 0 && !isBottom) {
                smoothScrollBy(0, -rootLayout.getScrollY());
            }
        }
        helper.onStopNestedScroll(child);
        //最后一定要调用这个，告诉父view滑动结束，不然父view的滑动会卡住。
        stopNestedScroll();
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return false;
    }

    /*==============以下为开放部分的recyclerView方法 ================*/
    //TODO  如果还需要其他recyclerview的方法，可在下方进行开放。

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration, int index) {
        recyclerView.addItemDecoration(decoration, index);
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    /**
     * 加载结束后调用该方法进行footview缩回
     */
    public void loadFinished() {
        isLoading = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isBottom) {
                    smoothScrollBy(0, -footView.getMeasuredHeight());
                }
            }
        }, 1500);
        //TODO 加载完成后的处理，缩回以及视图变化
        ((TextView) footContentView.findViewById(R.id.status_tv)).setText("加载成功");
    }

    public void setEnableLoad(boolean tf) {
        this.enableLoad = tf;
    }

    private Handler handler = new Handler();
    /*=============== 监听 ===================*/
    public onLoadingMoreListener listener;

    public void setOnLoadingListener(onLoadingMoreListener listener) {
        this.listener = listener;
    }

    public interface onLoadingMoreListener {
        void onLoading();
    }
}
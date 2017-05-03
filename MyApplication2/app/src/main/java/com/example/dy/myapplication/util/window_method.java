package com.example.dy.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dy.myapplication.R;
import com.example.dy.myapplication.ui.ui.Activity.IdcardActivity;
import com.upyun.upplayer.widget.UpVideoView;

/**
 * Created by dy on 17-4-6.
 * 使用方法：
 * 1.先createWindowManager();
 * 2.再createDesktopLayout();
 * 3.显示窗口showDesk();
 * 4.关闭窗口closeDesk();
 */

public class window_method {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayout;
    private DesktopLayout mDesktopLayout;
    private long startTime;
    private Activity activity;
    private UpVideoView upVideoView;
    private String url;
    private String roomid;
    // 声明屏幕的宽高
    float x, y;
    int top = 25;
    public window_method(Activity activity,String url,String roomid){
        this.activity = activity;
        this.url = url;
        this.roomid = roomid;
        System.out.println("window_url"+url);
    }
    /*
    * 用于创建窗口的类
    * */
    class DesktopLayout extends LinearLayout {
        public DesktopLayout(Context context) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);// 水平排列
            //设置宽高
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            View view = LayoutInflater.from(context).inflate(
                    R.layout.desklayout, null);
            this.addView(view);
        }
    }
    /**
     * 创建悬浮窗体
     */
    public void createDesktopLayout() {
        mDesktopLayout = new DesktopLayout(activity);
        upVideoView = (UpVideoView) mDesktopLayout.findViewById(R.id.window_video);
        ImageView window_video_close = (ImageView) mDesktopLayout.findViewById(R.id.window_video_close);
        window_video_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDesk();
            }
        });
        upVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, IdcardActivity.class);
                intent.putExtra("roomid",roomid);
                activity.startActivity(intent);
            }
        });

        mDesktopLayout.setOnTouchListener(new View.OnTouchListener() {
            float mTouchStartX;
            float mTouchStartY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - top; // 25是系统状态栏的高度
                Log.i("startP", "startX" + mTouchStartX + "====startY"
                        + mTouchStartY);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        Log.i("startP", "startX" + mTouchStartX + "====startY"
                                + mTouchStartY);
                        /*
                        long end = System.currentTimeMillis() - startTime;
                        // 双击的间隔在 300ms以下
                        if (end < 300) {
                            closeDesk();
                        }
                        */
                        startTime = System.currentTimeMillis();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        // 更新浮动窗口位置参数
                        mLayout.x = (int) (x - mTouchStartX);
                        mLayout.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout(v, mLayout);
                        break;

                    case MotionEvent.ACTION_UP:
                        /*
                        // 更新浮动窗口位置参数
                        mLayout.x = (int) (x - mTouchStartX);
                        mLayout.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout(v, mLayout);
                        // 可以在此记录最后一次的位置
                        mTouchStartX = mTouchStartY = 0;
                        */
                        long end1 = System.currentTimeMillis() - startTime;
                        // 双击的间隔在 300ms以下
                        if (end1 < 100) {
                            Intent intent = new Intent(activity, IdcardActivity.class);
                            intent.putExtra("roomid",roomid);
                            activity.startActivity(intent);
                            closeDesk();
                        }

                        break;

                }
                return true;
            }
        });
    }

    /**
     * 显示DesktopLayout
     */
    public void showDesk() {

        mWindowManager.addView(mDesktopLayout, mLayout);
        if(url == null){

        }else {
            Log.d("window_url", url);

            //upVideoView.setImage(R.drawable.douyu_loading);

            upVideoView.setVideoPath(url);
            upVideoView.start();
        }
        //finish();
    }
    /**
     * 关闭DesktopLayout
     */
    private void closeDesk() {
        mWindowManager.removeView(mDesktopLayout);
        activity.finish();
    }
    /**
     * 设置WindowManager
     */
    public void createWindowManager() {
        // 取得系统窗体
        mWindowManager = (WindowManager) activity.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        mLayout = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        mLayout.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        mLayout.gravity = Gravity.TOP | Gravity.LEFT;
        // 设置窗体宽度和高度
        mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
}

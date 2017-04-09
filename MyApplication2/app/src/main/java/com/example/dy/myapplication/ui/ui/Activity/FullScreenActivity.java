package com.example.dy.myapplication.ui.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dy.myapplication.R;
import com.upyun.upplayer.widget.UpVideoView;

public class FullScreenActivity extends AppCompatActivity {

    private UpVideoView upVideoView;
    private ImageView im_play;

    private ImageView im_back;
    private ImageView im_resume;
    private ImageView im_full;
    private RelativeLayout onplay_layout;
    String url;

    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(params);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_full_screen);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initview();
        viewevent();
    }

    private void initview(){
        upVideoView = (UpVideoView) findViewById(R.id.up_full_video);

        //upVideoView.fullScreen(this);
        //upVideoView.setMinimumHeight(getScreenSize(this)[1]);
        //upVideoView.setMinimumWidth(getScreenSize(this)[0]);
        upVideoView.setVideoPath(url);
        upVideoView.start();
        im_back = (ImageView) findViewById(R.id.im_back);
        im_play = (ImageView) findViewById(R.id.im_play);
        im_resume = (ImageView) findViewById(R.id.im_resume);
        im_full = (ImageView) findViewById(R.id.im_full);
        onplay_layout = (RelativeLayout) findViewById(R.id.onplay_layout);
    }
    public int[] getScreenSize(Activity activity){
        int screenSize[] = new int[2];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenSize[0] = displayMetrics.widthPixels;
        screenSize[1] = displayMetrics.heightPixels;
        return screenSize;
    }

    private void viewevent(){

        upVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onplay_layout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        onplay_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onplay_layout.setVisibility(View.GONE);
                return true;
            }
        });

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        im_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upVideoView.resume();
            }
        });

        im_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upVideoView.isPlaying()){
                    im_play.setImageResource(R.drawable.pause);
                    upVideoView.pause();
                }else{
                    im_play.setImageResource(R.drawable.play);
                    upVideoView.start();
                }
            }
        });

        im_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if(upVideoView.isFullState()){
                    id_card_view.setVisibility(View.VISIBLE);
                    upVideoView.exitFullScreen(IdcardActivity.this);
                }else{
                    while(!upVideoView.isFullState()){
                        id_card_view.setVisibility(View.GONE);
                        upVideoView.fullScreen(IdcardActivity.this);
                    }
                    /*
                    id_card_view.setVisibility(View.GONE);

                    int screenWidth;//屏幕宽度
                    int screenHeight;//屏幕高度
                    WindowManager windowManager = getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    screenWidth = display.getWidth();
                    screenHeight = display.getHeight();
                    System.out.println("屏幕高度"+display.getHeight());
                    System.out.println("屏幕高度"+display.getWidth());

                    upVideoView.setMinimumHeight(screenHeight);
                    upVideoView.setMinimumWidth(screenWidth);

                    upVideoView.fullScreen(IdcardActivity.this);

                }
                */
                upVideoView.pause();
                finish();

            }
        });


    }
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "竖屏模式", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "横屏模式", Toast.LENGTH_SHORT).show();
        }

    }
}

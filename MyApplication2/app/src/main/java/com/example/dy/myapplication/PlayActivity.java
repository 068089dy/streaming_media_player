package com.example.dy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.upyun.upplayer.widget.UpVideoView;

public class PlayActivity extends AppCompatActivity {
    //UpVideoView使用说明：https://github.com/upyun/android-player-sdk
    private UpVideoView upVideoview;
    private RelativeLayout onplay_layout;

    private ImageView im_play;

    private ImageView im_back;
    private ImageView im_resume;


//7671E5
    private int roomid;
    NetworkRequestImpl mNetworkRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        roomid = Integer.parseInt(intent.getStringExtra("roomid"));
        mNetworkRequest = new NetworkRequestImpl(this);

        initview();
        viewevent();
    }
    private void initview(){
        upVideoview = (UpVideoView) findViewById(R.id.uvw_video);
        onplay_layout = (RelativeLayout) findViewById(R.id.onplay_layout);
        onplay_layout.setVisibility(View.GONE);

        im_back = (ImageView) findViewById(R.id.im_back);

        im_play = (ImageView) findViewById(R.id.im_play);
        im_resume = (ImageView) findViewById(R.id.im_resume);

        //upVideoview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mNetworkRequest.getStreamUrl(roomid,urlListener);
    }

    private void viewevent(){
        upVideoview.setOnTouchListener(new View.OnTouchListener() {
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
                upVideoview.resume();
            }
        });

        im_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upVideoview.isPlaying()){
                    im_play.setImageResource(R.drawable.pause);
                    upVideoview.pause();
                }else{
                    im_play.setImageResource(R.drawable.play);
                    upVideoview.start();
                }
            }
        });
        /*
        im_pasue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

    }

    private UrlListener urlListener = new UrlListener() {
        @Override
        public void onSuccess(String url) {
            System.out.println("直播live:"+url);
            upVideoview.setVideoPath(url);
            upVideoview.fullScreen(PlayActivity.this);
            //upVideoview.resume();

            upVideoview.start();
        }

        @Override
        public void onError() {
            Toast.makeText(PlayActivity.this,"播放出错",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

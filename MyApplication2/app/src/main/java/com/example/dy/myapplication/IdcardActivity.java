package com.example.dy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.upyun.upplayer.widget.UpVideoView;

public class IdcardActivity extends AppCompatActivity {
    private UpVideoView upVideoView;
    private TextView tx_des;
    NetworkRequestImpl mNetworkRequest;
    int roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_idcard);
        Intent intent = getIntent();
        roomid = Integer.parseInt(intent.getStringExtra("roomid"));

        mNetworkRequest = new NetworkRequestImpl(this);
        mNetworkRequest.getStreamUrl(roomid,urlListener);
        initview();
    }

    private void initview(){
        upVideoView = (UpVideoView) findViewById(R.id.idcard_video);
        tx_des = (TextView) findViewById(R.id.tx_des);
        tx_des.setText("陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。");
    }

    private UrlListener urlListener = new UrlListener() {
        @Override
        public void onSuccess(String url) {
            System.out.println("直播live:"+url);
            upVideoView.setVideoPath(url);
            upVideoView.start();
        }

        @Override
        public void onError() {
            Toast.makeText(IdcardActivity.this,"播放出错",Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}

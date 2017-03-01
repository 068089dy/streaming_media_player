package com.example.dy.import_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.upyun.upplayer.widget.UpVideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_path;
    private UpVideoView upVideoview;
    private Button btn_start;
    private Button btn_info;
    private Button btn_fullscreen;
    private TableLayout hub_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

    }
    private void initview(){
        upVideoview = (UpVideoView) findViewById(R.id.uvw_video);
        et_path = (EditText) findViewById(R.id.et_path);
        btn_start = (Button) findViewById(R.id.start);
        btn_start.setOnClickListener(this);
        btn_info = (Button) findViewById(R.id.btn_info);
        btn_info.setOnClickListener(this);
        btn_fullscreen = (Button) findViewById(R.id.btn_fullscreen);
        btn_fullscreen.setOnClickListener(this);
        hub_view = (TableLayout) findViewById(R.id.hud_view);
        hub_view.setVisibility(View.VISIBLE);
        upVideoview.setHudView(hub_view);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.start:
                if(!et_path.getText().toString().equals("")) {
                    String url = et_path.getText().toString();
                    upVideoview.setVideoPath(url);
                    upVideoview.resume();
                    upVideoview.start();
                }else{
                    Toast.makeText(MainActivity.this,"地址为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_info:
                if(hub_view.getVisibility() == View.VISIBLE){
                    hub_view.setVisibility(View.GONE);
                }else{
                    hub_view.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_fullscreen:
                if(upVideoview.isFullState()){
                    upVideoview.exitFullScreen(this);
                    et_path.setVisibility(View.VISIBLE);

                    upVideoview.resume();
                    upVideoview.start();
                }else{
                    upVideoview.fullScreen(this);
                    et_path.setVisibility(View.GONE);
                }
            default: break;
        }
    }
}

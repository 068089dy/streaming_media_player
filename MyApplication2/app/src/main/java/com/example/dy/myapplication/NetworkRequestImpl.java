package com.example.dy.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by uspai.taobao.com on 2016/7/5.
 */
public class NetworkRequestImpl {
    private static final String TAG = "NetworkRequestImpl";
    private Context mContext;

    private RequestQueue mRequestQueue;

    public NetworkRequestImpl(Context context) {
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void getStreamUrl(final int roomId, final UrlListener urlListener) {
        final String url = "http://coapi.douyucdn.cn/lapi/live/thirdPart/getPlay/"+roomId+"?rate=0";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        Gson gson = new Gson();
                        try {
                            GsonDouyuRoom roomInfo = gson.fromJson(response, GsonDouyuRoom.class);
                            String url = roomInfo.getData().getLive_url();

                            urlListener.onSuccess(url);
                        } catch (Exception e) {
                            Log.e("", "onResponse: roomInfo is null", e);
                            urlListener.onError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDouyuRoomParams(roomId);
            }
        };

        mRequestQueue.add(request);
        try {
            System.out.println("请求头："+request.getHeaders());
            System.out.println("请求BodyContentType："+request.getBodyContentType());
            System.out.println("请求CacheKey："+request.getCacheKey());
            System.out.println("请求Url："+request.getUrl());
            System.out.println("请求Method："+request.getMethod());
            System.out.println("请求Tag："+request.getTag());
        }catch(Exception e){

        }
    }

    public HashMap<String, String> getDouyuRoomParams(int roomId) {
        int time = (int) (System.currentTimeMillis()/1000);
        String signContent = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time="
                + time + "9TUk5fjjUjg9qIMH3sdnh";
        String sign = strToMd5Low32(signContent);

        HashMap<String, String> map = new HashMap<>();
        map.put("auth", sign);
        Log.i(TAG,"auth:"+sign);
        map.put("time", Integer.toString(time));
        Log.i(TAG,"time:"+Integer.toString(time));
        map.put("aid", "pcclient");

        return map;
    }

    public static String strToMd5Low32(String str) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] bytes = md5.digest();
            for (byte b : bytes) {
                int digital = b&0xff;
                if (digital < 16)
                    builder.append(0);
                builder.append(Integer.toHexString(digital));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString().toLowerCase();
    }

    public void getPandaStream(final int roomId, final UrlListener urlListener){
        String url = "http://api.homer.panda.tv/chatroom/getinfo?roomid="+roomId;
        //获取roominfo
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String data1 = jsonObject.getString("data");
                    JSONObject jsonObject1 = new JSONObject(data1);
                    final String ts = jsonObject1.getString("ts");
                    final String sign = jsonObject1.getString("sign");
                    final String rid = jsonObject1.getString("rid");
                    String api_url = "http://www.panda.tv/api_room_v2?roomid="+roomId+"&_="+ts;
                    System.out.println("pandaurl:"+api_url);
                    //获取room_key
                    RequestQueue mRequestQueue1 = Volley.newRequestQueue(mContext);
                    StringRequest request1 = new StringRequest(Request.Method.GET, api_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String data1 = jsonObject.getString("data");
                                //System.out.println("pandaurl:"+data1);
                                jsonObject = new JSONObject(data1);
                                String videoinfo = jsonObject.getString("videoinfo");
                                //System.out.println("videoinfo:"+videoinfo);
                                jsonObject = new JSONObject(videoinfo);
                                String roomkey = jsonObject.getString("room_key");
                                //System.out.println("room_key:"+roomkey);
                                String plflag = jsonObject.getString("plflag");
                                String plflag1 = plflag.split("_")[1];

                                String live_url = "http://pl"+plflag1+".live.panda.tv/live_panda/"+roomkey+".flv?sign="+sign+"&time=&ts="+ts+"&rid="+rid;
                                System.out.println("livepandaurl:"+live_url);
                                urlListener.onSuccess(live_url);
                            }catch(Exception e){
                                urlListener.onError();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            urlListener.onError();
                        }
                    });
                    mRequestQueue1.add(request1);

                }catch (Exception e){
                    urlListener.onError();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                urlListener.onError();
            }
        });
        mRequestQueue.add(request);
    }
}

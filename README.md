# 实现转播斗鱼直播平台的直播视频。
## 1.android-player-sdk-master
播放器原工程文件：[这里](https://github.com/upyun/android-player-sdk)
## 2.import_demo
播放器sdk导入的demo
需要注意的地方
1.权限
2.gradle
3.jniLibs文件夹导入
## 3.Material-BottomNavigation-master
[这里](https://github.com/sephiroth74/Material-BottomNavigation)的底部导航菜单demo
## 4.MyApplication2
一个未成形的demo
## 抓取斗鱼直播源的方法,斗鱼不定时会变,来自[这里](https://github.com/littleMeng/video-live)
==== 2017.3.16测试有效 ====
```
public void getStreamUrl(final int roomId) {
  //get请求地址
  String url = "http://coapi.douyucdn.cn/lapi/live/thirdPart/getPlay/"+roomId+"?rate=0";

  //volley
  StringRequest request = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
      Log.i(TAG, response);
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

  //将request加入请求队列
  mRequestQueue.add(request);

  //查看信息
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

/*
* 请求头
*/
public HashMap<String, String> getDouyuRoomParams(int roomId) {
  int time = (int) (System.currentTimeMillis()/1000);
  String signContent = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
  String sign = strToMd5Low32(signContent);

  HashMap<String, String> map = new HashMap<>();
  map.put("auth", sign);
  Log.i(TAG,"auth:"+sign);
  map.put("time", Integer.toString(time));
  Log.i(TAG,"time:"+Integer.toString(time));
  map.put("aid", "pcclient");

  return map;
}

/*
* 加密str
*/
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

```
## 抓取熊猫直播源的方法

```
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

                    //获取room_key
                    RequestQueue mRequestQueue1 = Volley.newRequestQueue(mContext);
                    StringRequest request1 = new StringRequest(Request.Method.GET, api_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String data1 = jsonObject.getString("data");
                                jsonObject = new JSONObject(data1);
                                String videoinfo = jsonObject.getString("videoinfo");
                                jsonObject = new JSONObject(videoinfo);
                                String stream_addr = jsonObject.getString("stream_addr");
                                jsonObject = new JSONObject(stream_addr);
                                String roomkey = jsonObject.getString("room_key");
                                String live_url = "http://p17.live.panda.tv/live_panda/"+roomkey+".flv?sign="+sign+"&time=&ts="+ts+"&rid="+rid;
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
```

## 反编译笔记
```
blog.csdn.net/guolin_blog/article/details/49738023
```
ubuntu下安转jd-gui是依赖：
```
sudo apt-get install libgtk2.0-0:i386 libxxf86vm1:i386 libsm6:i386 lib32stdc++6
```

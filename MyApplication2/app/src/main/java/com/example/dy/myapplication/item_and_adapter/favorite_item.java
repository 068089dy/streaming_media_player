package com.example.dy.myapplication.item_and_adapter;

/**
 * Created by dy on 17-3-23.
 */

public class favorite_item {
    private String imagesrc;
    private String roomid;

    public favorite_item(String imagesrc,String roomid){
        this.imagesrc = imagesrc;
        this.roomid = roomid;
    }

    public String getRoomid(){
        return roomid;
    }

    public String getImageSrc(){
        return imagesrc;
    }
}

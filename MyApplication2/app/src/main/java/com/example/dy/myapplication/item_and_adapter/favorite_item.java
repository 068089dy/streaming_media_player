package com.example.dy.myapplication.item_and_adapter;

/**
 * Created by dy on 17-3-23.
 */

public class favorite_item {
    private String imagesrc;
    private String roomid;
    private String anchor;
    private Boolean is_start;
    private int online;

    public favorite_item(String imagesrc,String roomid,String anchor,boolean is_start,int online){
        this.imagesrc = imagesrc;
        this.roomid = roomid;
        this.anchor = anchor;
        this.is_start = is_start;
        this.online = online;

    }

    public String getRoomid(){
        return roomid;
    }

    public String getImageSrc(){
        return imagesrc;
    }

    public String getAnchor(){
        return anchor;
    }

    public boolean getis_start(){
        return is_start;
    }

    public int getOnline(){
        return online;
    }
}

package com.example.dy.myapplication.item_and_adapter;

/**
 * Created by dy on 17-3-21.
 */

public class Item {

    private String imagesrc;
    private String roomid;
    private String anchor;

    private int online;

    public Item(String imagesrc,String roomid,String anchor,int online){
        this.imagesrc = imagesrc;
        this.roomid = roomid;
        this.anchor = anchor;

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

    public int getOnline(){
        return online;
    }
}

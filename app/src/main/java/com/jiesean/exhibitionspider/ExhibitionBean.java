package com.jiesean.exhibitionspider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiesean on 2017/4/18.
 */

public class ExhibitionBean {

    private String title;
    private String tag = "无";
    private String time;
    private String location;
    private String cost;
    private List<String> keyWord;

    public ExhibitionBean(){
        keyWord = new ArrayList<>();
        keyWord.add("摄影");
        keyWord.add("绘画");
        keyWord.add("书法");
        keyWord.add("照片");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String URL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    private String imgURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = this.tag + tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean isWanted(){
        if (containKey(title) || containKey(tag)) {
            return true;
        }
        return false;
    }

    private boolean containKey(String info){
        for (int i = 0; i < keyWord.size(); i++) {
            if (info.trim().indexOf(keyWord.get(i)) != -1) {
                return true;
            }
        }
        return false;
    }
}

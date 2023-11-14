package com.example.otomarket.model;

public class SlideImg {
    String url;
    String res;

    public SlideImg(String url, String res) {
        this.url = url;
        this.res = res;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}

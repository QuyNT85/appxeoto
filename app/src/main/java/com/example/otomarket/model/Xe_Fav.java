package com.example.otomarket.model;

import java.io.Serializable;

public class Xe_Fav implements Serializable {

    String idXeFav, tenXeFav, anhXeFav;
    int soLuong, giaXeFav;

    public Xe_Fav() {
    }

    public Xe_Fav(String idXeFav, String tenXeFav, String anhXeFav, int soLuong, int giaXeFav) {
        this.idXeFav = idXeFav;
        this.tenXeFav = tenXeFav;
        this.anhXeFav = anhXeFav;
        this.soLuong = soLuong;
        this.giaXeFav = giaXeFav;
    }

    public String getIdXeFav() {
        return idXeFav;
    }

    public void setIdXeFav(String idXeFav) {
        this.idXeFav = idXeFav;
    }

    public String getTenXeFav() {
        return tenXeFav;
    }

    public void setTenXeFav(String tenXeFav) {
        this.tenXeFav = tenXeFav;
    }

    public String getAnhXeFav() {
        return anhXeFav;
    }

    public void setAnhXeFav(String anhXeFav) {
        this.anhXeFav = anhXeFav;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaXeFav() {
        return giaXeFav;
    }

    public void setGiaXeFav(int giaXeFav) {
        this.giaXeFav = giaXeFav;
    }
}
package com.example.otomarket.model;

import java.io.Serializable;

public class Xe implements Serializable {



    String maXe, tenXe, anhXe, maHANG, maPK;
    int giaXe,soluongxe;

    public Xe(String maXe, String tenXe, String anhXe, String maHANG, String maPK, int giaXe, int soluongxe) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.anhXe = anhXe;
        this.maHANG = maHANG;
        this.maPK = maPK;
        this.giaXe = giaXe;
        this.soluongxe = soluongxe;
    }

    public String getMaXe() {
        return maXe;
    }

    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public String getAnhXe() {
        return anhXe;
    }

    public void setAnhXe(String anhXe) {
        this.anhXe = anhXe;
    }

    public String getMaHANG() {
        return maHANG;
    }

    public void setMaHANG(String maHANG) {
        this.maHANG = maHANG;
    }

    public String getMaPK() {
        return maPK;
    }

    public void setMaPK(String maPK) {
        this.maPK = maPK;
    }

    public int getGiaXe() {
        return giaXe;
    }

    public void setGiaXe(int giaXe) {
        this.giaXe = giaXe;
    }

    public int getSoluongxe() {
        return soluongxe;
    }

    public void setSoluongxe(int soluongxe) {
        this.soluongxe = soluongxe;
    }
}
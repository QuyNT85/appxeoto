package com.example.otomarket.model;

public class GioHang {
    String maxe, tenxe, hinhxe;
    int giaxe, soluongxe;


    public GioHang() {
    }

    public GioHang(String maxe, String tenxe, String hinhxe, int giaxe, int soluongxe) {
        this.maxe = maxe;
        this.tenxe = tenxe;
        this.hinhxe = hinhxe;
        this.giaxe = giaxe;
        this.soluongxe = soluongxe;
    }

    public String getMaxe() {
        return maxe;
    }

    public void setMaxe(String maxe) {
        this.maxe = maxe;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }

    public String getHinhxe() {
        return hinhxe;
    }

    public void setHinhxe(String hinhxe) {
        this.hinhxe = hinhxe;
    }

    public int getGiaxe() {
        return giaxe;
    }

    public void setGiaxe(int giaxe) {
        this.giaxe = giaxe;
    }

    public int getSoluongxe() {
        return soluongxe;
    }

    public void setSoluongxe(int soluongxe) {
        this.soluongxe = soluongxe;
    }

    public int tinhTongTien() {
        int tongtien = 0;
        tongtien = soluongxe * giaxe;
        return tongtien;
    }
}

package com.example.otomarket.model;

public class HangXe {
    public String MaHangXe,TenHangXe, HinhHangXe;

    public HangXe(String maHangXe, String tenHangXe, String hinhHangXe) {
        MaHangXe = maHangXe;
        TenHangXe = tenHangXe;
        HinhHangXe = hinhHangXe;
    }

    public String getMaHangXe() {
        return MaHangXe;
    }

    public void setMaHangXe(String maHangXe) {
        MaHangXe = maHangXe;
    }

    public String getTenHangXe() {
        return TenHangXe;
    }

    public void setTenHangXe(String tenHangXe) {
        TenHangXe = tenHangXe;
    }

    public String getHinhHangXe() {
        return HinhHangXe;
    }

    public void setHinhHangXe(String hinhHangXe) {
        HinhHangXe = hinhHangXe;
    }
}

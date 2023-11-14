package com.example.otomarket.model;

public class Item {
    String maXe, tenXe,anhXe,soLuong;

    public Item(String maXe, String tenXe, String anhXe, String soLuong) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.anhXe = anhXe;
        this.soLuong = soLuong;
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}

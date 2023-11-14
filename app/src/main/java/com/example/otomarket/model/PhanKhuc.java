package com.example.otomarket.model;

public class PhanKhuc {
    public String MaPK, TenPK, HinhPK;

    public PhanKhuc(String maPK, String tenPK, String hinhPK) {
        MaPK = maPK;
        TenPK = tenPK;
        HinhPK = hinhPK;
    }

    public String getMaPK() {
        return MaPK;
    }

    public void setMaPK(String maPK) {
        MaPK = maPK;
    }

    public String getTenPK() {
        return TenPK;
    }

    public void setTenPK(String tenPK) {
        TenPK = tenPK;
    }

    public String getHinhPK() {
        return HinhPK;
    }

    public void setHinhPK(String hinhPK) {
        HinhPK = hinhPK;
    }
}

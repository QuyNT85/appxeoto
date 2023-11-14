package com.example.otomarket.server;

public class SERVER {
    public static String serverIP = "http://192.168.1.31";
    public static String hangpath = serverIP + "/webService_OTO/getHang.php";
    public static String imgHangpath = serverIP + "/webService_OTO/qlbanoto/hang/";

    public static String pkpath = serverIP + "/webService_OTO/getPK.php";
    public static String imgPKpath = serverIP + "/webService_OTO/qlbanoto/pk/";

    public static String xePath = serverIP + "/webService_OTO/getXe.php";
    public static String imgXepath = serverIP + "/webService_OTO/qlbanoto/xe2/";

    public static String bestCarPath = serverIP + "/webService_OTO/getXePK.php?mapk=\"lxr\"";


    public static String xeByBrand = serverIP + "/webService_OTO/getXeHang.php?mahang='";
    public static String xeByPK = serverIP + "/webService_OTO/getXePK.php?mapk='";

    public static String searchPath = serverIP + "/webService_OTO/search.php?key=";


    public static String khachhangpath = serverIP + "/webService_OTO/khachhang.php";
    public static String addpath = serverIP + "/webService_OTO/adduser.php";
    public static String loginpath = serverIP + "/webService_OTO/Login.php";

    public static String fbggpath = serverIP + "/webService_OTO/facebookandgoogle.php";

    public static String historyPath = serverIP + "/webService_OTO/getOrdetHistory.php?makh=";
    public static String filterPath = serverIP + "/webService_OTO/getXe.php?";

    public static String hoadonpath = serverIP + "/webService_OTO/getHoaDon.php";


}

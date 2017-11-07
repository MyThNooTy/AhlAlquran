package com.samcotec.ahlalquran;

/**
 * Created by PC07 on 10/11/2017.
 */

public class MyData {
    private int gID;
    private String gName;
    private String sDate;
    private String eDate;
    private String gAdminName;
    private String gImage_link;

    public String getsDate() {
        return sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public String getgAdminName() {
        return gAdminName;
    }

    //public MyData(int ID, String name, String image_link) {
    public MyData(int ID, String name, String sdate, String edate,String gadminname) {
        this.gID = ID;
        this.gName = name;
        this.sDate = sdate;
        this.eDate = edate;
        this.gAdminName = gadminname;
        //this.gImage_link = image_link;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public void setgAdminName(String gAdminName) {
        this.gAdminName = gAdminName;
    }

    public int getID(){
        return gID;
    }
    public void setID(int id){
        this.gID = id;
    }
    public String getName(){
        return gName;
    }
    public String getImageLink(){
        return gImage_link;
    }
    public void setImageLink(String imagelink){
        this.gImage_link = imagelink;
    }
}

package com.example.weijunn.project01.sqlitedata;

public class DataProvider {
    private byte[] img;
    private String location;
    private String name;
    private String number;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
    public DataProvider(byte[] img,String location,String name,String number){
        this.img = img;
        this.location = location;
        this.name =name;
        this.number =number;
    }

}



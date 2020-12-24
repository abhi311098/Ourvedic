package com.abhi.ourvedic.model;

import java.io.Serializable;

public class DataFModel implements Serializable {

    int itemid;
    String itemname;
    String itemprice;
    String localname;

    public DataFModel() {
    }

    public DataFModel(int itemid, String itemname, String itemprice, String localname) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.localname = localname;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }
}

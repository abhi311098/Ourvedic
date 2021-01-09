package com.abhi.ourvedic;

public class item {
    private int item_id;
    private String item_local_name;
    private String item_name;
    int item_image;
    private int item_Price;

    public item(){}
    public item(int item_id, String item_local_name, String item_name, int item_image, int item_Price){
        this.item_id = item_id;
        this.item_local_name = item_local_name;
        this.item_name = item_name;
        this.item_image = item_image;
        this.item_Price = item_Price;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_local_name() {
        return item_local_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public int getItem_image() {
        return item_image;
    }

    public int getItem_Price() {
        return item_Price;
    }
}

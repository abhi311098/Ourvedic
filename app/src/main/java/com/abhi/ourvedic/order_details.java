package com.abhi.ourvedic;

public class order_details {
    private final String name;
    private final String email;
    private final String itemIds;
    private final String delivery_address;
    private final String mobile;
    private final int final_amount;
    private final String mode_of_payment;
    private final String order_date_time;     // in dd/mm/yyyy  followed with hh/mm of 24hrs format  like 090120211810
    private String delivered_date_time; //Same as above & can be "null" if undelivered or yet not delivered

    public order_details(String name, String email, String orderIds, String delivery_address, String mobile, int final_amount, String mode_of_payment, String order_date_time, String delivered_date_time) {
        this.name = name;
        this.email = email;
        this.itemIds = orderIds;
        this.delivery_address = delivery_address;
        this.mobile = mobile;
        this.final_amount = final_amount;
        this.mode_of_payment = mode_of_payment;
        this.order_date_time = order_date_time;
        this.delivered_date_time = delivered_date_time;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {return email; }

    public String getItemIds() {
        return itemIds;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public String getMobile() {
        return mobile;
    }

    public int getFinal_amount() {
        return final_amount;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public String getDelivered_date_time() {
        return delivered_date_time;
    }

    public void setDelivered_date_time(String delivered_date_time) {
        this.delivered_date_time = delivered_date_time;
    }
}

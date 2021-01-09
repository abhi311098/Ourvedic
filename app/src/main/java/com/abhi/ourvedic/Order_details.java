package com.abhi.ourvedic;

public class Order_details {
    private String name;
    private int orderId;
    private String delivery_address;
    private String mobile;
    private int final_amount;
    private String mode_of_payment;
    private String order_date_time;     // in dd/mm/yyyy  followed with hh/mm of 24hrs format  like 090120211810
    private String delivered_date_time; //Same as above & can be "null" if undelivered or yet not delivered

    public Order_details(String name, int orderId, String delivery_address, String mobile, int final_amount, String mode_of_payment, String order_date_time, String delivered_date_time) {
        this.name = name;
        this.orderId = orderId;
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

    public int getOrderId() {
        return orderId;
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

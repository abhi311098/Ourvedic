package com.abhi.ourvedic.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProfileModel {

    @PrimaryKey (autoGenerate = true)
    int id ;
    String email;
    String name;
    String house;
    String street;
    String area;
    String pincode;
    String landmark;
    String number;

    public ProfileModel(String email, String name, String house, String street,
                        String area, String pincode, String landmark, String number) {
        this.email = email;
        this.name = name;
        this.house = house;
        this.street = street;
        this.area = area;
        this.pincode = pincode;
        this.landmark = landmark;
        this.number = number;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

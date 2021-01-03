package com.abhi.ourvedic;

public class user_details {

    private String username;
    private String email;
    private String password;
    private String full_address;
    private String mob;

    public user_details(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public user_details(String username, String email, String password, String full_address, String mob) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.full_address = full_address;
        this.mob = mob;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFull_address() {
        return full_address;
    }

    public String getMob() {
        return mob;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }
}

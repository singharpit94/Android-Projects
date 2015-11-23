package com.ofinu.ofinu_cust;

/**
 * Created by Siraj on 8/31/2015.
 */
public class User {

    String name, email, contact, password;
    String longitude,latitude;

    public User(String name, String email, String contact, String password){
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }
    public User(String latitude,String longitude,String contact){

        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = contact;


    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.contact = "-1";
        this.name = "";
    }
}

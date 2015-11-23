package com.ofinu.ofinudriver; /**
 * Created by Siraj on 9/4/2015.
 */

/**
 * Created by Siraj on 8/31/2015.
 */
public class User {

    String name, contact, autono, pin;
    String latitude, longitude;

    public User(String name, String contact, String autono, String pin){
        this.name = name;
        this.contact = contact;
        this.autono = autono;
        this.pin = pin;
    }
    public User(String latitude, String longitude, String pin){
        this.latitude = latitude;
        this.longitude = longitude;
        this.pin = pin;

    }
    public User(String pin) {
        this.pin = pin;
        this.autono = "-1";
        this.contact = "-1";
        this.name = "";
    }
}
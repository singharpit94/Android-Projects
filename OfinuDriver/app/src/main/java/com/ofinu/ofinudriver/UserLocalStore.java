package com.ofinu.ofinudriver;

/**
 * Created by Siraj on 9/4/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Siraj on 8/31/2015.
 */
public class UserLocalStore {
    public static final String SP_NAME = "DriverDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("contact", user.contact);
        spEditor.putString("autono", user.autono);
        spEditor.putString("pin", user.pin);
        spEditor.commit();

    }
    public User getLoggedInUser() {
        String name = userLocalDatabase.getString("name", "");
        String contact = userLocalDatabase.getString("contact", "");
        String autono = userLocalDatabase.getString("autono", "");
        String password = userLocalDatabase.getString("pin", "");

        User storedUser = new User(name, contact, autono, password);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false)== true){
            return true;
        }else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

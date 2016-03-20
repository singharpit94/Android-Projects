package com.example.arpit.ashish;

/**
 * Created by arpit on 2/4/16.
 */
public class User {
    private int id;
    private int userid;
    private String title;
    private String body;

    public User(){

    }
    public User(int id,int userid,String title,String body){

        this.id=id;
        this.userid=userid;
        this.title=title;
        this.body=body;
    }


    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String gettitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

   public String getBody(){
       return body;
   }
    public void setBody(String body){
        this.body=body;
    }


}


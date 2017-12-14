package com.example.alysa.mobapde_scrapbook.models;

/**
 * Created by Alysa on 12/3/2017.
 */

public class User {

    private String first_n; //first name
    private String last_n; //last name
    private String user_n; //username
    private String pass_w; //password
    private int status; // status
    private int id; //id

    public String getFirstName(){ return first_n; }
    public String getLastName(){ return last_n; }
    public String getUserName(){ return user_n;}
    public String getPassword(){ return pass_w;}
    public int getStatus(){ return status; }
    public int getID(){ return id; }

    public void setFirstName(String i){ this.first_n = i; }
    public void setLastName(String i){ this.last_n = i; }
    public void setUserName(String i){ this.user_n = i;}
    public void setPassword(String i){ this.pass_w = i;}
    public void setStatus(int i){ this.status = i; }
    public void setID(int i){ this.id = i; }


}

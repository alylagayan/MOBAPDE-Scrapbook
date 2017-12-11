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

    public User(String fn, String ln, String un, String pw, int s){
        this.first_n = fn;
        this.last_n = ln;
        this.user_n = un;
        this.pass_w = pw;
        this.status = s;
    }

    public String getFirstName(){ return first_n; }
    public String getLastName(){ return last_n; }
    public String getUserName(){ return user_n;}
    public String getPassword(){ return pass_w;}
    public int getStatus(){ return status; }

}

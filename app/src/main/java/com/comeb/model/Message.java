package com.comeb.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by côme on 24/09/2015.
 */
public class Message {
    protected int ResImg;
    protected int id;
    protected String pseudo;
    protected String message;
    protected Date time;

    public Message(int i, String p,String m){
        id = i;
        pseudo=p;
        message=m;
        time=new Date();
    }

    public Message(String p,String m){
        pseudo=p;
        message=m;
        time=new Date();
    }

    public boolean isLeft(){
        return  false;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getResImg() {
        return ResImg;
    }

    public void setResImg(int resImg) {
        ResImg = resImg;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        return message;
    }
    public static Message fabrique(String json_object){
        JSONObject j;
        try {
            j = new JSONObject(json_object);
            if(j.getString("login").equals(MyCredentials.getLogin())){
                return new MessageRight(j.getString("login"),j.getString("message"));
            }else{
                return new MessageLeft(j.getString("login"),j.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Message fabrique2(int id, String login, String message){

        if(login.equals(MyCredentials.getLogin())){
            return new MessageRight(id,login,message);
        }else{
            return new MessageLeft(id,login,message);
        }
    }
}

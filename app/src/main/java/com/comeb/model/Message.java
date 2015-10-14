package com.comeb.model;

import com.comeb.tchat.TchatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by côme on 24/09/2015.
 */
public abstract class Message {
    protected int ResImg;
    protected String pseudo;
    protected String message;
    protected Date time;

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
            if(j.getString("login").equals(TchatActivity.getLogin())){
                return new MessageRight(j.getString("login"),j.getString("message"));
            }else{
                return new MessageLeft(j.getString("login"),j.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

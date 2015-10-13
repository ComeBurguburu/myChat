package com.comeb.model;

import java.util.Date;

/**
 * Created by côme on 24/09/2015.
 */
public abstract class Elem {
    protected int ResImg;
    protected String pseudo;
    protected String message;
    protected Date time;

    public Elem(String p,String m){
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
}

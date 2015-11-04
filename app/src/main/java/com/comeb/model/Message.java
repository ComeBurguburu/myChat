package com.comeb.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by côme on 24/09/2015.
 */
public abstract class Message {
    protected int resImg;
    protected int id;
    protected String pseudo;
    protected String message;
    protected String uuid;
    protected String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message(int id, String login, String message, String uuid) {
        this.id = id;
        this.pseudo = login.trim();
        this.message = message.trim();
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message1 = (Message) o;

        if (getPseudo() != null ? !getPseudo().equals(message1.getPseudo()) : message1.getPseudo() != null)
            return false;
        return !(getMessage() != null ? !getMessage().equals(message1.getMessage()) : message1.getMessage() != null);

    }

    @Override
    public int hashCode() {
        int result = getPseudo() != null ? getPseudo().hashCode() : 0;
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        return result;
    }

    public Message(String login, String message, String uuid) {
        this.id = 0;
        this.pseudo = login;
        this.message = message;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Message(String p, String m) {
        pseudo = p;
        message = m;
    }

    public boolean isLeft() {
        return false;
    }

    public int getResImg() {
        return resImg;
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

    public String toString() {
        return message;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Message(JSONObject json) throws JSONException {
        setPseudo((String) json.getString("login"));
        setMessage((String) json.getString("message"));
        setUuid((String) json.getString("uuid"));

        if (json.has("images")) {
            try {
                setImg((String) json.getJSONArray("images").get(0));
            } catch (Exception e) {
                setImg(null);
            }
        }
    }

    public static Message fabrique(int id, String login, String message, String uuid) {
        if (login.equals(MyCredentials.getLogin())) {
            return new MessageRight(id, login, message, uuid);
        } else {
            return new MessageLeft(id, login, message, uuid);
        }
    }

    public static Message fabrique(String json_object) {
        JSONObject j;
        try {
            j = new JSONObject(json_object);
            if (j.getString("login").equals(MyCredentials.getLogin())) {
                return new MessageRight(j);
            } else {
                return new MessageLeft(j);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new MessageLeft("(void)", "(void)");
        }
    }

    public boolean hasImg() {
        return img != null;
    }

}
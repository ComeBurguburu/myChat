package com.comeb.model;

import com.comeb.tchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by côme on 24/09/2015.
 */
public class MessageRight extends Message {
    public MessageRight(int id, String login, String message, String uuid) {
        super(id, login, message, uuid);
        resImg = R.mipmap.ic_user_red;
    }

    public MessageRight(String p, String m) {
        super(p, m);
        resImg = R.mipmap.ic_user_red;
    }

    public MessageRight(String login, String message, String uuid) {
        super(login, message);
        resImg = R.mipmap.ic_user_red;
        this.uuid = uuid;
    }

    public MessageRight(String login, String message, String uuid, JSONArray images) {
        super(login, message, uuid);
        resImg = R.mipmap.ic_user_red;

        try {
            img = (String) images.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public MessageRight(JSONObject j) throws JSONException {
        super(j);
        resImg = R.mipmap.ic_user_red;
    }

    @Override
    public boolean isLeft() {
        return false;
    }
}

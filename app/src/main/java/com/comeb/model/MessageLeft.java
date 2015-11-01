package com.comeb.model;

import com.comeb.tchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by côme on 24/09/2015.
 */
public class MessageLeft extends Message {
    public MessageLeft(String p, String m) {
        super(p, m);
        resImg = R.mipmap.ic_user_blue;
    }

    public MessageLeft(int id, String login, String message, String uuid) {
        super(id, login, message, uuid);
        resImg = R.mipmap.ic_user_blue;
    }

    public MessageLeft(String login, String message, String uuid) {
        super(login, message);
        resImg = R.mipmap.ic_user_blue;
        this.uuid = uuid;
    }

    public MessageLeft(String login, String message, String uuid, JSONArray images) {
        super(login, message, uuid);
        resImg = R.mipmap.ic_user_blue;
        try {
            setImg((String) images.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MessageLeft(JSONObject j) throws JSONException {
        super(j);
        resImg = R.mipmap.ic_user_blue;
    }

    @Override
    public boolean isLeft() {
        return true;
    }
}

package com.comeb.tchat;

import com.comeb.model.Message;

import java.util.ArrayList;

/**
 * Created by côme on 14/10/2015.
 */
public interface SyncListener {
    void onSuccess(ArrayList<Message> messages);

    void onFailure();
}

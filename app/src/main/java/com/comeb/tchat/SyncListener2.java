package com.comeb.tchat;

import com.comeb.model.Message;

import java.util.ArrayList;

/**
 * Created by benjaminjornet on 26/10/15.
 */
public interface SyncListener2 {
        public void onSuccess (String username, String password);
        public void onFailure (String errorMessage);
}

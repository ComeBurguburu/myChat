package com.comeb.tchat;

import android.content.Context;

/**
 * Created by benjaminjornet on 26/10/15.
 */
public interface SyncListener2 {
        public void onFailure (String errorMessage);
        public void error(String message,boolean userWrong,boolean passwordWrong);
        public void switchToTchat(Context context,String login,String password);
}

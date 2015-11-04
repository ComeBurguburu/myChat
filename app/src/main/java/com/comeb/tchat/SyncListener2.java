package com.comeb.tchat;

/**
 * Created by benjaminjornet on 26/10/15.
 */
public interface SyncListener2 {
    public void onFailure(String errorMessage);

    public void error(String message, boolean userWrong, boolean passwordWrong);

    public void onSuccess(String login, String password);
}

package com.comeb.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by benjaminjornet on 14/10/15.
 */
public class MyCredentials {

    private static Context context;

    public static void setContext(Context c){
        context=c;
    }
    public static String getPassword() {
        SharedPreferences prefs = context.getSharedPreferences("users_credentials", context.MODE_PRIVATE);
        return prefs.getString("password", "");
    }

    public static void setPassword(String password) {
        edit(context,"password",password);
    }
    private static void edit(Context context,String key,String value){
        SharedPreferences prefs = context.getSharedPreferences("users_credentials", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getLogin() {
        SharedPreferences prefs = context.getSharedPreferences("users_credentials", context.MODE_PRIVATE);
        return prefs.getString("login", "");
    }


    public static void setLogin(String login) {
        edit(context,"login",login);
    }
    public static boolean isSomeOneLogged(){
        return !(getLogin().equals("")||getPassword().equals(""));
    }

}

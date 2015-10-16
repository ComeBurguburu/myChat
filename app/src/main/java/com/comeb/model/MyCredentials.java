package com.comeb.model;

/**
 * Created by benjaminjornet on 14/10/15.
 */
public class MyCredentials {
    private static String login;
    private static String password;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MyCredentials.password = password;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        MyCredentials.login = login;
    }

}

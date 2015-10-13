package com.comeb.com.comeb.async;

import android.content.Context;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by côme on 07/10/2015.
 */
public class ServerAPI {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String url_root = "http://training.loicortola.com/chat-rest/2.0/";

    private static ServerAPI singleton = null;

    private ServerAPI() {
    }

    public static ServerAPI getInstance() {
        if (singleton == null) {
            singleton = new ServerAPI();
        }
        return singleton;
    }


    private String fullUrl(String str1, String str2, String str3) {
        return fullUrl(str1, str2, str3, "");
    }

    private String fullUrl(String str1, String str2, String str3, String str4) {
        StringBuffer sb = new StringBuffer();
        sb.append(url_root);
        sb.append(str1);
        if (!str2.equals("")) {
            sb.append("/");
            sb.append(str2);
        }
        if (!str3.equals("")) {
            sb.append("/");
            sb.append(str3);
        }
        if (!str4.equals("")) {
            sb.append("/");
            sb.append(str4);
        }
        return sb.toString();
    }

    /*
        /connect/{user}/{password}
        Vérifie l'existence de la combinaison user/password et retourne la chaine de caractères true si elle existe, false sinon.
    */
    private String getURLCredentials(String user, String password) {
        return fullUrl("connect", user, password);
    }

    /*
            /message/{user}/{password}/{message}
    Poster un message dont l'auteur est user. La combinaison user/password doit être valide pour que le message soit posté. Aucune valeur de retour n'est renvoyé par le serveur.
   */


    /*
    /messages/{user}/{password}
    Recevoir la liste de tous messages qui sont sur le serveur. Les messages sont envoyés dans l'ordre chronologique. La liste que vous recevrez sera une chaînes de caractères respectant le format suivant :
auteur1:message1;auteur2:message2; … auteurN:messageN
     */


    public String getSendMessageURL(String user, String password, String message) {
        return fullUrl("message", user, password, message);
    }

    public String getGetMessageURL(String user, String password) {
        return fullUrl("messages", user, password);
    }

    /* private boolean  testCredentials(String user,String password){
         String s = testCredentialsString(user, password);
         return (s!=null) && s.equals("true");
     }*/
    public String getURLtestCredentials() {
        return getURLCredentials("user", "pass");
    }

    public void testCredentialsString(Context context, String user, String password) {
        new AsyncTestCredentials(context, getURLCredentials(user, password)).execute();
    }

    public void sendMessage(Context context, String user, String password, String message) {
        new AsyncSendMessage(context, getSendMessageURL(user, password, message)).execute();
    }

    public void getAllMessage(Context context, String user, String password) {
        new AsyncGetMessage(context, getGetMessageURL(user, password)).execute();
    }
    public void getAllMessage2(Context context, String user, String password) {
        new AsyncGetMessage(context, getGetMessageURL(user, password)).execute();
    }

    public String post(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
       // JSONObject j=new JSONObject();
       // j.put("login","test");
       // j.put("password",getLogin())
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}


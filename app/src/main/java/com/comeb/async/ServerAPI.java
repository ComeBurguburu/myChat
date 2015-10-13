package com.comeb.async;

import android.content.Context;

import com.comeb.tchat.TchatActivity;
import com.squareup.okhttp.Credentials;
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

    private String fullUrl(String route){
        return fullUrl(route,"","");
    }
    private String fullUrl(String str1, String str2) {
        return fullUrl(str1, str2,"");
    }

    private String fullUrl(String str1, String str2, String str3) {
        StringBuffer sb = new StringBuffer();
        sb.append(url_root);
        sb.append(str1);
        if (str2!= null && !str2.equals("")) {
            sb.append("/");
            sb.append(str2);
        }
        if (str3!=null && !str3.equals("")) {
            sb.append("/");
            sb.append(str3);
        }

        System.out.println(sb.toString());
        return sb.toString();
    }



    /*
        /connect/{user}/{password}
        Vérifie l'existence de la combinaison user/password et retourne la chaine de caractères true si elle existe, false sinon.
    */
    private String getURLCredentials() {
        return fullUrl("connect");
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


    public String getSendMessageURL(String message) {
        return fullUrl("message", message);
    }
    public String getSendMessageFileURL(String message,String uuid,String filename){
        return fullUrl(message,uuid,filename);
    }

    public String getGetMessageURL() {
        return fullUrl("messages");
    }

    /* private boolean  testCredentials(String user,String password){
         String s = testCredentialsString(user, password);
         return (s!=null) && s.equals("true");
     }*/


    public void testCredentialsString(Context context, String user, String password) {
        new AsyncTestCredentials(context, getURLCredentials()).execute();
    }
    public void testMessageString(Context context, String user, String password) {
        new AsyncTestCredentials(context, getGetMessageURL()).execute();
    }

    public void sendMessage(Context context, String message) {
        new AsyncSendMessage(context, getSendMessageURL(message)).execute();
    }

    public void getAllMessage(Context context) {
        new AsyncGetMessage(context, getGetMessageURL()).execute();
    }


    public String post(String url,String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // RequestBody body = RequestBody.create(JSON, json.toString());
        String credential = Credentials.basic(TchatActivity.getLogin(), TchatActivity.getPassword());



        Request request = new Request.Builder()
                .url(url)
                .header("Authorization",credential)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}


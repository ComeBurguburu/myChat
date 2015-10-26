package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.comeb.model.Message;
import com.comeb.model.MessageSimple;
import com.comeb.model.MyCredentials;
import com.comeb.tchat.SyncListener;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by côme on 07/10/2015.
 */
public class ServerAPI {
    private final String url_root = "http://training.loicortola.com/chat-rest/2.0/";
    private final String url_connect = "http://formation-android-esaip.herokuapp.com/connect/";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static ServerAPI singleton = null;
    private static ArrayList<AsyncTask> asyncList;

    private ServerAPI() {
    }

    public static ServerAPI getInstance() {
        if (singleton == null) {
            singleton = new ServerAPI();
            singleton.asyncList = new ArrayList<AsyncTask>();
        }
        return singleton;
    }

    public static void stopAllAsync(){
        if(singleton==null){
            return;
        }
        Iterator<AsyncTask> it = asyncList.iterator();
        while(it.hasNext()){
            it.next().cancel(true);
        }
    }

    private String fullUrl(String route) {
        return fullUrl(route, "", "");
    }

    private String fullUrl(String str1, String str2) {
        return fullUrl(str1, str2, "");
    }

    private String fullUrl(String str1, String str2, String str3) {
        StringBuffer sb = new StringBuffer();
        sb.append(url_root);
        sb.append(str1);
        if (str2 != null && !str2.equals("")) {
            sb.append("/");
            sb.append(str2);
        }
        if (str3 != null && !str3.equals("")) {
            sb.append("/");
            sb.append(str3);
        }

        System.out.println(sb.toString());
        return sb.toString();
    }

    private String fullUrlConnect(String route) {
        return fullUrl(route, "", "");
    }

    private String fullUrlConnect(String str1, String str2) {
        return fullUrl(str1, str2, "");
    }

    private String fullUrlConnect(String str1, String str2, String str3) {
        StringBuffer sb = new StringBuffer();
        sb.append(url_connect);
        sb.append(str1);
        if (str2 != null && !str2.equals("")) {
            sb.append("/");
            sb.append(str2);
        }
        if (str3 != null && !str3.equals("")) {
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
    private boolean checkConnexion (String username, String pwd){

        String url_tmp = "";
        if((!username.equals("")) && (!pwd.equals("")) && (!username.contentEquals("--")) && (!pwd.contentEquals("--"))){
            url_tmp = fullUrlConnect(username, pwd);
        }

        try{
            URL myUrl = new URL(url_tmp);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(15000);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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


    private String getSendMessageURL() {
        return fullUrl("messages");
    }

    private String getSendMessageFileURL(String message, String uuid, String filename) {
        return fullUrl(message, uuid, filename);
    }

    private String getGetMessageURL() {
        return fullUrl("messages");
    }


   public void testCredentials(Context context, String user, String password) {
        asyncList.add(new AsyncTestCredentials(context, getURLCredentials(), user, password).execute());
    }


    public void sendMessage(Context context, String message) {
       sendMessage(context, message, null);
    }

    public void sendMessage(Context context, String message,ArrayList<String> base64) {
        asyncList.add(new AsyncSendMessage(context, getSendMessageURL(), message, base64).execute());
    }

    public void getAllMessage(SyncListener syncListener) {
        asyncList.add(new AsyncGetMessage(syncListener, getGetMessageURL()).execute());
    }

    public void getImage(ImageView image,String URL,Context context){
        //Picasso.with(context).load(Uri.parse(URL)).into(image_show);
        asyncList.add(new AsyncLoadImage(image, URL, context).execute());
        System.out.println("Load: " + URL);
    }


  /*  public String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        String credential = Credentials.basic(MyCredentials.getLogin(), MyCredentials.getPassword());
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Authorization", credential);

        if (json != null && !json.equals("")) {
            requestBuilder.post(body);
        }

        Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }*/
    public Response post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        String credential = Credentials.basic(MyCredentials.getLogin(), MyCredentials.getPassword());
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Authorization", credential);

        if (json != null && !json.equals("")) {
            requestBuilder.post(body);
        }

        Response response = client.newCall(requestBuilder.build()).execute();
        return response;
    }
    public InputStream fetch_stream(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String credential = Credentials.basic(MyCredentials.getLogin(), MyCredentials.getPassword());

        Request request = new Request.Builder().url(url).header("Authorization", credential).build();

        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }

    public String post_connect(String url, String login, String password) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String credential = Credentials.basic(login, password);


        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Authorization", credential);


        Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }

    public Response get(String url) throws IOException {
        return post(url, "");
    }
    public static ArrayList<Message> uniqUser(ArrayList<Message> messages) {
        if(messages==null){
            return null;
        }

        int index;
        ArrayList L=new ArrayList<MessageSimple>();
        for (index = 0; index < messages.size(); index++) {
            MessageSimple pseudo = new MessageSimple(messages.get(index).getPseudo());
            if (!L.contains(pseudo)) {
                L.add(pseudo);
            }

        }
        return L;

    }


    public static ArrayList convertMessage(Response response) {
        String message= null;
        if(response==null){
            return null;
        }

        try {
            message = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Message> L = new ArrayList<Message>();
        if(!response.message().equals("OK")){
           return null;

        }else {

            JSONArray list;
            try {
                list = new JSONArray(message);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            int index;
            for (index = 0; index < list.length(); index++) {
                try {
                    String json_message = list.get(index).toString();
                    L.add(Message.fabrique(json_message));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return L;
    }
}


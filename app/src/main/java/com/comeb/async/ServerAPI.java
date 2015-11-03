package com.comeb.async;

import android.content.Context;
import android.widget.ImageView;

import com.comeb.model.Message;
import com.comeb.model.MessageSimple;
import com.comeb.model.MyCredentials;
import com.comeb.tchat.SyncListener;
import com.comeb.tchat.SyncListener2;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by côme on 07/10/2015.
 */
public class ServerAPI {
    private final String url_root = "http://training.loicortola.com/chat-rest/2.0/";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static ServerAPI singleton = null;
    private static AsyncTestCredentials async_test;
    private static AsyncRegister async_register;
    private static AsyncSendMessage async_send;
    private static AsyncGetMessage async_get_message;
    private static AsyncLoadImage async_load_image;

    private ServerAPI() {
    }

    public static ServerAPI getInstance() {
        if (singleton == null) {
            singleton = new ServerAPI();
        }
        return singleton;
    }

    public static void stopAllAsync() {
        if (singleton == null) {
            return;
        }
        if (async_test != null) {
            async_test.cancel(true);
            async_test = null;
        }
        if (async_register != null) {
            async_register.cancel(true);
            async_register = null;
        }
        if (async_get_message != null) {
            async_get_message.cancel(true);
            async_get_message = null;
        }
        if (async_send != null) {
            async_send.cancel(true);
            async_send = null;
        }
        if (async_load_image != null) {
            async_load_image.cancel(true);
            async_load_image = null;
        }
    }

    private String fullUrl(String route) {
        return fullUrl(route, "", "");
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

        return sb.toString();
    }





    /*
        /connect/{user}/{password}
        Vérifie l'existence de la combinaison user/password et retourne la chaine de caractères true si elle existe, false sinon.
    */

    private String getURLCredentials() {
        return fullUrl("connect");
    }

    private String getURLRegister() {
        return fullUrl("register");
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

    private String getGetMessageURL() {
        return fullUrl("messages");
    }


    public void testCredentials(SyncListener2 context, String user, String password) {
        if (async_test != null && async_test.isFinish()) {
            async_test.cancel(true);
            async_test = null;
        }
        if (async_test == null) {
            async_test = new AsyncTestCredentials(context, getURLCredentials(), user, password);
            async_test.execute();
        }
    }

    public void register(SyncListener2 context, String user, String password) {
        if (async_register != null && async_register.isFinish()) {
            async_register.cancel(true);
            async_register = null;
        }
        if (async_register == null) {
            async_register = new AsyncRegister(context, getURLRegister(), user, password);
            async_register.execute();
        }
    }

    public void sendMessage(Context context, String message, ArrayList<String> base64) {
        if (async_send != null && async_send.isFinish()) {
            async_send.cancel(true);
            async_send = null;
        }
        if (async_send == null) {
            async_send = new AsyncSendMessage(context, getSendMessageURL(), message, base64);
            async_send.execute();
        }
    }

    public void getAllMessage(SyncListener syncListener) {
        if (async_get_message != null && async_get_message.isFinish()) {
            async_get_message.cancel(true);
            async_get_message = null;
        }
        if (async_get_message == null) {
            async_get_message = new AsyncGetMessage(syncListener, getGetMessageURL());
            async_get_message.execute();
        }
    }

    public void getImage(ImageView image, String URL, Context context) {
        //Picasso.with(context).load(Uri.parse(URL)).into(image_show);
        if (async_load_image != null && async_load_image.isFinish()) {
            async_load_image.cancel(true);
            async_load_image = null;
        }
        if (async_load_image == null) {
            async_load_image = new AsyncLoadImage(image, URL);
            async_load_image.execute();
        }
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

    public Response post_register(String url, String login, String password) throws IOException {

        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            json.put("login", login);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json.toString());


        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);


        Response response = client.newCall(requestBuilder.build()).execute();
        return response;
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
        if (messages == null) {
            return null;
        }

        int index;
        ArrayList L = new ArrayList<MessageSimple>();
        for (index = 0; index < messages.size(); index++) {
            MessageSimple pseudo = new MessageSimple(messages.get(index).getPseudo());
            if (!L.contains(pseudo)) {
                L.add(pseudo);
            }

        }
        return L;

    }


    public static ArrayList convertMessage(Response response) {
        String message = null;
        if (response == null) {
            return null;
        }

        try {
            message = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Message> L = new ArrayList<Message>();
        if (!response.message().equals("OK")) {
            return null;

        } else {

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
                    Message m = Message.fabrique(json_message);
                    if (filter(m))
                        L.add(m);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return L;
    }

    private static boolean filter(Message m) {
        boolean DEBUG = false;

        ArrayList<String> available = new ArrayList<String>();
        available.add("test2");
        available.add("benji");
        available.add("côme");

        if (DEBUG) {
            return (m != null && available.contains(m.getPseudo()));
        } else {
            return true;
        }

    }
}


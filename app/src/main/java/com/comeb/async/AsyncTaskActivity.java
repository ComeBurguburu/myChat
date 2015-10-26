package com.comeb.async;
import android.app.Activity;
import android.content.Context;

import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.Map;

/**
 * Created by benjaminjornet on 26/10/15.
 */

public class AsyncTaskActivity extends Activity {
/*
    AsyncHttpClient client = new AsyncHttpClient();

    RequestParams params = new RequestParams();
    params.put("username", "abcd123");
    params.put("password", "123456");

    AsyncTestCredentials client = new AsyncTestCredentials();
    client.post("http://formation-android-esaip.herokuapp.com/connect/", params, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {
            // success
        }

        @Override
        public void onFailure(Throwable error, String content) {
            // something went wrong
        }
    });

    AsyncTestCredentials client = new AsyncTestCredentials();
    client.post("https://url.com/getdata?username=abcd123&password=123456", new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {
            // success
        }

        @Override
        public void onFailure(Throwable error, String content) {
            // something went wrong
        }
    });
    */
    /*
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Context context;
    private String url_server = "http://formation-android-esaip.herokuapp.com/connect/";
    URLStreamHandlerFactory url_server_factory;
    private URL urlServer;

    public URL getUrlServer() {
        return urlServer;
    }

    public void setUrlServer(URL urlServer) {
        this.urlServer = urlServer;
    }
    
    private void setContext(Context c) {
        context = c;
    }

    private boolean isConnexionPossible (String username, String pwd){
        
        //urlServer.getHost("http://formation-android-esaip.herokuapp.com/connect/");
        urlServer.setURLStreamHandlerFactory(url_server_factory);
        urlServer.getHost()
*/
        /*
        javax.xml.ws.BindingProvider bp = (javax.xml.ws.BindingProvider)port;
        Map<String,Object> context = bp.getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url_server);
        context.put(BindingProvider.USERNAME_PROPERTY,username);
        context.put(BindingProvider.PASSWORD_PROPERTY, pwd);
*/
        /*
        request.setURI(new URI(url_server));

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(new URI("http://www.internet.com/api"));
        */
    /*
        /connect/{user}/{password}
        Vérifie l'existence de la combinaison user/password et retourne la chaine de caractères true si elle existe, false sinon.
    */



}
package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.tchat.LoginActivity;
import com.comeb.tchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AsyncTestCredentials extends AsyncTask<Void, Integer, Void> {
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
    private String URL;
    private String response;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private void setContext(Context c) {
        context = c;
    }

    private AsyncTestCredentials() {

    }

    public AsyncTestCredentials(Context context, String URL, String user, String password) {
        super();
        setContext(context);
        setURL(URL);
        setLogin(user);
        setPassword(password);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, ServerAPI.getInstance().getURLtestCredentials(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        try {
            response = ServerAPI.getInstance().post_connect(getURL(), login, password);
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            JSONObject resp = new JSONObject(response);
            if (resp.getInt("status") == 200) {

                LoginActivity.switchToTchat(context, getLogin(), getPassword());
            } else {
                JSONArray arr = resp.getJSONArray("elements");
                LoginActivity.error(String.valueOf(arr.get(0)) + context.getString(R.string.is_incorrect));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LoginActivity.error(context.getString(R.string.connexion_error));


        // Toast.makeText(context, "Le traitement asynchrone est terminé:"+response, Toast.LENGTH_LONG).show();
    }


}
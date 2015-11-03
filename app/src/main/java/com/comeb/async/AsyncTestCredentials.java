package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.tchat.R;
import com.comeb.tchat.SyncListener2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AsyncTestCredentials extends AsyncTask<Void, Integer, Void> {
    private boolean isFinish;
    private SyncListener2 sync;
    private Context context;
    private String URL;
    private String response;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getURL() {
        return URL;
    }

    public AsyncTestCredentials(SyncListener2 sync, String URL, String user, String password) {
        super();
        this.context = (Context) sync;
        this.sync = sync;
        this.URL = URL;
        this.login = user;
        this.password = password;
        this.isFinish = false;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (response == null) {
            sync.error(context.getString(R.string.no_connexion), true, true);
            return;
        }

        try {
            JSONObject resp = new JSONObject(response);
            if (resp.getInt("status") == 200) {
                sync.switchToTchat(context, getLogin(), getPassword());
            } else {
                JSONArray arr = resp.getJSONArray("elements");
                sync.error(context.getString(R.string.is_incorrect, arr.get(0)), arr.get(0).equals("login"), arr.get(0).equals("password"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            sync.error(context.getString(R.string.connexion_error), true, true);
        }

    }

    public boolean isFinish() {
        return isFinish;
    }


}
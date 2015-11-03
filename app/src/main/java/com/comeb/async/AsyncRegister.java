package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.tchat.R;
import com.comeb.tchat.SyncListener2;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AsyncRegister extends AsyncTask<Void, Integer, Void> {

    private SyncListener2 sync;
    private String login;
    private String password;
    private Context context;
    private String URL;
    private Response response;
    private String answer;
    private boolean isFinish;


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

    public String getURL() {
        return URL;
    }

    private AsyncRegister() {

    }

    public AsyncRegister(SyncListener2 sync, String URL, String login, String password) {
        super();
        this.context = (Context) sync;
        this.sync = sync;
        this.URL = URL;
        this.login = login;
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
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        try {
            response = ServerAPI.getInstance().post_register(getURL(), login, password);
            answer = response.body().string();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
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
            JSONObject resp = new JSONObject(answer);
            if (resp.getInt("status") == 200) {
                sync.switchToTchat(context, getLogin(), getPassword());
            } else {
                String message = resp.getString("message");
                sync.onFailure(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            sync.onFailure(context.getString(R.string.no_connexion));
        }
        this.isFinish = true;
    }

    public boolean isFinish() {
        return isFinish;
    }
}
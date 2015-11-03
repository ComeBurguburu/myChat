package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.model.MyCredentials;
import com.comeb.tchat.R;
import com.comeb.tchat.SyncListener;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class AsyncGetMessage extends AsyncTask<Void, Integer, Void> {
    private SyncListener syncListener;
    private Context context;
    private String URL;
    protected Response response;
    private ArrayList messages;
    private boolean isFinish;

    public String getURL() {
        return URL;
    }


    public AsyncGetMessage(SyncListener syncListener, String URL) {
        super();
        this.isFinish = false;
        this.context=(Context)syncListener;
        this.syncListener = syncListener;
        this.URL = URL;
        this.syncListener = syncListener;
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
        JSONObject json = new JSONObject();
        try {

            json.put("login", MyCredentials.getLogin());
            json.put("password", MyCredentials.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            response = ServerAPI.getInstance().get(getURL());
            messages = ServerAPI.convertMessage(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (response == null) {
            syncListener.displayError(true, context.getString(R.string.no_connexion));
        } else {
            syncListener.displayError(false, "");
            syncListener.onSuccess(messages);
        }
        this.isFinish = true;
    }

    public boolean isFinish() {
        return isFinish;
    }

   /* private ArrayList convertMessage_GSon(String response)  {
        Gson gson=new Gson();
        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
        return gson.fromJson(response, listType);
        }*/


}
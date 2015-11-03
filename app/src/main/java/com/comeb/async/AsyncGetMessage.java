package com.comeb.async;

import android.os.AsyncTask;

import com.comeb.model.MyCredentials;
import com.comeb.tchat.SyncListener;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class AsyncGetMessage extends AsyncTask<Void, Integer, Void> {
    protected SyncListener syncListener;
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
        this.URL = URL;
        this.syncListener = syncListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
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
            syncListener.onFailure();
        } else {
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
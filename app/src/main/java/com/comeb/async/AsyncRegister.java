package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AsyncRegister extends AsyncTask<Void, Integer, Void> {


    private String login;
    private String password;
    private Context context;
    private String URL;
    private Response response;
    private String answer;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private void setContext(Context c) {
        context = c;
    }

    private AsyncRegister() {

    }

    public AsyncRegister(Context context, String URL,String login,String password) {
        super();
        this.context=context;
        this.URL=URL;
        this.login=login;
        this.password=password;
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
            response = ServerAPI.getInstance().post_register(getURL(),login,password);
            answer = response.body().string();
        } catch (IOException e) {
            response = null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        try {
            JSONObject resp=new JSONObject(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(response.code()==200){
            //TODO
        }else{
            //TODO
        }

    }


}
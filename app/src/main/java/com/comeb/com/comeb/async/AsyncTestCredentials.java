package com.comeb.com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

class AsyncTestCredentials extends AsyncTask<Void, Integer, Void>
{
    private Context context;
    private String URL;
    private String response;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private void setContext(Context c){
        context = c;
    }

    private AsyncTestCredentials(){

    }

    public AsyncTestCredentials(Context context, String URL) {
        super();
        setContext(context);
        setURL(URL);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, ServerAPI.getInstance().getURLtestCredentials(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        try {
            response = ServerAPI.getInstance().post(getURL());
        } catch (IOException e) {
            response=null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        Toast.makeText(context, "Le traitement asynchrone est terminé:"+response, Toast.LENGTH_LONG).show();
    }



}
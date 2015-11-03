package com.comeb.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.comeb.model.MyCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


class AsyncLoadImage extends AsyncTask<Void, Integer, Void> {
    private String URL;
    protected String response;
    private Bitmap bmp;
    private ImageView image_view;
    private boolean isFinish;

    public String getURL() {
        return URL;
    }

    public AsyncLoadImage(ImageView v, String URL) {
        super();
        this.isFinish = false;
        this.URL = URL;
        this.image_view = v;
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
            InputStream input = ServerAPI.getInstance().fetch_stream(getURL());
            bmp = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            response = "error";
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        image_view.setImageBitmap(bmp);
        this.isFinish = true;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public InputStream toInputSteam(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }


   /* private ArrayList convertMessage_GSon(String response)  {
        Gson gson=new Gson();
        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
        return gson.fromJson(response, listType);
        }*/


}
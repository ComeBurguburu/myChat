package com.comeb.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.comeb.model.MyCredentials;
import com.comeb.tchat.SyncListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


class AsyncLoadImage extends AsyncTask<Void, Integer, Void> {
   protected SyncListener syncListener;
    private String URL;
    protected String response;
    private Context context;
    private Bitmap bmp;
    private ImageView image_view;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private void setContext(Context c) {
        context = c;
    }

    private AsyncLoadImage() {

    }

    public AsyncLoadImage(ImageView v, String URL,Context context) {
        super();
        setContext(context);
        setURL(URL);
        this.image_view=v;
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

    }

   /* private ArrayList convertMessage_GSon(String response)  {
        Gson gson=new Gson();
        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
        return gson.fromJson(response, listType);
        }*/


}
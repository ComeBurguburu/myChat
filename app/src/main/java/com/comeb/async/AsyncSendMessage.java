package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.comeb.model.MyCredentials;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

class AsyncSendMessage extends AsyncTask<Void, Integer, Void> {
    private Context context;
    private String URL;
    private Response response;
    private String message;
    private ArrayList<String> base64_list;
    private String answer;

    private ArrayList getBase64() {
        return base64_list;
    }

    private boolean isBase64() {
        return base64_list != null && !base64_list.isEmpty();
    }

    public void setBase64(ArrayList<String> base64) {
        this.base64_list = base64;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private void setContext(Context c) {
        context = c;
    }

    private AsyncSendMessage() {

    }

    public AsyncSendMessage(Context context, String URL, String message, ArrayList<String> base64) {
        super();
        this.context = context;
        this.URL = URL;
        this.message = message;
        this.base64_list = base64;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
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
        JSONArray attachements = new JSONArray();
        JSONObject json_image = new JSONObject();
        try {
            json.put("login", MyCredentials.getLogin());
            json.put("message", message);
            json.put("uuid", UUID.randomUUID());
            if (isBase64()) {
                for (Object base64 : getBase64()) {

                    json_image.put("mimeType", "image/png").put("data", (String) base64);
                    attachements.put(json_image);
                }

                json.put("attachments", attachements);
            }

            //   "attachments": [{"mimeType": "image/png", "data": "yourbase64imagecontenthere"}]}

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            response = ServerAPI.getInstance().post(getURL(), json.toString());
            answer = response.body().string();
        } catch (IOException e) {
            response = null;
            answer = "server error";
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(context, answer, Toast.LENGTH_LONG).show();
    }


}
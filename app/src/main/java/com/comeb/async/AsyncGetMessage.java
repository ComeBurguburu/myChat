package com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.model.Message;
import com.comeb.model.MessageSimple;
import com.comeb.model.MyCredentials;
import com.comeb.tchat.TchatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


class AsyncGetMessage extends AsyncTask<Void, Integer, Void>
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

    private AsyncGetMessage(){

    }

    public AsyncGetMessage(Context context, String URL) {
        super();
        setContext(context);
        setURL(URL);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
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
        } catch (IOException e) {
            response="error";
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        //  Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
        // TchatActivity.rebuildArray(convertMessage(response));
     //   TchatActivity.getAdapter(0).setList(uniqUser(response));
     //   TchatActivity.getAdapter(0).notifyDataSetChanged();

        /*
        TODO by COME

        TchatActivity.getAdapter(1).setList(convertMessage(response));
        TchatActivity.getAdapter(1).notifyDataSetChanged();
        TchatActivity.getRecyclerView(0).scrollToPosition(TchatActivity.getAdapter(0).getItemCount());
        TchatActivity.getRecyclerView(1).scrollToPosition(TchatActivity.getAdapter(1).getItemCount());
*/
    }
    @Deprecated
    private ArrayList uniqUser(String response){
        ArrayList<MessageSimple>L=new ArrayList<>();
        //"auteur1:message1;auteur2:message2; … auteurN:messageN";
        String []auteur_message=response.split(";");
        int i=0;
        ArrayList<String> pseudo_uniq=new ArrayList<String>();
        for(i=0;i<auteur_message.length;i++){
            String information = auteur_message[i];
            String []parts = information.split(":");
            if(parts.length==2) {
                if(!parts[0].equals(MyCredentials.getLogin()) && !pseudo_uniq.contains(parts[0]) ){
                    L.add(new MessageSimple(parts[0]));
                    pseudo_uniq.add(parts[0]);
                }
            }
        }
        return L;
    }


    private ArrayList convertMessage(String response){
        System.out.println(response);

        ArrayList<Message> L=new ArrayList<Message>();
        StringBuffer sb=new StringBuffer();
        JSONObject jo= null;
        try {
            jo = new JSONObject(response);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json_string=sb.append("{list:").append(response).append("}").toString();
        JSONArray list;
        try {
            jo=new JSONObject(json_string);
            list = (JSONArray) jo.get("list");
        } catch (JSONException e) {
            return null;
        }
        int index;
        for(index=0;index<list.length();index++){
            try {
                String json_message = list.get(index).toString();
                L.add(Message.fabrique(json_message));

            } catch (JSONException e) {
            }
        }
        return L;
    }
   /* private ArrayList convertMessage_GSon(String response)  {
        Gson gson=new Gson();
        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
        return gson.fromJson(response, listType);
        }*/
}
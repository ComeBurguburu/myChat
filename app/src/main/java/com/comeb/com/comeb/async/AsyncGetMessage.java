package com.comeb.com.comeb.async;

import android.content.Context;
import android.os.AsyncTask;

import com.comeb.model.Elem;
import com.comeb.model.ElemLeft;
import com.comeb.model.ElemRight;
import com.comeb.model.ElemSimple;
import com.comeb.tchat.TchatActivity;

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

        try {
            response = ServerAPI.getInstance().post(getURL());
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
        TchatActivity.getAdapter(1).setList(convertMessage(response));
        TchatActivity.getAdapter(0).setList(uniqUser(response));
        TchatActivity.getAdapter(0).notifyDataSetChanged();
        TchatActivity.getAdapter(1).notifyDataSetChanged();
        TchatActivity.getRecyclerView(1).scrollToPosition(TchatActivity.getAdapter(1).getItemCount());
        System.out.println(TchatActivity.getAdapter(1).getItemCount());

    }

    private ArrayList uniqUser(String response){
        ArrayList<ElemSimple>L=new ArrayList<>();
        //"auteur1:message1;auteur2:message2; … auteurN:messageN";
        String []auteur_message=response.split(";");
        int i=0;
        ArrayList<String> pseudo_uniq=new ArrayList<String>();
        for(i=0;i<auteur_message.length;i++){
            String information = auteur_message[i];
            String []parts = information.split(":");
            if(parts.length==2) {
                if(!parts[0].equals(TchatActivity.getLogin()) && !pseudo_uniq.contains(parts[0]) ){
                    L.add(new ElemSimple(parts[0]));
                    pseudo_uniq.add(parts[0]);
                }
            }

        }
        return L;


    }


    private ArrayList convertMessage(String response){
        ArrayList<Elem>L=new ArrayList<>();
        //"auteur1:message1;auteur2:message2; … auteurN:messageN";
        String []auteur_message=response.split(";");
        int i=0;
        for(i=0;i<auteur_message.length;i++){
            String information = auteur_message[i];
            String []parts = information.split(":");
            if(parts.length==2) {
                if(parts[0].equals(TchatActivity.getLogin())) {
                    L.add(new ElemLeft(parts[0],parts[1]));
                }else{
                    L.add(new ElemRight(parts[0], parts[1]));
                }
            }

        }
        return L;


    }



}
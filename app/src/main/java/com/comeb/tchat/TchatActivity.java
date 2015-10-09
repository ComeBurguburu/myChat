package com.comeb.tchat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;



/**
 * Created by côme on 24/09/2015.
 */
public class TchatActivity extends AppCompatActivity {
    private AsyncTask<Void, Integer, Void> async;
    private ArrayList<Elem> list;
    private static MyAdapter arrayAdapter;
    private static String login="test";
    private String password="test";

    public static MyAdapter getAdapter() {
        return arrayAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // async.cancel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        final ListView listview = (ListView) findViewById(R.id.list);
        list = new ArrayList<Elem>();
        list.add(new ElemLeft("Toto", "bonjour"));
        list.add(new ElemLeft("Toto", "bonjour"));
        list.add(new ElemRight("Titi", "aurevoir"));
        list.add(new ElemRight("Titi", "aurevoir"));

    arrayAdapter = new MyAdapter(this,list);

    listview.setAdapter(arrayAdapter);
        final EditText ed=(EditText)findViewById(R.id.edit);
        ImageView v=(ImageView)findViewById(R.id.send);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String message = ed.getText().toString();
                if (!message.equals("")) {
                    list.add(new ElemRight("Titi", ed.getText().toString()));
                    ed.setText("");
                    arrayAdapter.notifyDataSetChanged();
                    ServerAPI.getInstance().sendMessage(TchatActivity.this,"test","test",message);
                }
            }
        });

       // new AsyncTestCredentials(TchatActivity.this,ServerAPI.getInstance().getURLtestCredentials()).execute();
        ServerAPI.getInstance().testCredentialsString(TchatActivity.this, getLogin(), getPassword());
        //Toast.makeText(TchatActivity.this,ServerAPI.getInstance().getURLtestCredentials(), Toast.LENGTH_LONG).show();
        ServerAPI.getInstance().getAllMessage(TchatActivity.this,getLogin(),getPassword());
    }

    public void snack(String message){
        View parentLayout = findViewById(R.id.home);

        final Snackbar snack = Snackbar.make(parentLayout,message, Snackbar.LENGTH_SHORT);
        snack.show();
        snack.setAction(getString(R.string.hide), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack.dismiss();
            }
        });
    }
    public void initialise_timer(){
        final Handler handler = new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run(){
                //foobar();
                handler.postDelayed(this,100);
            }
        };
        handler.postDelayed(runnable,100);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        switch (item.getItemId()) {

            case R.id.action_refresh:
                ServerAPI.getInstance().getAllMessage(TchatActivity.this,getLogin(),getPassword());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }
}


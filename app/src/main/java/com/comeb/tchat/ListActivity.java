package com.comeb.tchat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.comeb.adapter.MyAdapter;
import com.comeb.async.ServerAPI;
import com.comeb.model.Message;
import com.comeb.model.MessageLeft;
import com.comeb.model.MessageRight;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements SyncListener{
    private ArrayList list;
    private static MyAdapter arrayAdapter;
    private static ListView listview;

    public static ListView getView() {
        return listview;
    }

    public static MyAdapter getAdapter() {
        return arrayAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listview = (ListView) findViewById(R.id.listv);
        list = new ArrayList<Message>();
        list.add(new MessageLeft("Toto", "bonjour"));
        list.add(new MessageLeft("Toto", "bonjour"));
        list.add(new MessageRight("Titi", "aurevoir"));
        list.add(new MessageRight("Titi", "aurevoir"));

        arrayAdapter = new MyAdapter(this, list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview.setAdapter(arrayAdapter);
        Context context = ListActivity.this;
        ServerAPI.getInstance().getAllMessage(this);


    }

    @Override
    public void onSuccess(ArrayList messages) {
        getAdapter().setList(messages);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure() {

    }
}

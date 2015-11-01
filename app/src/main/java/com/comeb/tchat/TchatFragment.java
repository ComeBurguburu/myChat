package com.comeb.tchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.comeb.adapter.SimpleRecyclerAdapter;
import com.comeb.async.ServerAPI;
import com.comeb.database.DatabaseHandler;
import com.comeb.model.Message;

import java.util.ArrayList;

/**
 * Created by côme on 16/10/2015.
 */
public class TchatFragment extends DummyFragment {
    private int color;
    private SimpleRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> encoded;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public TchatFragment() {
    }

    public SimpleRecyclerAdapter getAdapter() {
        return this.adapter;
    }

    @SuppressLint("ValidFragment")
    public TchatFragment(int color) {
        this.color = color;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tchat_fragment, container, false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);
        final EditText ed = (EditText) view.findViewById(R.id.edit);
        final ImageView preview = (ImageView) view.findViewById(R.id.preview);
        final ImageView send = (ImageView) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String message = ed.getText().toString();
                if (!message.equals("")) {
                    ed.setText("");
                    ServerAPI.getInstance().sendMessage(getContext(), message, encoded);
                    preview.setImageBitmap(null);
                    setEncoded(null);
                }
            }
        });

        ImageView addImageButton = (ImageView) view.findViewById(R.id.add);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  recyclerView.setHasFixedSize(true);
        if (adapter == null) {

            adapter = new SimpleRecyclerAdapter(new ArrayList<Message>(), getContext());
        }
        recyclerView.setAdapter(adapter);
        DatabaseHandler dao = DatabaseHandler.getInstance(getContext());

        getAdapter().setList(dao.getAllMessages());
        getAdapter().notifyDataSetChanged();

        return view;
    }

    protected void addImage() {
        int RESULT_LOAD_IMAGE = 1;

        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            getActivity().startActivityForResult(intent, RESULT_LOAD_IMAGE);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            getActivity().startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }

    }

    public void setEncoded(ArrayList<String> encoded) {
        this.encoded = encoded;
    }

}


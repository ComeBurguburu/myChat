package com.comeb.tchat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.comeb.adapter.SimpleRecyclerAdapter;
import com.comeb.model.Message;

import java.util.ArrayList;

/**
 * Created by côme on 16/10/2015.
 */
public class AboutFragment extends DummyFragment {
    private int color;
    private SimpleRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public AboutFragment() {
    }

    public SimpleRecyclerAdapter getAdapter() {
        return this.adapter;
    }

    @SuppressLint("ValidFragment")
    public AboutFragment(int color) {
        this.color = color;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (adapter == null) {
            adapter = new SimpleRecyclerAdapter(new ArrayList<Message>(), getContext());
        }
        recyclerView.setAdapter(adapter);

        return view;
    }
}


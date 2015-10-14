package com.comeb.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comeb.model.Message;
import com.comeb.tchat.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.VersionViewHolder> {
    List<Message> versionModels;
    private Message currentItem;


    public SimpleRecyclerAdapter(List<Message> versionModels) {
        this.versionModels = versionModels;

    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        currentItem = versionModels.get(i);

        versionViewHolder.setPseudo(versionModels.get(i).getPseudo());
            versionViewHolder.setMessage(versionModels.get(i).getMessage());
           // versionViewHolder.setDate(versionModels.get(i).getDate());
        if(versionModels.get(i).isLeft()==true) {
           versionViewHolder.setImageLeftResource(versionModels.get(i).getResImg());
        }else{
            versionViewHolder.setImageRightResource(versionModels.get(i).getResImg());
        }

    }

    @Override
    public int getItemCount() {

            return versionModels == null ? 0 : versionModels.size();
    }

    public void setList(ArrayList list) {
        if(list==null){
            return;
        }
        versionModels.clear();
        versionModels.addAll(list);
    }

    public List<Message> getVersionModels() {
        return versionModels== null ? new ArrayList<Message>():versionModels;
    }


    class VersionViewHolder extends RecyclerView.ViewHolder{
        private TextView message;
        private TextView pseudo;
        private TextView time;
        private ImageView thumb_image_left;
        private ImageView thumb_image_right;
        CardView cardItemLayout;


        public void setMessage(String message) {
            this.message.setText(message);
        }

        public void setPseudo(String pseudo) {
            this.pseudo.setText(pseudo);
        }


        public void setImageRightResource(int ressId) {
            this.thumb_image_right.setImageResource(ressId);

            thumb_image_left.setVisibility(View.INVISIBLE);
            thumb_image_right.setVisibility(View.VISIBLE);
        }

            public void setImageLeftResource(int ressId) {
                this.thumb_image_left.setImageResource(ressId);

                thumb_image_right.setVisibility(View.INVISIBLE);
            thumb_image_left.setVisibility(View.VISIBLE);
        }




        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            pseudo = (TextView)itemView.findViewById(R.id.pseudo);
            message = (TextView)itemView.findViewById(R.id.message);
            time = (TextView)itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                }
            });

            //Message item =(Message)list.get(position);

            thumb_image_left = (ImageView) itemView.findViewById(R.id.image_left);
            thumb_image_right = (ImageView) itemView.findViewById(R.id.image_right);


        }


    }



}

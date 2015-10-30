package com.comeb.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comeb.async.ServerAPI;
import com.comeb.model.Message;
import com.comeb.tchat.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.VersionViewHolder> {
    private final Context context;
    List<Message> versionModels;
    private Message currentItem;


    public SimpleRecyclerAdapter(List<Message> versionModels, Context context) {
        this.versionModels = versionModels;
        this.context = context;

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

        if (versionModels.get(i).isLeft() == true) {
            versionViewHolder.setImageLeftResource(versionModels.get(i).getResImg(), context);
        } else {
            versionViewHolder.setImageRightResource(versionModels.get(i).getResImg(), context);
        }
        versionViewHolder.setImage(versionModels.get(i).getImg(), context);
    }

    @Override
    public int getItemCount() {

        return versionModels == null ? 0 : versionModels.size();
    }

    public void setList(ArrayList<Message> list) {
        if (list == null) {
            return;
        }
        versionModels.clear();
        versionModels.addAll(list);
    }

    public List<Message> getVersionModels() {
        return versionModels == null ? new ArrayList<Message>() : versionModels;
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        CardView cardItemLayout;
        private TextView message;
        private TextView pseudo;
        private ImageView thumb_image_left;
        private ImageView thumb_image_right;
        private ImageView thumb_image_center;


        public VersionViewHolder(final View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            pseudo = (TextView) itemView.findViewById(R.id.pseudo);
            message = (TextView) itemView.findViewById(R.id.message);

            thumb_image_left = (ImageView) itemView.findViewById(R.id.image_left);
            thumb_image_right = (ImageView) itemView.findViewById(R.id.image_right);
            thumb_image_center = (ImageView) itemView.findViewById(R.id.image_center);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // item clicked
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage(currentItem.getInfo());
                    alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                }
            });
        }

        private void setMessage(String message) {
            this.message.setText(message);
        }

        public void setPseudo(String pseudo) {
            this.pseudo.setText(pseudo);
        }

        public void setImageRightResource(int ressId, Context context) {
            this.thumb_image_right.setImageResource(ressId);
            thumb_image_left.setVisibility(View.INVISIBLE);
            thumb_image_right.setVisibility(View.VISIBLE);
        }

        public void setImage(String url, Context context) {
            if (url != null) {
                if (thumb_image_center.getDrawable() == null) {
                    thumb_image_center.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_add_image));
                }
                ServerAPI.getInstance().getImage(thumb_image_center, url, context);

            } else {
                thumb_image_center.setImageBitmap(null);
            }
        }

        public void setImageLeftResource(int ressId, Context context) {
            this.thumb_image_left.setImageResource(ressId);
            thumb_image_right.setVisibility(View.INVISIBLE);
            thumb_image_left.setVisibility(View.VISIBLE);
        }


    }

}

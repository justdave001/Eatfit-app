package com.example.eatfit.Messages;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatfit.R;


public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView usernameTV;
    public TextView messageBodyTV;
    public ImageView userAvatarIV;
    public ImageView messageBodyIV;


    public MessageViewHolder(View itemView) {
        super(itemView);

        this.usernameTV = (TextView) itemView.findViewById(R.id.username);
        this.messageBodyTV = (TextView) itemView.findViewById(R.id.message_body);
        this.userAvatarIV = (ImageView) itemView.findViewById(R.id.user_avatar);
        this.messageBodyIV = (ImageView) itemView.findViewById(R.id.message_body_image);
    }
}

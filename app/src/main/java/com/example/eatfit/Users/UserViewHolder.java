package com.example.eatfit.Users;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatfit.R;


public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView userAvatar;
    public TextView toolTipTarget;

    public UserViewHolder(View itemView) {
        super(itemView);
        // The avatar imageView for the user
        this.userAvatar = itemView.findViewById(R.id.user_avatar);
        // The target for the ToolTip
        this.toolTipTarget =  itemView.findViewById(R.id.user_avatar_tooltip_target);
    }
}

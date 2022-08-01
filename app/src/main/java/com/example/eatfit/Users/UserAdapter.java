package com.example.eatfit.Users;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatfit.Activities.ChatActivity;
import com.example.eatfit.Activities.ChatMainActivity;

import com.example.eatfit.R;
import com.tomergoldst.tooltips.ToolTip;

import java.util.ArrayList;



public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<UserObject> mUserObjects;

    public UserAdapter(ArrayList<UserObject> userObjects){
        this.mUserObjects = userObjects;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_user_item, parent, false);
        return new UserViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        // Get the userObject from the ArrayList at that position
        final UserObject userObject = mUserObjects.get(position);

        // Set the colour of the avatar to match the user's preference as stored by the userObject
        holder.userAvatar.setColorFilter(Color.parseColor(userObject.getAvatarColour()));



        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ChatActivity.toolTipsManager != null) {
                    ChatActivity.toolTipsManager.findAndDismiss(holder.toolTipTarget);
                    buildAndShowToolTip(holder, userObject);

                } else {

                    // Show a Toast if there's no ToolTips Manager
                    Toast.makeText(ChatMainActivity.mainContext, userObject.getUsername(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buildAndShowToolTip(final UserViewHolder holder, UserObject userObject){
        // Build the ToolTip containing the username
        ToolTip.Builder builder = new ToolTip.Builder(ChatMainActivity.mainContext,
                holder.toolTipTarget,
                ChatActivity.mRootContainer,
                userObject.getUsername(),
                ToolTip.POSITION_BELOW);

        Log.d("name", userObject.getUsername());
        // Set the alignment of the ToolTip
        builder.setAlign(ToolTip.ALIGN_LEFT);
        // Set the background colour and text to match the user's avatar
        builder.setBackgroundColor(Color.parseColor(userObject.getAvatarColour()));
        // Show the ToolTip
        ChatActivity.toolTipsManager.show(builder.build());
        // Dismiss the ToolTip after a short delay
        Handler dismissToolTipHandler = new Handler(Looper.getMainLooper());
        Runnable dismissToolTipRunnable = new Runnable() {
            @Override
            public void run() {
                ChatActivity.toolTipsManager.findAndDismiss(holder.toolTipTarget);
            }
        };
        dismissToolTipHandler.postDelayed(dismissToolTipRunnable, 2000);
    }

    /**
     * @return the total number of items in the data set held by the Adapter
     */
    @Override
    public int getItemCount() {
        if (mUserObjects != null) {
            return mUserObjects.size();
        } else {
            return 0;
        }
    }
}

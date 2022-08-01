package com.example.eatfit.Messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


import com.example.eatfit.Activities.ChatActivity;
import com.example.eatfit.Activities.ChatMainActivity;
import com.example.eatfit.R;

import com.example.eatfit.R;

public class MessageDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_messages_ephemeral_body)
                .setPositiveButton(R.string.dialog_messages_ephemeral_positive_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing the count is iterated in onDismiss
                    }
                })
                .setNegativeButton(R.string.dialog_messages_ephemeral_negative_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Set the dialog to not show again
                        ChatMainActivity.sharedPreferences.edit().putBoolean(ChatActivity.DIALOG_DISMISSED_KEY, true).apply();
                    }
                });
        return builder.create();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        int counter = ChatMainActivity.sharedPreferences.getInt(ChatActivity.DIALOG_COUNTER_KEY, 1) + 1;
        ChatMainActivity.sharedPreferences.edit().putInt(ChatActivity.DIALOG_COUNTER_KEY, counter).apply();
    }
}

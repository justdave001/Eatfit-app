package com.example.eatfit.Users;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Objects;


public class UserObject {

    public static final String MESSAGE_TYPE = "Greeting";

    private static final Gson sGson = new Gson();
    private String mUsername;
    private String mAvatarColour;


    public UserObject(String username, String avatarColour){
        this.mUsername = username;
        this.mAvatarColour = avatarColour;
    }

    public static Message newNearbyMessage(UserObject userObject){
        return new Message(sGson.toJson(userObject).getBytes(Charset.forName("UTF-8")), MESSAGE_TYPE);
    }


    public static UserObject fromNearbyMessage(Message message){
        String nearbyMessageString = new String(message.getContent()).trim();
        return sGson.fromJson(
                (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                UserObject.class);
    }


    @Override
    public boolean equals(Object obj) {
        boolean match = false;
        if (obj != null && obj instanceof UserObject){
            if (Objects.equals(((UserObject) obj).mUsername, this.mUsername) &&
                    Objects.equals(((UserObject) obj).mAvatarColour, this.mAvatarColour)){
                match = true;
            }
        }
        return match;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getAvatarColour() {
        return mAvatarColour;
    }
}

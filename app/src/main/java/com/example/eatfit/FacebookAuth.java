package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eatfit.Activities.LoginActivity;
import com.example.eatfit.Activities.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookAuth extends LoginActivity {
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                    Intent i  = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,id, first_name, last_name");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();


}

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(@Nullable AccessToken oldAccessToken, @Nullable AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
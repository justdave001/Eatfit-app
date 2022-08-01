package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    public CallbackManager callbackManager;

    TabLayout tab_layout;
    ViewPager view_pager;
    FloatingActionButton google, facebook1;
    float o=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseInstallation.getCurrentInstallation().saveInBackground();


        callbackManager = CallbackManager.Factory.create();



        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        facebook1 = findViewById(R.id.facebook);
        google=findViewById(R.id.fab_google);


        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, FacebookAuth.class);
                startActivity(i);
            }
        });



        tab_layout.addTab(tab_layout.newTab().setText("Login"));
        tab_layout.addTab(tab_layout.newTab().setText("Sign up"));
        tab_layout.setTabGravity(tab_layout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tab_layout.getTabCount());
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        facebook1.setTranslationY(300);
        google.setTranslationY(300);
        tab_layout.setTranslationY(300);

        facebook1.setAlpha(o);
        google.setAlpha(o);
        tab_layout.setAlpha(o);

        facebook1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        tab_layout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
    }

}

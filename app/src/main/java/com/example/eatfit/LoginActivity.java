package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseInstallation;

public class LoginActivity extends AppCompatActivity {
    TabLayout tab_layout;
    ViewPager view_pager;
    FloatingActionButton google, facebook;
    float o=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        facebook = findViewById(R.id.facebook);
        google=findViewById(R.id.fab_google);

        tab_layout.addTab(tab_layout.newTab().setText("Login"));
        tab_layout.addTab(tab_layout.newTab().setText("Sign up"));
        tab_layout.setTabGravity(tab_layout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tab_layout.getTabCount());
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        facebook.setTranslationY(300);
        google.setTranslationY(300);
        tab_layout.setTranslationY(300);

        facebook.setAlpha(o);
        google.setAlpha(o);
        tab_layout.setAlpha(o);

        facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        tab_layout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
    }
}
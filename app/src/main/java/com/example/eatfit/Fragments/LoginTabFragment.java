package com.example.eatfit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eatfit.Activities.SplashActivity;
import com.example.eatfit.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginTabFragment extends Fragment {
    EditText username;
    EditText password;
    TextView forgot_password;
    Button loginBtn;

    ParseUser currentUser = ParseUser.getCurrentUser();

    float o =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container,false);

        if (currentUser!=null){
            loginScreen();
        }

        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        forgot_password = root.findViewById(R.id.forgot_password);
        loginBtn = root.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user!=null){
                                Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();
                                loginScreen();
                            }
                            else {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



        username.setTranslationX(800);
        password.setTranslationX(800);
        forgot_password.setTranslationX(800);
        loginBtn.setTranslationX(800);

        username.setAlpha(o);
        password.setAlpha(o);
        forgot_password.setAlpha(o);
        loginBtn.setAlpha(o);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgot_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }

    private void loginScreen() {
        Intent intent = new Intent(getContext(), SplashActivity.class);
        startActivity(intent);

    }


}

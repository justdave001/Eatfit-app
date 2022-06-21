package com.example.eatfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {
    EditText username;
    EditText password;
    TextView forgot_password;
    Button loginBtn;
    float o =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container,false);

        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        forgot_password = root.findViewById(R.id.forgot_password);
        loginBtn = root.findViewById(R.id.loginBtn);

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


}

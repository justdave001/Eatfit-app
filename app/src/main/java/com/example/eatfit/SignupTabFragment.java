package com.example.eatfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    EditText username;
    EditText f_name;
    EditText l_name;
    EditText signup_pass;
    EditText signup_pass2;
    Button signupBtn;
    float o =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container,false);
        username = root.findViewById(R.id.username);
        f_name = root.findViewById(R.id.f_name);
        l_name = root.findViewById(R.id.l_name);
        signup_pass = root.findViewById(R.id.signup_pass);
//        signup_pass2 = root.findViewById(R.id.signup_pass2);
        signupBtn = root.findViewById(R.id.signupBtn);

        username.setTranslationX(800);
        f_name.setTranslationX(800);
        l_name.setTranslationX(800);
        signup_pass.setTranslationX(800);
//        signup_pass2.setTranslationX(800);
        signupBtn.setTranslationX(800);

        username.setAlpha(o);
        f_name.setAlpha(o);
        l_name.setAlpha(o);
        signup_pass.setAlpha(o);
//        signup_pass2.setAlpha(o);
        signupBtn.setAlpha(o);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        f_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        l_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup_pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
//        signup_pass2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signupBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
}}

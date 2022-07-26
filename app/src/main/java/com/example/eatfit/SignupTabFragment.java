package com.example.eatfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupTabFragment extends Fragment {
    EditText username;
    EditText f_name;
    EditText l_name;
    EditText signup_pass;
    TextView firstName;
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
        signupBtn = root.findViewById(R.id.signupBtn);
        firstName = root.findViewById(R.id.firstName);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!username.getText().toString().isEmpty() && !signup_pass.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.put("f_name", f_name.getText().toString());
                    user.put("l_name", l_name.getText().toString());

                    user.setPassword(signup_pass.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();

                                username.setText("");
                                f_name.setText("");
                                l_name.setText("");
                                signup_pass.setText("");

                            } else {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        username.setTranslationX(800);
        f_name.setTranslationX(800);
        l_name.setTranslationX(800);
        signup_pass.setTranslationX(800);

        signupBtn.setTranslationX(800);

        username.setAlpha(o);
        f_name.setAlpha(o);
        l_name.setAlpha(o);
        signup_pass.setAlpha(o);

        signupBtn.setAlpha(o);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        f_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        l_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup_pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        signupBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
}}

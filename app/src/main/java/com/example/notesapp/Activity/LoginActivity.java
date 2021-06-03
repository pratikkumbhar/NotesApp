package com.example.notesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
EditText emailedittext,passwordedittext;
TextView loginbutton,donthaveaccount;
FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailedittext = findViewById(R.id.loginemailedittext);
        passwordedittext = findViewById(R.id.loginpassowrdedittext);
        loginbutton = findViewById(R.id.loginbutton);
        donthaveaccount = findViewById(R.id.donthaveaccount);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintlayout);
        AnimationDrawable drawable = (AnimationDrawable) constraintLayout.getBackground();
        drawable.setEnterFadeDuration(2000);
        drawable.setExitFadeDuration(4000);
        drawable.start();
        auth = FirebaseAuth.getInstance();
        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);

                startActivity(intent);
                finish();
            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = emailedittext.getText().toString();
                password = passwordedittext.getText().toString();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Fill info", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<=6)
                {
                    passwordedittext.setError("Min 6 letters");
                }
                else if (!email.matches(emailPattern))
                {
                    emailedittext.setError("Enter valid email");
                }
                else
                {
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent intent1 = new Intent(LoginActivity.this,HomeActivity.class);
                                intent1.putExtra("reset",true);
                               startActivity(intent1);
                               finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
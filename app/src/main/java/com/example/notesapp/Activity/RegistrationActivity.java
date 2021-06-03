package com.example.notesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Firebase.Users;
import com.example.notesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
EditText usernameedittext,emailedittext,passwordedittext;
TextView signupbutton;
TextView alreadyhaveaccount;
FirebaseAuth auth;
FirebaseDatabase database;
DatabaseReference reference;
String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameedittext = findViewById(R.id.signupusernameedittext);
        emailedittext = findViewById(R.id.signupemailedittext);
        passwordedittext = findViewById(R.id.signuppassowrdedittext);
        signupbutton = findViewById(R.id.signupbutton);
        alreadyhaveaccount = findViewById(R.id.alreadyhaveaccoutn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();

            }
        });



        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,username;
                email = emailedittext.getText().toString();
                password = passwordedittext.getText().toString();
                username = usernameedittext.getText().toString();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(username)){
                    Toast.makeText(RegistrationActivity.this, "Full all info", Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches(emailPattern)){
                    emailedittext.setError("Enter Valid Email");
                    Toast.makeText(RegistrationActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<=6){
                    passwordedittext.setError("min 6 letters needed");
                }
                else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isComplete()) {
                                Users users = new Users(auth.getUid(),username,email,password);
                                DatabaseReference reference = database.getReference().child("users").child(Objects.requireNonNull(auth.getUid()));
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                        startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                        finish();}
                                        else {
                                            Toast.makeText(RegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(RegistrationActivity.this, "EnterValidEntry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
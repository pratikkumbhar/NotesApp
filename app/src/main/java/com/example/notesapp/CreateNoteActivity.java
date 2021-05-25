package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateNoteActivity extends AppCompatActivity {
EditText titleEdittext,subtitleEdittext,noteEdittext;
ImageView priority1,priority2,priority3,priority4,saveButton;
String title,subtitle,notes;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        titleEdittext = findViewById(R.id.title_edittext);
        subtitleEdittext = findViewById(R.id.subtitle_edittext);
        noteEdittext = findViewById(R.id.notes_edittext);
        priority1 = findViewById(R.id.priority1_selcted);
        priority2 = findViewById(R.id.priority2_selcted);
        priority3 = findViewById(R.id.priority3_selcted);
        priority4 = findViewById(R.id.priority4_selcted);
        toolbar = findViewById(R.id.toolbar);
        saveButton = findViewById(R.id.saveNotesButton);
        setSupportActionBar(toolbar);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
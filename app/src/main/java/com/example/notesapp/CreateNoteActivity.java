package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Room.Entity;
import com.example.notesapp.ViewModel.MyViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {
EditText titleEdittext,subtitleEdittext,noteEdittext;
ImageView priority1,priority2,priority3,priority4;
String title,subtitle,notes;
String utitle,usubtitle,unotes;
int id;
FloatingActionButton saveButton,deleteButton;
Toolbar toolbar;
String priority = "1";
String upriority = "1";
MyViewModel viewModel;
boolean update = false;

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
        saveButton = findViewById(R.id.savebutton);
        deleteButton = findViewById(R.id.deletebutton);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

      update = getIntent().getBooleanExtra("update",false);

      if (update){
            deleteButton.setVisibility(View.VISIBLE);
         id = getIntent().getIntExtra("id",0);

         title = getIntent().getStringExtra("title");
         subtitle = getIntent().getStringExtra("subtitle");
         notes = getIntent().getStringExtra("notes");
         upriority = getIntent().getStringExtra("prority");

         if (upriority.equals("1"))
         {
             priority1.setImageResource(R.drawable.proritydone_icon);
             priority2.setImageResource(0);
             priority3.setImageResource(0);
             priority4.setImageResource(0);
         }

         if (upriority.equals("2"))
         {
             priority1.setImageResource(0);
             priority2.setImageResource(R.drawable.proritydone_icon);
             priority3.setImageResource(0);
             priority4.setImageResource(0);
         }

         if (upriority.equals("3"))
         {
             priority1.setImageResource(0);
             priority2.setImageResource(0);
             priority3.setImageResource(R.drawable.proritydone_icon);
             priority4.setImageResource(0);
         }

         if (upriority.equals("4"))
         {
             priority1.setImageResource(0);
             priority2.setImageResource(0);
             priority3.setImageResource(0);
             priority4.setImageResource(R.drawable.proritydone_icon);
         }

         titleEdittext.setText(title);
         subtitleEdittext.setText(subtitle);
         noteEdittext.setText(notes);


          priority1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority1.setImageResource(R.drawable.proritydone_icon);
                  priority2.setImageResource(0);
                  priority3.setImageResource(0);
                  priority4.setImageResource(0);

                  upriority = "1";
              }
          });
          priority2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority2.setImageResource(R.drawable.proritydone_icon);
                  priority1.setImageResource(0);
                  priority3.setImageResource(0);
                  priority4.setImageResource(0);
                  upriority = "2";
              }
          });
          priority3.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority3.setImageResource(R.drawable.proritydone_icon);
                  upriority = "3";
                  priority1.setImageResource(0);
                  priority2.setImageResource(0);
                  priority4.setImageResource(0);
              }
          });
          priority4.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority4.setImageResource(R.drawable.proritydone_icon);
                  upriority = "4";
                  priority1.setImageResource(0);
                  priority2.setImageResource(0);
                  priority3.setImageResource(0);
              }
          });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog sheetDialog = new BottomSheetDialog(CreateNoteActivity.this, R.style.BottomSheetStyle);
                View view = LayoutInflater.from(CreateNoteActivity.this).inflate(R.layout.delete_alert,(LinearLayout) findViewById(R.id.buttom_sheet));
                sheetDialog.setContentView(view);
                TextView yes,no;
                yes = view.findViewById(R.id.delete_yes);
                no = view.findViewById(R.id.delete_no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteNotes(id);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sheetDialog.dismiss();
                    }
                });
                sheetDialog.show();

            }
        });
         saveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 utitle = titleEdittext.getText().toString();
                 usubtitle = subtitleEdittext.getText().toString();
                 unotes = noteEdittext.getText().toString();
                 UpdateNotes(utitle,usubtitle,unotes,upriority,id);

             }
         });


      }
      else {

          saveButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  title = titleEdittext.getText().toString();
                  subtitle = subtitleEdittext.getText().toString();
                  notes = noteEdittext.getText().toString();
                  CreateNotes(title, subtitle, notes, priority);

              }
          });

          priority1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority1.setImageResource(R.drawable.proritydone_icon);
                  priority2.setImageResource(0);
                  priority3.setImageResource(0);
                  priority4.setImageResource(0);

                  priority = "1";
              }
          });
          priority2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority2.setImageResource(R.drawable.proritydone_icon);
                  priority1.setImageResource(0);
                  priority3.setImageResource(0);
                  priority4.setImageResource(0);
                  priority = "2";
              }
          });
          priority3.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority3.setImageResource(R.drawable.proritydone_icon);
                  priority = "3";
                  priority1.setImageResource(0);
                  priority2.setImageResource(0);
                  priority4.setImageResource(0);
              }
          });
          priority4.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  priority4.setImageResource(R.drawable.proritydone_icon);
                  priority = "4";
                  priority1.setImageResource(0);
                  priority2.setImageResource(0);
                  priority3.setImageResource(0);
              }
          });

      }

    }




    private void CreateNotes(String title, String subtitle, String notes,String priority) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy",date.getTime());
        Entity entity = new Entity();
        entity.title = title;
        entity.date = (String) sequence;
        entity.subtitle = subtitle;
        entity.notes = notes;
        entity.priority = priority;
        viewModel.insertnotes(entity);
        finish();
        Toast.makeText(this, "Note Creaeted", Toast.LENGTH_SHORT).show();

    }

    private void UpdateNotes(String title, String subtitle, String notes,String priority,int id)
    {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy",date.getTime());
        Entity entity = new Entity();
        entity.id = id;
        entity.title = title;
        entity.date = (String) sequence;
        entity.subtitle = subtitle;
        entity.notes = notes;
        entity.priority = priority;
        viewModel.updatenotes(entity);
        finish();
        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
    }
    private void DeleteNotes(int id)
    {

        Entity entity = new Entity();
        entity.id = id;
        viewModel.deletenotes(id);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        finish();
    }




}
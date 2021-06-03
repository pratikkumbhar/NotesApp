package com.example.notesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.Room.Database;
import com.example.notesapp.Room.Entity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
Button signoutButton,backupbutton;
FirebaseAuth auth;
   public ProgressDialog dialog;
    Database database2;
List<Entity> listLiveData;
FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        signoutButton = findViewById(R.id.signoutbutton);
        backupbutton = findViewById(R.id.backupbutton);
        auth = FirebaseAuth.getInstance();

dialog = new ProgressDialog(this);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
            }
        });
        backupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(SettingActivity.this);
                dialog.setTitle("Backing up...");
                dialog.setTitle("wait till it done");
                dialog.setCancelable(false);
                dialog.show();
                syndata syndata = new syndata(SettingActivity.this);
              syndata.execute();
              dialog.dismiss();
            }
        });

    }





    public static class syndata extends AsyncTask<Void, Void, List<Entity>>
    {

        Context context;
        public syndata(Context contex)
        {
             this.context = contex;
        }




        @Override
        protected List<Entity> doInBackground(Void... voids) {

            List<Entity> notesforsync = Database.getInstance(context).notesDao().getNotesforsync();
            Log.i("444",String.valueOf(notesforsync));
            return notesforsync;
        }

        @Override
        protected void onPostExecute(List<Entity> unused) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            DatabaseReference databaseReference = database.getReference().child("notes").child(Objects.requireNonNull(auth.getUid()));
            databaseReference.setValue(unused).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(context, "Backup Done", Toast.LENGTH_SHORT).show();


                    }
                    else
                    {
                        Toast.makeText(context, "Backup Failed", Toast.LENGTH_LONG).show();

                    }
                }
            });
            super.onPostExecute(unused);
        }
    }

}

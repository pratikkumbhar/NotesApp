package com.example.notesapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Adaptor.MyAdaptor;
import com.example.notesapp.Firebase.MyWorker;
import com.example.notesapp.R;
import com.example.notesapp.Room.Database;
import com.example.notesapp.Room.Entity;
import com.example.notesapp.ViewModel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "123";
    RecyclerView recyclerView;
FloatingActionButton fab;
MyViewModel viewMode;
MyAdaptor adaptor;
FirebaseAuth auth;
SearchView searchView;
TextView createnewNote;
List<Entity> filteredallnotes;
TextView nofilter,hightolow,lowtohigh;
ImageView settingbutton;


boolean resetDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycleView);
        fab =    findViewById(R.id.floting_button);
        nofilter = findViewById(R.id.nofilter);
        lowtohigh = findViewById(R.id.lowtohigh);
        createnewNote = findViewById(R.id.createnewnoteTextview);
        hightolow = findViewById(R.id.hightolow);
        settingbutton = findViewById(R.id.setting_button);
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        auth = FirebaseAuth.getInstance();
        searchView = findViewById(R.id.searchview);
        viewMode = ViewModelProviders.of(this).get(MyViewModel.class);
        resetDB =  getIntent().getBooleanExtra("reset",false);

        Log.i(TAG, "reset : "+resetDB);
        if (resetDB)
        {

            viewMode.deleteAllData();
            resetDB = false;
            SyncDataFromBackup();


        }
        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                finish();
            }
        });


        nofilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loaddata(0);
                nofilter.setBackgroundResource(R.drawable.filterselected_background);
                lowtohigh.setBackgroundResource(R.drawable.filtercontent_background);
                hightolow.setBackgroundResource(R.drawable.filtercontent_background);
            }
        });lowtohigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loaddata(2);
                nofilter.setBackgroundResource(R.drawable.filtercontent_background);
                lowtohigh.setBackgroundResource(R.drawable.filterselected_background);
                hightolow.setBackgroundResource(R.drawable.filtercontent_background);
            }
        });hightolow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loaddata(1);
                nofilter.setBackgroundResource(R.drawable.filtercontent_background);
                lowtohigh.setBackgroundResource(R.drawable.filtercontent_background);
                hightolow.setBackgroundResource(R.drawable.filterselected_background);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,CreateNoteActivity.class);
                startActivity(intent);
            }
        });


        viewMode.getallnotes.observe(this, new Observer<List<Entity>>() {
            @Override
            public void onChanged(List<Entity> entities) {
                setAdaptor(entities);
                filteredallnotes = entities;
            }
        });


        searchView.setQueryHint("Search here...");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });


    }


    private void NotesFilter(String newText) {
        ArrayList<Entity> filternotes = new ArrayList<>();
        for (Entity entity : this.filteredallnotes)
        {
            if (entity.getTitle().contains(newText) || entity.getSubtitle().contains(newText))
            {
                filternotes.add(entity);
            }
        }
            this.adaptor.searchNotes(filternotes);

    }

    private void Loaddata(int i) {
        if (i == 0) {
            viewMode.getallnotes.observe(this, new Observer<List<Entity>>() {
                @Override
                public void onChanged(List<Entity> entities) {
                    if (entities.size() == 0)
                    {
                        createnewNote.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        createnewNote.setVisibility(View.GONE);
                    }
                    setAdaptor(entities);



                    filteredallnotes = entities;
                }
            });
        }else if (i == 1)
        {
            viewMode.getlowtohigh.observe(this, new Observer<List<Entity>>() {
                @Override
                public void onChanged(List<Entity> entities) {
                    if (entities.size() == 0)
                    {
                        createnewNote.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        createnewNote.setVisibility(View.GONE);
                    }
                    setAdaptor(entities);
                    filteredallnotes = entities;
                }
            });
        }
        else if (i == 2){
            viewMode.gethightolow.observe(this, new Observer<List<Entity>>() {
                @Override
                public void onChanged(List<Entity> entities) {
                    if (entities.size() == 0)
                    {
                        createnewNote.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        createnewNote.setVisibility(View.GONE);
                    }
                    setAdaptor(entities);
                    filteredallnotes = entities;

                }
            });
        }
    }


    public void SyncDataFromBackup()
    {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Syncing Data...");
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.show();
        List<Entity> entities = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("notes").child(Objects.requireNonNull(auth.getUid()));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                entities.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    Log.i("555","syncing");
                    Entity entity = snap.getValue(Entity.class);
                    entities.add(entity);
                }
                Log.i("555",String.valueOf(entities));
                viewMode.insetAllnotes(entities);
                dialog.dismiss();
                finish();
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.i("555","Sync Data Failed");
                dialog.dismiss();
            }
        });

        return;
    }

    public void setAdaptor(List<Entity> entities)
    {
        Log.i("123",entities.toString());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adaptor = new MyAdaptor(entities,HomeActivity.this);
        recyclerView.setAdapter(adaptor);



    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }
    }




}
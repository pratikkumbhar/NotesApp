package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.notesapp.Adaptor.MyAdaptor;
import com.example.notesapp.Room.Entity;
import com.example.notesapp.ViewModel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
RecyclerView recyclerView;
FloatingActionButton fab;
MyViewModel viewMode;
MyAdaptor adaptor;
SearchView searchView;
List<Entity> filteredallnotes;
TextView nofilter,hightolow,lowtohigh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycleView);
        fab =    findViewById(R.id.floting_button);
        nofilter = findViewById(R.id.nofilter);
        lowtohigh = findViewById(R.id.lowtohigh);
        hightolow = findViewById(R.id.hightolow);
searchView = findViewById(R.id.searchview);
        viewMode = ViewModelProviders.of(this).get(MyViewModel.class);

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
                searchView.setIconified(true);
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
                    setAdaptor(entities);
                    filteredallnotes = entities;
                }
            });
        }else if (i == 1)
        {
            viewMode.getlowtohigh.observe(this, new Observer<List<Entity>>() {
                @Override
                public void onChanged(List<Entity> entities) {
                    setAdaptor(entities);
                    filteredallnotes = entities;
                }
            });
        }
        else if (i == 2){
            viewMode.gethightolow.observe(this, new Observer<List<Entity>>() {
                @Override
                public void onChanged(List<Entity> entities) {
                    setAdaptor(entities);
                    filteredallnotes = entities;
                }
            });
        }
    }

    public void setAdaptor(List<Entity> entities)
    {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adaptor = new MyAdaptor(entities,HomeActivity.this);
        recyclerView.setAdapter(adaptor);
    }

}
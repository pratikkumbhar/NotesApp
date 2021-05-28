package com.example.notesapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.Repository.NotesRepository;
import com.example.notesapp.Room.Entity;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    public NotesRepository repository;
    public LiveData<List<Entity>> getallnotes;
    public LiveData<List<Entity>> getlowtohigh;
    public LiveData<List<Entity>> gethightolow;

    public MyViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getallnotes = repository.getallnotes;
        getlowtohigh = repository.getlowtohigh;
        gethightolow = repository.gethightolow;

    }

    public void insertnotes(Entity entity)
    {
        repository.insertnotes(entity);
    }
    public void deletenotes(int id)
    {
        repository.deletenotes(id);
    }
    public void updatenotes(Entity entity)
    {
        repository.updatenotes(entity);
    }
}

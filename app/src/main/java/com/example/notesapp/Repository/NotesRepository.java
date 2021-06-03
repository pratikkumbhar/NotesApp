package com.example.notesapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesapp.Room.Dao;
import com.example.notesapp.Room.Database;
import com.example.notesapp.Room.Entity;

import java.util.List;

public class NotesRepository {

    public Dao notesDao;
    public LiveData<List<Entity>> getallnotes;
    public LiveData<List<Entity>> getlowtohigh;
    public LiveData<List<Entity>> gethightolow;



          public NotesRepository(Application application)
          {
              Database database = Database.getInstance(application);
              notesDao = database.notesDao();
              getallnotes = notesDao.getallNotes();
              getlowtohigh = notesDao.getlowtohigh();
              gethightolow = notesDao.gethightolow();

          }

       public void insertnotes(Entity entity)
          {
              notesDao.insertNotes(entity);
          }
          public void insetAllNotes(List<Entity> entities)
          {
              notesDao.insertList(entities);
          }
          public void deletenotes(int id)
          {
              notesDao.deleteNotes(id);
          }
         public void updatenotes(Entity entity)
          {
              notesDao.updateNotes(entity);
          }
          public void deleteAllData(){
              notesDao.deleteAllData();
          }
}

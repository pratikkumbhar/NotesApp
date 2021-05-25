package com.example.notesapp.Room;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM user_table ")
    List<Entity> getallNotes();

    @Insert
    void insertNotes(Entity entity);

    @Query("DELETE FROM user_table WHERE id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Entity entity);
}

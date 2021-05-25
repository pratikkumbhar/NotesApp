package com.example.notesapp.Room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = Entity.class,version = 1)
public abstract class Database extends RoomDatabase {

    public abstract Dao notesDao();

    public static volatile Database INSTANCE;

    public static Database getInstance(final Context context)
    {
        if (INSTANCE==null)
        {
            synchronized (Database.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),Database.class,"MyDatabase").build();
                }
            }

        }
            return INSTANCE;
    }


}

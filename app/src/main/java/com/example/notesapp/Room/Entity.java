package com.example.notesapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "user_table")
public class Entity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "notes_title")
    String title;

    @ColumnInfo(name = "notes_subtitle")
    String subtitle;

    @ColumnInfo(name = "notes")
    String notes;

    @ColumnInfo(name = "priority")
    String priority;
}


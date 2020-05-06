package com.kumar.androidarchitecture;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    public void insert(Note note);

    @Update
    public void update(Note note);

    @Delete
    public void delete(Note note);

    @Query("DELETE FROM notes_table")
    public void removeNotes();

    @Query("SELECT * FROM notes_table ORDER BY proirity DESC")
    LiveData<List<Note>> getAllNotes();
}

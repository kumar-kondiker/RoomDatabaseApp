package com.kumar.androidarchitecture;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteDatabaseRepository noteDatabaseRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabaseRepository=new NoteDatabaseRepository(application);
        allNotes=noteDatabaseRepository.getAllNotes();
    }

    public void insert(Note note)
    {
        noteDatabaseRepository.insert(note);
    }

    public void update(Note note)
    {
        noteDatabaseRepository.update(note);
    }

    public void delete(Note note)
    {
        noteDatabaseRepository.delete(note);
    }

    public void deleteAll()
    {
        noteDatabaseRepository.removeAllNotes();
    }

    public LiveData<List<Note>> getAllnotes()
    {
        return allNotes;
    }
}

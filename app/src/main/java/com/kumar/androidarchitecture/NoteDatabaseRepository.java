package com.kumar.androidarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteDatabaseRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> getAllNotes;

    public NoteDatabaseRepository(Application application)
    {
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao=database.noteDao();
        getAllNotes=noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note)
    {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note)
    {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    public void removeAllNotes()
    {
        new RemoveAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return  getAllNotes;
    }



    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;

        private InsertAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;

        private UpdateAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;

        private DeleteAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class RemoveAllNotesAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;

        private RemoveAllNotesAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.removeNotes();
            return null;
        }
    }



    private static class GetAllNotesAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;

        private GetAllNotesAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.getAllNotes();
            return null;
        }
    }
}

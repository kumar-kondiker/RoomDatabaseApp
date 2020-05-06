package com.kumar.androidarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.kumar.androidarchitecture.AddEditNoteActivity.EXTRA_DESCRIPTION;
import static com.kumar.androidarchitecture.AddEditNoteActivity.EXTRA_ID;
import static com.kumar.androidarchitecture.AddEditNoteActivity.EXTRA_PROIRITY;
import static com.kumar.androidarchitecture.AddEditNoteActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_RESULT_CODE = 1;
    private static final int EDIT_RESULT_CODE = 2;
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_RESULT_CODE);
            }
        });

        recyclerView = findViewById(R.id.note_reccyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = adapter.getNote(viewHolder.getAdapterPosition());
                noteViewModel.delete(note);
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.onItemClickListner(new NoteAdapter.OnItemClickListnerInf() {
            @Override
            public void onItemClick(Note note) {
             Intent intent=new Intent(MainActivity.this, AddEditNoteActivity.class);
             intent.putExtra(EXTRA_ID,note.getId());
             intent.putExtra(EXTRA_TITLE,note.getTitle());
             intent.putExtra(EXTRA_DESCRIPTION,note.getDescription());
             intent.putExtra(EXTRA_PROIRITY,note.getProirity());

             startActivityForResult(intent,EDIT_RESULT_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.mainmenu_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteallnotesmainmainu:
                noteViewModel.deleteAll();
                Toast.makeText(this, "Notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ADD_RESULT_CODE == requestCode && RESULT_OK == resultCode) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EXTRA_DESCRIPTION);
            int proirity = data.getIntExtra(EXTRA_PROIRITY, 0);

            Note note = new Note(title, description, proirity);
            noteViewModel.insert(note);
        }
        else if(EDIT_RESULT_CODE == requestCode && RESULT_OK == resultCode)
        {
            int id=data.getIntExtra(EXTRA_ID,-1);
            if (id==-1)
            {
                Toast.makeText(this, "Note not updated", Toast.LENGTH_SHORT).show();
                return;
            }


            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EXTRA_DESCRIPTION);
            int proirity = data.getIntExtra(EXTRA_PROIRITY, 0);

            Note note = new Note(title, description, proirity);
            note.setId(id);
            noteViewModel.update(note);
        }

    }
}

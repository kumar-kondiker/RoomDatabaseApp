package com.kumar.androidarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID="com.kumar.androidarchitechture.EXTRA_ID";
    public static final String EXTRA_TITLE="com.kumar.androidarchitechture.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.kumar.androidarchitechture.EXTRA_DESCRIPTION";
    public static final String EXTRA_PROIRITY="com.kumar.androidarchitechture.EXTRA_PROIRITY";

    EditText edittext_title;
    EditText editText_description;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        edittext_title = findViewById(R.id.id_add_title);
        editText_description = findViewById(R.id.id_add_description);
        numberPicker = findViewById(R.id.id_proirity);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            edittext_title.setText(intent.getStringExtra(EXTRA_TITLE));
            editText_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PROIRITY,-1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnote_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.savenote_menu:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = edittext_title.getText().toString().trim();
        String description = editText_description.getText().toString().trim();
        int proirity = numberPicker.getValue();

        if (title.isEmpty() || description.isEmpty())
        {
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DESCRIPTION,description);
        intent.putExtra(EXTRA_PROIRITY,proirity);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1)
        {
            intent.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,intent);
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}

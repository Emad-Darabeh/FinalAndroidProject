package com.example.androidlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    EditText noteTitleET, noteBodyET;
    Button saveNoteButton;
    ImageView backIV;

    String notebookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitleET = findViewById(R.id.note_title_edit_view);
        noteBodyET = findViewById(R.id.note_body_edit_view);

        backIV = findViewById(R.id.back_arrow);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNoteActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        saveNoteButton = findViewById(R.id.save_button);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noteTitle = noteTitleET.getText().toString();
                String noteBody = noteBodyET.getText().toString();

                if (noteTitle.equals("")) {
                    noteTitleET.setError("Enter a title");
                    return;
                }

                if (noteBody.equals("")) {
                    noteBodyET.setError("Enter a body");
                    return;
                }


                String noteId = FirebaseDatabase.getInstance().getReference().child("Note").push().getKey();

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                notebookId = getIntent().getStringExtra("notebookId");

                Note newNote;

                if (notebookId == null) {
                    String autoNotebookId = FirebaseDatabase.getInstance().getReference().child("Notebook").push().getKey();
                    Notebook newNotebook = new Notebook("auto created", formattedDate, autoNotebookId, userId);
                    FirebaseDatabase.getInstance().getReference().child("Notebook").child(autoNotebookId).setValue(newNotebook);

                    newNote = new Note(noteTitle, formattedDate, noteBody, autoNotebookId, userId);
                } else {
                    newNote = new Note(noteTitle, formattedDate, noteBody, notebookId, userId);
                }

                FirebaseDatabase.getInstance().getReference().child("Note").child(noteId).setValue(newNote);

                Intent intent = new Intent(AddNoteActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}

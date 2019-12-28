package com.example.androidlabproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    RecyclerView notesRV;
    FloatingActionButton addNoteFBtn;
    ImageView backIV;

    NoteAdapter noteAdapter;

    List<Note> noteList  = new ArrayList<>();

    String notebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);



        backIV = findViewById(R.id.back_arrow);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        addNoteFBtn = findViewById(R.id.fab);
        addNoteFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                intent.putExtra("notebookId", getIntent().getStringExtra("notebookId"));
                startActivity(intent);
            }
        });

        getNotes();

        notesRV = findViewById(R.id.notes_recycler_view);
        notesRV.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this ,noteList);
        notesRV.setAdapter(noteAdapter);

    }

    private void getNotes() {
        FirebaseDatabase.getInstance().getReference().child("Note")
                .addValueEventListener(new ValueEventListener() {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        noteList.clear();
                        notebookId = getIntent().getStringExtra("notebookId");
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){

                            Note note = snapshot.getValue(Note.class);
                            Log.e("note",note.toString());
                            if (notebookId != null) {
                                if (note.getNotebookId().equals(notebookId)) {
                                    noteList.add(note);
                                }
                            } else if (note.getUserId().equals(uid)){
                                noteList.add(note);
                            }

                        }
                        if (noteList.size() > 0)
                            noteAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}

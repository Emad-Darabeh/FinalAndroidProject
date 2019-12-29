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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    ImageView addNotebookIV;
    TextView showNotebooksTV, showNotesTV, signOutTV;
    RecyclerView notesRV;

    NoteAdapter noteAdapter;

    List<Note> noteList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addNotebookIV = findViewById(R.id.add_notebook_image_view);
        addNotebookIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AddNotebookActivity.class);
                startActivity(intent);
            }
        });

        showNotebooksTV = findViewById(R.id.show_notebooks_text_view);
        showNotebooksTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, NotebooksActivity.class);
                startActivity(intent);
            }
        });

        showNotesTV = findViewById(R.id.show_notes_text_view);
        showNotesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        signOutTV = findViewById(R.id.sign_out_text_view);
        signOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomePage.this , SignInSignUpActivity.class);
                startActivity(intent);
            }
        });

        getNotesPreview();

        notesRV = findViewById(R.id.notes_recycler_view);
        notesRV.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this ,noteList);
        notesRV.setAdapter(noteAdapter);
    }

    private void getNotesPreview() {
        FirebaseDatabase.getInstance().getReference().child("Note")
                .addValueEventListener(new ValueEventListener() {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        noteList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){

                            Note note = snapshot.getValue(Note.class);
                            Log.e("note",note.toString());
                            if (note.getUserId().equals(uid))
                                noteList.add(note);

                            if (noteList.size() == 4)
                                break;
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

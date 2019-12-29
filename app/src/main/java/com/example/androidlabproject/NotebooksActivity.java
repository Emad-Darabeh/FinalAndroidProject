package com.example.androidlabproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class NotebooksActivity extends AppCompatActivity {

    RecyclerView notebooksRV;
    FloatingActionButton addNotebookFBtn;
    ImageView backIV;

    NotebookAdapter notebookAdapter;

    List<Notebook> notebookList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebooks);

        backIV = findViewById(R.id.back_arrow);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotebooksActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        addNotebookFBtn = findViewById(R.id.fab);
        addNotebookFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotebooksActivity.this, AddNotebookActivity.class);
                startActivity(intent);
            }
        });

        getNotebooks();

        notebooksRV = findViewById(R.id.notebooks_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        notebooksRV.setLayoutManager(layoutManager);

        notebookAdapter = new NotebookAdapter(this ,notebookList);
        notebooksRV.setAdapter(notebookAdapter);
    }

    private void getNotebooks() {
        FirebaseDatabase.getInstance().getReference().child("Notebook")
                .addValueEventListener(new ValueEventListener() {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        notebookList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){

                            Notebook notebook = snapshot.getValue(Notebook.class);
                            Log.e("notebook",notebook.toString());
                            if (notebook.getUserId().equals(uid))
                                notebookList.add(notebook);

                        }
                        notebookAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}

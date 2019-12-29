package com.example.androidlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotebookActivity extends AppCompatActivity {

    TextView cancelTV;
    EditText notebookNameET;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notebook);

        cancelTV = findViewById(R.id.cancel_text_view);
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNotebookActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        notebookNameET = findViewById(R.id.notebook_name_edit_view);

        saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notebookTitle = notebookNameET.getText().toString();

                if (notebookTitle.equals("")) {
                    notebookNameET.setError("Enter a name");
                    return;
                }

                String notebookId = FirebaseDatabase.getInstance().getReference().child("Notebook").push().getKey();

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Notebook newNotebook = new Notebook(notebookTitle, formattedDate, notebookId, userId);

                FirebaseDatabase.getInstance().getReference().child("Notebook").child(notebookId).setValue(newNotebook);

                Intent intent = new Intent(AddNotebookActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}

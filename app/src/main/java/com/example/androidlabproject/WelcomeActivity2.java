package com.example.androidlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity2 extends AppCompatActivity {

    TextView skipTV;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_2);

        skipTV = findViewById(R.id.skip_text_view);
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity2.this, SignInSignUpActivity.class);
                startActivity(intent);
            }
        });
        nextBtn = findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity2.this, SignInSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}

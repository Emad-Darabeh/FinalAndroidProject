package com.example.androidlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInSignUpActivity extends AppCompatActivity {

    Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        signInBtn = findViewById(R.id.sign_in_button);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInSignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        signUpBtn = findViewById(R.id.sign_up_button);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInSignUpActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}

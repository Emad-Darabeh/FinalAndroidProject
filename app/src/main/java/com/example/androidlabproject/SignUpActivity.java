package com.example.androidlabproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ImageView closeIcon;
    TextView signInTV, forgotPasswordTV;
    EditText emailET, passwordET;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            Intent intent = new Intent(SignUpActivity.this , HomePage.class);
            startActivity(intent);
        }

        emailET = findViewById(R.id.email_edit_view);
        passwordET = findViewById(R.id.password_edit_view);
        signUpBtn = findViewById(R.id.sign_up_button);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if(email.equals("")){
                    emailET.setError("You Must to Enter Email");
                    return;
                }

                if(password.equals("")){
                    passwordET.setError("You Must to Enter Password");
                    return;
                }

                doSingUp(email, password);
            }
        });

        signInTV = findViewById(R.id.sign_in_text_view);
        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordTV = findViewById(R.id.forgot_password_text_view);
        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInSignUpActivity.class);
                startActivity(intent);
            }
        });

    }



    public void doSingUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String emailF = user.getEmail();
                            String uid = user.getUid();
                            Map<String, Object> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("email", emailF);
                            data.put("createdAt", new Date().getTime());

                            FirebaseDatabase.getInstance().getReference().child("User").child(uid).setValue(data)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("error", e.toString());
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(SignUpActivity.this, HomePage.class);
                                            startActivity(intent);
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}

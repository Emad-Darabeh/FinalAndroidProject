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

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ImageView closeIcon;
    TextView signUpTV;
    EditText emailET, passwordET;
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null) {
            Intent intent = new Intent(LogInActivity.this , HomePage.class);
            startActivity(intent);
        }

        emailET = findViewById(R.id.email_edit_view);
        passwordET = findViewById(R.id.password_edit_view);
        signInBtn = findViewById(R.id.sign_in_button);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if (email.equals("")) {
                    emailET.setError("You Must to Enter Email");
                    return;
                }

                if (password.equals("")) {
                    passwordET.setError("You Must to Enter Password");
                    return;
                }

                LogInActivity.this.doSignIn(emailET.getText().toString(), passwordET.getText().toString());
            }
        });

        signUpTV = findViewById(R.id.sign_up_text_view);
    //    signUpTV.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
    //            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
    //            LogInActivity.this.startActivity(intent);
    //        }
    //    });

        closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignInSignUpActivity.class);
                LogInActivity.this.startActivity(intent);
            }
        });

    }

    private void doSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String, Object> data = new HashMap<>();
                            data.put("lastSignIn", new Date().getTime());

                            FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).updateChildren(data)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LogInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("error",e.getLocalizedMessage());
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(LogInActivity.this , HomePage.class);
                                            startActivity(intent);
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

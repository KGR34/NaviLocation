package com.varsitycollege.simplelocation;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    final int REQ_CODE = 100;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("maps");
    EditText txt_email, txt_password;
    Button btn_login, btn_signup;
    String email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        mAuth = FirebaseAuth.getInstance();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA};

                if (!AppPermissions.hasPermissions(LoginActivity.this, permissions)) {
                    ActivityCompat.requestPermissions(LoginActivity.this, permissions, REQ_CODE);
                }


                email = txt_email.getText().toString().trim();
                password = txt_password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener
                        (new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "user"
                                            + mAuth.getCurrentUser().getEmail() + "logged in", Toast.LENGTH_SHORT).show();

                                    GlobalUser.userID = email;
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = txt_email.getText().toString().trim();
                password = txt_password.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                        (new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "user created"
                                            + mAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                                    GlobalUser.userID = email;
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });
    }

}

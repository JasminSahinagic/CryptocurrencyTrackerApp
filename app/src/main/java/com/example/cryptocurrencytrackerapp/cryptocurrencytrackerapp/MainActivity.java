package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogIn;
    private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPass;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainSetUp();
    }


    public void mainSetUp() {
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    startActivity(new Intent(MainActivity.this, CryptoCurrencyTracker.class));
                    Toast.makeText(MainActivity.this, "User connected", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        buttonLogIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogIn:
                logIn();
                break;
            case R.id.buttonSignUp:
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
                break;
        }
    }

    private void logIn() {
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = auth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "User logged in.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, CryptoCurrencyTracker.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

package com.example.movieapp21;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseAuth mAuth1;

    private EditText email, password;
    Button login, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth1 = FirebaseAuth.getInstance();

         email = findViewById(R.id.emailLogin);
         password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.loginbtn);
        signUp = findViewById(R.id.signUp);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);




    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn:
                ClickLogin();
                break;
            case R.id.signUp:
                ClickSignUp();
                break;
        }

    }

    //This is method for doing operation of check login
    private void ClickLogin() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (userEmail.isEmpty()) {
            email.setError("Email cannot be empty!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }
        if (userPassword.isEmpty()) {
            password.setError("Password cannot be empty!");
            password.requestFocus();
            return;
        }

        mAuth1.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(startIntent);
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();

                }
            }
        });
//        } else {
//            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplicationContext().startActivity(startIntent);
//        }

    }

    //The method for opening the registration page and another processes or checks for registering
    private void ClickSignUp() {
        Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(startIntent);
    }
}

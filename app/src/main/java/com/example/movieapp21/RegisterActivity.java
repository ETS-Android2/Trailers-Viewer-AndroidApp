package com.example.movieapp21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText email, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button registerUser = (Button) findViewById(R.id.loginbtn);
        registerUser.setOnClickListener(this);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        password2 = findViewById(R.id.password2);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn:
                registerUser();
                break;
        }

    }

    private void registerUser() {


        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userPassword2 = password2.getText().toString().trim();

        if(userEmail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(userPassword.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(userPassword2.isEmpty()){
            password2.setError("Password repeat is required!");
            password2.requestFocus();
            return;
        }
        if(!userPassword.equals(userPassword2)){
            password2.setError("Passwords do not match");
            password2.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }
        if(password.length()<6){
            password.setError("Password should be longer than 6 characters");
            password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(userEmail, userPassword);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        //
                                        getApplicationContext().startActivity(startIntent);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
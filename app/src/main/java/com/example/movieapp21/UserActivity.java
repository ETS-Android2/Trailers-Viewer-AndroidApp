package com.example.movieapp21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {
    Button changeEmail,changepass,savedMovies, logout;
    TextView email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        email = (TextView) findViewById(R.id.emailtv);
        changeEmail = (Button) findViewById(R.id.changeemailtv);
        savedMovies = (Button) findViewById(R.id.savedtv);
        logout = (Button) findViewById(R.id.logouttv);
        changepass = (Button) findViewById(R.id.changepasstv);
        String userEmail = mAuth.getInstance().getCurrentUser().getEmail();

        email.setText(userEmail);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                getApplicationContext().startActivity(startIntent);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = (LayoutInflater.from(UserActivity.this)).inflate(R.layout.user_input, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UserActivity.this);
                alertBuilder.setView(v);
                EditText userInput = (EditText) v.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               String userinputtext = userInput.getText().toString();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(userinputtext)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(UserActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(UserActivity.this, "Password Change Failed!", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = (LayoutInflater.from(UserActivity.this)).inflate(R.layout.user_input, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UserActivity.this);
                alertBuilder.setView(v);
                EditText userInput = (EditText) v.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String userinputtext = userInput.getText().toString();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateEmail(userinputtext)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(UserActivity.this, "Email Changed", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(UserActivity.this, "Email Change Failed!", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        savedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SavedActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(startIntent);
            }
        });
    }
}
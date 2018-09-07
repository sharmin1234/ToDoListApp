package com.sharmin.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private EditText editTextEmail, editTextPassword;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    CheckBox checkBoxRemember;
    Button btnLogIn;
    boolean logged_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkboxLogIn);
        btnLogIn = findViewById(R.id.btnLogIn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        sharedPrefs = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxRemember.isChecked()){
                    editor.putBoolean("logged_in", true);
                    editor.apply();
                }
                else {
                    editor.putBoolean("logged_in", false);
                    editor.apply();
                }
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        checkSession();
    }

    public void onSignUpClick(View view) {

        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void logIn() {

        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String dataEmail = data.child("email").getValue(String.class);
                    String dataPass = data.child("password").getValue(String.class);

                    if (email.equals(dataEmail) && password.equals(dataPass)){
                        startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void checkSession(){
        logged_in = sharedPrefs.getBoolean("logged_in", false);
        if (logged_in) {
            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
            finish();
        }
    }
}


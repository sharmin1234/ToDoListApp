package com.sharmin.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private EditText editTextEmail, editTextPassword;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    CheckBox checkBoxRemember;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkboxSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().length() > 0 &&
                        editTextPassword.getText().toString().length() > 0){
                    signUp();
                }
            }
        });
    }

    public void signUp() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
        String id = databaseReference.push().getKey();

        databaseReference.child(id).setValue(new UserInfo(email, password));

        startActivity(new Intent(SignUpActivity.this, DashBoardActivity.class));
        finish();
    }
}

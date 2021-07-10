package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogIn,btnSignUp;
    private EditText etEmail, etPassword, etClass, etSchoolNumber;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private String USER_KEY = "User";

    private Intent startMain, loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogIn:
                startActivity(loginActivity);
                break;

            case R.id.btnSignUp:
                signUpInAuth();
                break;
        }

    }

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etClass = findViewById(R.id.etClass);
        etSchoolNumber = findViewById(R.id.etSchoolNumber);

        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        database = FirebaseDatabase.getInstance().getReference(USER_KEY);
        auth = FirebaseAuth.getInstance();

        startMain = new Intent(this, MainActivity.class);
        loginActivity = new Intent(this,LogIn.class);
    }

    private void signUpInAuth() {
        if (!TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString()) && !TextUtils.isEmpty(etClass.getText().toString()) && !TextUtils.isEmpty(etSchoolNumber.getText().toString())) {

            auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Вы зарегестрированы", Toast.LENGTH_SHORT).show();
                        userDataToDatabase();
                        startActivity(loginActivity);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void userDataToDatabase() {

        String id = database.getKey();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String schoolClass = etClass.getText().toString();
        String schoolNumber = etSchoolNumber.getText().toString();

        User user = new User(email,password,id,schoolClass,schoolNumber);
        if (!etEmail.getText().equals(null) && !etPassword.getText().equals(null)) {
            database.push().setValue(user);
        } else {
            Toast.makeText(this, "data base error", Toast.LENGTH_SHORT).show();
        }
    }
}
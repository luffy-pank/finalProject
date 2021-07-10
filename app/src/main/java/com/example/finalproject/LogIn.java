package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogIn,btnSignUp;
    private EditText etEmail, etPassword;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private String USER_KEY = "User";
    private ImageView ivAvatar;

    private Intent startMain,registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init();




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser fUser = auth.getCurrentUser();
        if (fUser != null) {

        }
    }

    private void init(){
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);


        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        database = FirebaseDatabase.getInstance().getReference(USER_KEY);
        auth = FirebaseAuth.getInstance();

        startMain = new Intent(this, MainActivity.class);
        registerActivity = new Intent(this, RegisterActivity.class);





    }

    @Override
    public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnLogIn:
                        if (!TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString())){
                            auth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull  Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LogIn.this, "Вы вошли", Toast.LENGTH_SHORT).show();
                                        etPassword.setText("");
                                        startActivity(startMain);
                                    } else {
                                        Toast.makeText(LogIn.this, "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                        break;

                    case R.id.btnSignUp:
                        startActivity(registerActivity);
                        break;
                }

            }




    }

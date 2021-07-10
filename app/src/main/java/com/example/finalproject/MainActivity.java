package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private EditText etMessage;
    private FloatingActionButton btnSend;
    private ListView lvMessage;
    private FirebaseListAdapter<Message> adapter;

    private String MESSAGE_KEY = "Message";


    private DatabaseReference database;
    private FirebaseAuth auth;
    private Intent startLogIn, startProfile;
    private ImageView checkProfile;



    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void displayAllMessage() {
        Query query = database = FirebaseDatabase.getInstance().getReference(MESSAGE_KEY);
        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(query,Message.class)
                .setLayout(R.layout.item)
                .build();
        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull  View v, @NonNull  Message model, int position) {
                TextView tvMessageUser, tvMessageTime, bvMessageText;
                tvMessageUser = v.findViewById(R.id.tvMessageUser);
                tvMessageTime = v.findViewById(R.id.tvMessageTime);
                bvMessageText = v.findViewById(R.id.bvMessageText);

                tvMessageUser.setText(model.getUserName());
//                tvMessageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
                bvMessageText.setText(model.getUserMessage());
            }

        };
        lvMessage.setAdapter(adapter);
//        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String username =
//            }
//        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkProfile:
                startActivity(startProfile);
                finish();
                break;
            case  R.id.btnSend:
                if (etMessage.getText().toString().equals("")) {
                    return;
                }
                FirebaseUser fUser = auth.getCurrentUser();
                String email = fUser.getEmail();
                String textMessage = etMessage.getText().toString();
                Message message = new Message(email, textMessage);
                database.push().setValue(message);
                etMessage.setText("");


        }




    }

    private void init() {
        checkProfile = findViewById(R.id.checkProfile);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        lvMessage = findViewById(R.id.lvMessage);
        checkProfile.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference(MESSAGE_KEY);


        startLogIn = new Intent(this, LogIn.class);
        startProfile = new Intent(this, Profile.class);
        loginCheck();

    }

    private void loginCheck() {
        FirebaseUser fUser = auth.getCurrentUser();
        if (fUser == null) {
            startActivity(startLogIn);
            finish();
        } else {
            displayAllMessage();
        }
    }

}
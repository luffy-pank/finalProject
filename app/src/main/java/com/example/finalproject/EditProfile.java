package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivAvatar, ivLogOut;
    private TextView tvEmail,tvDescription,tvSchoolName,tvClass;
    private Button ivChat, btnEditProfile;
    private Intent startMain, startLogin;

    private FirebaseAuth auth;
    private DatabaseReference database;
    private String USER_KEY = "User";
    private String EMAIL_KEY = "email";






    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        init();
        getDataFromDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAvatar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Pick Image from")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                            }



        });
                AlertDialog alert = builder.create();
                alert.show();
                break;


            case R.id.ivChat:
                startActivity(startMain);
                finish();
                break;



        }
    }

    private void init() {
        ivAvatar = findViewById(R.id.ivAvatar);
        ivLogOut = findViewById(R.id.ivLogOut);
        tvEmail = findViewById(R.id.tvEmail);
        tvDescription = findViewById(R.id.tvDescription);
        tvSchoolName = findViewById(R.id.tvSchoolName);
        tvClass = findViewById(R.id.tvClass);
        ivChat = findViewById(R.id.ivChat);

        startMain = new Intent(this, MainActivity.class);
        startLogin = new Intent(this, LogIn.class);


        ivAvatar.setOnClickListener(this);
        ivLogOut.setOnClickListener(this);
        ivChat.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);

        database = FirebaseDatabase.getInstance().getReference(USER_KEY);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivAvatar.setImageBitmap(selectedImage);
                Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditProfile.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void getDataFromDB() {
        FirebaseUser fUser = auth.getCurrentUser();
        String email = fUser.getEmail();
        database.orderByChild(EMAIL_KEY).equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);



                tvEmail.setText(user.getEmail());
                tvClass.setText(user.getSchoolClass());
                tvSchoolName.setText(user.getSchoolNumber());
                tvDescription.setText("пусто");



            }}

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

//    private void setData(DataSnapshot snapshot){
//        User user = snapshot.getValue(User.class);
//        tvEmail.setText(user.getEmail());
//        tvClass.setText(user.getSchoolClass());
//        tvSchoolName.setText(user.getSchoolNumber());
//        tvDescription.setText("пусто");
//    }
        }}
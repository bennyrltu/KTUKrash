package edu.ktu.ktukrash;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity {
    CardView cardProfile;
    CardView cardDraw;
    CardView carGallery;
    CardView cardFill;
    CardView cardLogout;
    //private Button button1, button2,button3;
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

//        Button button1 = findViewById(R.id.declarationButton);
//        Button button2 = findViewById(R.id.drawButton);
//        Button button3 = findViewById(R.id.UppImagesButton);
//
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivityNew();
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openPaint();
//            }
//        });
//
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openActivityNewUpp();
//            }
//        });

        cardProfile = findViewById(R.id.cardProfile);
        cardDraw = findViewById(R.id.cardDraw);
        carGallery = findViewById(R.id.cardGallery);
        cardFill = findViewById(R.id.cardFill);
        cardLogout = findViewById(R.id.cardLogout);

        cardProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                openPaint();

            }
        });

        cardDraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                openPaint();

            }
        });

        carGallery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                openPaint();

            }
        });

        cardFill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                openPaint();

            }
        });

        cardLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(dashboard.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = findViewById(R.id.fullName);
        final TextView emailTextView = findViewById(R.id.email);
        final TextView ageTextView = findViewById(R.id.age);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;

//                    fullNameTextView.setText(fullName);
//                    emailTextView.setText(email);
//                    ageTextView.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dashboard.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void showToast(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

    private void openPaint() {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }

    public void openActivityNew(){

        Intent intent = new Intent(this, DeclarationStart.class);
        startActivity(intent);
    }

    public void openActivityNewUpp(){

        Intent intent = new Intent(this, EventPictures.class);
        startActivity(intent);
    }
}

package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button button1, button2, button3;
    private Button logout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private CardView cardProfile;
    private CardView cardDraw;
    private CardView carGallery;
    private CardView cardFill;
    private CardView cardUpl;
    private CardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        //setContentView(R.layout.activity_profile);

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
//                openActivityNew2();
//            }
//        });
//
//        logout = findViewById(R.id.signOut);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//            }
//        });

        cardProfile = findViewById(R.id.cardProfile);
        cardDraw = findViewById(R.id.cardDraw);
        carGallery = findViewById(R.id.cardGallery);
        cardFill = findViewById(R.id.cardFill);
        cardUpl = findViewById(R.id.cardUpl);
        cardLogout = findViewById(R.id.cardLogout);

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showToast("yep");

            }
        });

        cardDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openPaint();
                showToast("yep");
            }
        });

        carGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showToast("yep");

            }
        });

        cardFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivityNew();
                showToast("yep");
            }
        });

        cardUpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivityNew2();
                showToast("yep");
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                showToast("yep");
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
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

                if (userProfile != null) {
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
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openPaint() {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }

    public void openActivityNew() {
        Intent intent = new Intent(this, DeclarationStart.class);
        startActivity(intent);
    }

    public void openActivityNew2() {
        Intent intent = new Intent(this, EventPictures.class);
        startActivity(intent);
    }
}
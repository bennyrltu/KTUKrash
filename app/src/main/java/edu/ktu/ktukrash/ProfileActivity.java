package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private Button button1, button2;
    private Button logout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button button1 = findViewById(R.id.declarationButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNew();
            }
        });

        logout = findViewById(R.id.signOut);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
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

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void openActivityNew(){
        Intent intent = new Intent(this, DeclarationStart.class);
        startActivity(intent);
    }
    }
package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button button1 = (Button) findViewById(R.id.declarationButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNew();
            }
        });
    }
    public void openActivityNew(){
        Intent intent = new Intent(this, DeclarationStart.class);
        startActivity(intent);
    }
    }
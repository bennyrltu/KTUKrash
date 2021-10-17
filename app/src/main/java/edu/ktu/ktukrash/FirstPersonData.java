package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FirstPersonData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_person_data);

        Intent intent = getIntent();
        String text = intent.getStringExtra(DeclarationCars.EXTRA_TEXT);
        String text2 = intent.getStringExtra(DeclarationCars.EXTRA_TEXT2);

        TextView textView1 = (TextView) findViewById(R.id.firstPersonNumber);
        TextView textView2 = (TextView) findViewById(R.id.secondPersonNumber);

        textView1.setText(text);
        textView2.setText(text2);
    }
}
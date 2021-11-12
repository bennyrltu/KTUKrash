package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeclarationStart extends AppCompatActivity  {

    private Button button, continueButton;
    private Switch switchas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_start);

        button = (Button) findViewById(R.id.button);
        continueButton = (Button) findViewById(R.id.continueButton);
        switchas = (Switch) findViewById(R.id.continueSwitch);

        continueButton.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:112"));
                startActivity(intent);

            }
        });

        switchas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    continueButton.setVisibility(View.VISIBLE);
                }
                else{
                    continueButton.setVisibility(View.GONE);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNew();
            }
        });
    }
    public void openActivityNew(){
        Intent intent = new Intent(this, DeclarationCars.class);
        startActivity(intent);
    }

}
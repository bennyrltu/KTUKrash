package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class A_FinalUI_4 extends AppCompatActivity {

    private Button backButton,frontButton;

    private EditText companyName, policy, greenCard, agency, name, address, country, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui4);

        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        companyName = findViewById(R.id.CompanyName);
        policy = findViewById(R.id.editTextTextPersonName3);
        greenCard = findViewById(R.id.editTextTextPersonName4);
        agency = findViewById(R.id.editTextTextPersonName5);
        name = findViewById(R.id.editTextTextPersonName6);
        address = findViewById(R.id.editTextTextPersonName7);
        country = findViewById(R.id.editTextTextPersonName8);
        email = findViewById(R.id.editTextTextPersonName9);


        frontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });
    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, A_FinalUI_3.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_FinalUI_5.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        data1.put("FP_Insurance_Name", companyName.getText().toString());
        data1.put("FP_Insurance_Policy", policy.getText().toString());
        data1.put("FP_Insurance_Green_Card", greenCard.getText().toString());
        data1.put("FP_Insurance_Agency", agency.getText().toString());
        data1.put("FP_Insurance_Name2", name.getText().toString());
        data1.put("FP_Insurance_Address", address.getText().toString());
        data1.put("FP_Insurance_Country", country.getText().toString());
        data1.put("FP_Insurance_Email", email.getText().toString());

        intent.putExtra("pdfData1", data1);
        startActivity(intent);
    }
}
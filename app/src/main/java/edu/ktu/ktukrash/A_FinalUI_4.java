package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;

public class A_FinalUI_4 extends AppCompatActivity {

    private Button backButton,frontButton;
    public float x = 440f;

    private EditText companyName, policy, greenCard, agency, name, address, country, email;
    private SwitchCompat doesCover;


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

        doesCover= findViewById(R.id.switch5);


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

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, A_FinalUI_3.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_FinalUI_5.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data1.put("FP_Insurance_Name", companyName.getText().toString());
        data1.put("FP_Insurance_Policy", policy.getText().toString());
        data1.put("FP_Insurance_Green_Card", greenCard.getText().toString());
        data1.put("FP_Insurance_Agency", agency.getText().toString());
        data1.put("FP_Insurance_Name2", name.getText().toString());
        data1.put("FP_Insurance_Address", address.getText().toString());
        data1.put("FP_Insurance_Country", country.getText().toString());
        data1.put("FP_Insurance_Email", email.getText().toString());

        if (doesCover.isChecked()){
            data1.put("FP_DoesCover", "true");
        }
        else{
            data1.put("FP_DoesCover", "false");
        }

        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}
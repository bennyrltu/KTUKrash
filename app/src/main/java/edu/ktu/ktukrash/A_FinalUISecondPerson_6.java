package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class A_FinalUISecondPerson_6 extends AppCompatActivity {

    private Button backButton,frontButton;
    private EditText damage, remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_uisecond_person6);
        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        damage = findViewById(R.id.TextInput);
        remarks = findViewById(R.id.TextInputa);

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
        Intent intent = new Intent(this, A_Circumstances_2.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, reviewDeclaration.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data1.put("FP_Remarks_damage", damage.getText().toString());
        data1.put("FP_Remarks", remarks.getText().toString());
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}
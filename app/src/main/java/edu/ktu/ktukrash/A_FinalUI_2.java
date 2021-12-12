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
import android.widget.Switch;

import java.util.HashMap;

public class A_FinalUI_2 extends AppCompatActivity {

    private Button backButton,frontButton;
    private SwitchCompat injuries, A_B, vehicles;
    private EditText witness;
    public float x = 330f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui2);

        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        injuries = findViewById(R.id.switch1);
        A_B = findViewById(R.id.switch2);
        vehicles = findViewById(R.id.switch3);
        witness = findViewById(R.id.TextInput);


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
        Intent intent = new Intent(this, A_FinalUI_1.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_FinalUI_3.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        if (injuries.isChecked()){
            data1.put("FP_Injuries", "true");
        }
        else{
            data1.put("FP_Injuries", "false");
        }

        if (A_B.isChecked()){
            data1.put("FP_other_than_A_B", "true");
        }
        else{
            data1.put("FP_other_than_A_B", "false");
        }

        if (vehicles.isChecked()){
            data1.put("FP_other_than_A_B_vehicles", "true");
        }
        else{
            data1.put("FP_other_than_A_B_vehicles", "false");
        }

        data1.put("FP_Witness", witness.getText().toString());
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}
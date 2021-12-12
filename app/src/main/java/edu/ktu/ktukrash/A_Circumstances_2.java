package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.HashMap;

public class A_Circumstances_2 extends AppCompatActivity {

    private Button backButton,frontButton;

    public int counter=0;
    public float x = 935f;
    private SwitchCompat switch6, switch11, switch13, switch14, switch15, switch16, switch17, switch18, switch19, switch7, switch8, switch9, switch10, switch20, switch21, switch22;
    private SwitchCompat switch12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acircumstances2);
        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        switch6= findViewById(R.id.switch6);
        switch11= findViewById(R.id.switch11);
        switch12= findViewById(R.id.switch12);
        switch13= findViewById(R.id.switch13);
        switch14= findViewById(R.id.switch14);
        switch15= findViewById(R.id.switch15);
        switch16= findViewById(R.id.switch16);
        switch17= findViewById(R.id.switch17);
        switch18= findViewById(R.id.switch18);
        switch19= findViewById(R.id.switch19);
        switch7= findViewById(R.id.switch7);
        switch8= findViewById(R.id.switch8);
        switch9= findViewById(R.id.switch9);
        switch10= findViewById(R.id.switch10);
        switch20= findViewById(R.id.switch20);
        switch21= findViewById(R.id.switch21);
        switch22= findViewById(R.id.switch22);


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
        Intent intent = new Intent(this, A_FinalUISecondPerson_5.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        if (switch6.isChecked()){
            data2.put("SP_1", "true");
            counter++;
        }
        else{
            data2.put("SP_1", "false");
        }

        if (switch11.isChecked()){
            data2.put("SP_2", "true");
            counter++;
        }
        else{
            data2.put("SP_2", "false");
        }

        if (switch12.isChecked()){
            data2.put("SP_3", "true");
            counter++;
        }
        else{
            data2.put("SP_3", "false");
        }

        if (switch13.isChecked()){
            data2.put("SP_4", "true");
            counter++;
        }
        else{
            data2.put("SP_4", "false");
        }

        if (switch14.isChecked()){
            data2.put("SP_5", "true");
            counter++;
        }
        else{
            data2.put("SP_5", "false");
        }

        if (switch15.isChecked()){
            data2.put("SP_6", "true");
            counter++;
        }
        else{
            data2.put("SP_6", "false");
        }

        if (switch16.isChecked()){
            data2.put("SP_7", "true");
            counter++;
        }
        else{
            data2.put("SP_7", "false");
        }

        if (switch17.isChecked()){
            data2.put("SP_8", "true");
            counter++;
        }
        else{
            data2.put("SP_8", "false");
        }

        if (switch18.isChecked()){
            data2.put("SP_9", "true");
            counter++;
        }
        else{
            data2.put("SP_9", "false");
        }

        if (switch19.isChecked()){
            data2.put("SP_10", "true");
            counter++;
        }
        else{
            data2.put("SP_10", "false");
        }

        if (switch7.isChecked()){
            data2.put("SP_11", "true");
            counter++;
        }
        else{
            data2.put("SP_11", "false");
        }

        if (switch8.isChecked()){
            data2.put("SP_12", "true");
            counter++;
        }
        else{
            data2.put("SP_12", "false");
        }

        if (switch9.isChecked()){
            data2.put("SP_13", "true");
            counter++;
        }
        else{
            data2.put("SP_13", "false");
        }

        if (switch10.isChecked()){
            data2.put("SP_14", "true");
            counter++;
        }
        else{
            data2.put("SP_14", "false");
        }

        if (switch20.isChecked()){
            data2.put("SP_15", "true");
            counter++;
        }
        else{
            data2.put("SP_15", "false");
        }

        if (switch21.isChecked()){
            data2.put("SP_16", "true");
            counter++;
        }
        else{
            data2.put("SP_16", "false");
        }

        if (switch22.isChecked()){
            data2.put("SP_17", "true");
            counter++;
        }
        else{
            data2.put("SP_17", "false");
        }

        String countas = String.valueOf(counter);

        data2.put("SP_CircumstancesC",countas);

        Intent intent = new Intent(this, A_FinalUISecondPerson_6.class);
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}
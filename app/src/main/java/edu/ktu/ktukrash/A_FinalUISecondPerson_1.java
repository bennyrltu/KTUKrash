package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class A_FinalUISecondPerson_1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Button button;


    //Location stuff
    private Button map;
    private TextView currentTime;
    private Intent intent;
    private EditText locationText;
    public float x = 660f;

    private TextView date;
    //private TextView currentTime;

    private Button backButton,frontButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_uisecond_person1);
        initializeViews();
        Button button = (Button) findViewById(R.id.OpenDatePicker);
        currentTime = (TextView) findViewById(R.id.editTextTextPersonName);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String currentDateAndTime = df.format(new Date());

        currentTime.setText(currentDateAndTime);

        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        date = (TextView) findViewById(R.id.DisplayDate);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

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


        //Location stuff--------------------------------------------------------------------
        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMap();
            }
        });
        locationText = (EditText) findViewById(R.id.LocationOfEvent);
        //---------------------------------------------------

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (5) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newText = data.getStringExtra("loc");
                    locationText.setText(newText);
                }
                break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setText(currentDateString);
    }

    private void startMap(){
        intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 5);
    }

    private void initializeViews(){
        button = (Button) findViewById(R.id.OpenDatePicker);
    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, A_FinalUI_6.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_FinalUISecondPerson_2.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data2.put("SP_Date", date.getText().toString());
        data2.put("SP_Time", currentTime.getText().toString());
        data2.put("SP_Location", locationText.getText().toString());
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);

        startActivity(intent);
    }
}
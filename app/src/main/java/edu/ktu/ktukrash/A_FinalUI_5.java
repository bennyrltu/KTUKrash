package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.animation.ObjectAnimator;
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

import com.hbb20.CountryCodePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class A_FinalUI_5 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button backButton,frontButton,button;
    private EditText license_no, categories;
    private TextView license_valid_until;
    public float x = 495f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui5);

        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        license_no = findViewById(R.id.editTextTextPersonName16);
        categories = findViewById(R.id.editTextTextPersonName2);
        license_valid_until = findViewById(R.id.textView16);

        Button button = (Button) findViewById(R.id.OpenDatePicker);

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, A_FinalUI_4.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_Circumstances_1.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data1.put("FP_Driving_license_no", license_no.getText().toString());
        data1.put("FP_Driving_ategories", categories.getText().toString());
        data1.put("FP_License_valid_until", license_valid_until.getText().toString());
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textView16);
        textView.setText(currentDateString);

    }
}
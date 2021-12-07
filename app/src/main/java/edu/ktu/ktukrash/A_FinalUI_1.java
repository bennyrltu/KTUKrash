package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class A_FinalUI_1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button button;

    //Location stuff
    private Button map;
    private TextView currentTime;
    private Intent intent;
    private EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui1);

        initializeViews();
        Button button = (Button) findViewById(R.id.OpenDatePicker);
        TextView currentTime = (TextView) findViewById(R.id.editTextTextPersonName);

        DateFormat df = new SimpleDateFormat("KK:mm:ss", Locale.getDefault());
        String currentDateAndTime = df.format(new Date());

        currentTime.setText(currentDateAndTime);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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
        locationText = (EditText) findViewById(R.id.addressField);
        //---------------------------------------------------


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.DisplayDate);
        textView.setText(currentDateString);



    }

    private void startMap(){
        intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 5);
    }

    private void initializeViews(){
        button = (Button) findViewById(R.id.OpenDatePicker);
    }
}
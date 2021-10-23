package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.hbb20.CountryCodePicker;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class FirstPersonData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private CountryCodePicker ccp;
    private TextView phoneTextView;
    private Button button;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_person_data);

        Intent intent = getIntent();
        String text = intent.getStringExtra(DeclarationCars.EXTRA_TEXT);

        TextView textView1 = (TextView) findViewById(R.id.firstPersonNumber);

        //EditText editText2 = (EditText) findViewById(R.id.addressField);

        textView1.setText(text);

        initializeViews();
        listeners();

        Button button = (Button) findViewById(R.id.OpenDatePicker);

        Places.initialize(getApplicationContext(), "AIzaSyDxk5WKLun-UPLVYU2MAPbLgllJkcnCO0A");
        Places.createClient(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    private void initializeViews(){
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneTextView = (EditText) findViewById(R.id.FirstPersonPhoneNumber);
        button = (Button) findViewById(R.id.button2);
    }

    private void listeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Variable
                String code = ccp.getSelectedCountryCode();
                String country = ccp.getSelectedCountryEnglishName();
                String number = phoneTextView.getText().toString();

                // Create Toast
                Context context = getApplicationContext();
                CharSequence text = "Country - " + country + ", Value Sent: " + code + " " + number;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
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
}
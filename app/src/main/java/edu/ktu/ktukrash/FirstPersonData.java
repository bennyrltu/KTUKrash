package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FirstPersonData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private CountryCodePicker ccp;
    private TextView phoneTextView;
    private Button button, button2,button3;
    //Location stuff
    private Button map;
    private Intent intent;
    private EditText locationText;


    public float x = 250f;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DatabaseReference root = db.getReference()
            .child("Declaration_Data")
            .child(currentuser)
            .child("Declarations");
    //--------------------------------

    public static final String EXTRA_TEXT3 = "ktu.edu.KTUKrash.EXTRA.TEXT3";
    public static final String EXTRA_TEXT4 = "ktu.edu.KTUKrash.EXTRA.TEXT4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_person_data);

        Intent intent = getIntent();
        String text = intent.getStringExtra(DeclarationCars.EXTRA_TEXT);

        String text2 = intent.getStringExtra(DeclarationCars.EXTRA_TEXT2);

        TextView textView1 = (TextView) findViewById(R.id.firstPersonNumber);
        TextView textView2 = (TextView) findViewById(R.id.textView7);


        textView1.setText(text);
        textView2.setText(text2);

        initializeViews();
        Button button = (Button) findViewById(R.id.OpenDatePicker);
        Button button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.backButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNewActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
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
        locationText = (EditText) findViewById(R.id.addressField);
        //---------------------------------------------------


        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, DeclarationCars.class);
        startActivity(intent);
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

    private void startMap(){
        intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 5);
    }

    private void initializeViews(){
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneTextView = (EditText) findViewById(R.id.FirstPersonPhoneNumber);
        button = (Button) findViewById(R.id.OpenDatePicker);
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

    private void OpenNewActivity() {

        Intent intent = new Intent(this, SecondPersonData.class);// <-- OLD

        //Intent intent = new Intent(this, A_FinalUI_1.class);

        //Validation--------------------------------------------------------------
        EditText name = (EditText) findViewById(R.id.FirstPersonNameField);
        EditText lastName = (EditText) findViewById(R.id.firstPersonLastName);

        EditText phone = (EditText) findViewById(R.id.FirstPersonPhoneNumber);
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText personalCode = (EditText) findViewById(R.id.editTextNumber);

        TextView textView1 = (TextView) findViewById(R.id.firstPersonNumber);
        TextView textView2 = (TextView) findViewById(R.id.textView7);
        TextView textView3 = (TextView) findViewById(R.id.DisplayDate);

        Button button = (Button) findViewById(R.id.OpenDatePicker);


        if(name.getText().toString().isEmpty()){
            name.setError("This field is required!");
            name.requestFocus();
            return;
        }

        if(!name.getText().toString().matches("^[a-zA-Z ]+$")){
            name.setError("Name cannot contain special characters or numbers!");
            name.requestFocus();
            return;
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("This field is required!");
            lastName.requestFocus();
            return;
        }

        if(!lastName.getText().toString().matches("^[a-zA-Z ]+$")){
            lastName.setError("Last name cannot contain special characters or numbers!");
            lastName.requestFocus();
            return;
        }

        if(phone.getText().toString().isEmpty()){
            phone.setError("This field is required!");
            phone.requestFocus();
            return;
        }
        if(phone.getText().toString().matches(".*\\D.*")){
            phone.setError("Phone number only contains numbers!");
            phone.requestFocus();
            return;
        }
        if(locationText.getText().toString().isEmpty()){
            locationText.setError("This field is required");
            locationText.requestFocus();
            return;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("This field is required");
            email.requestFocus();
            return;
        }
        if(!email.getText().toString().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
            email.setError("Invalid email entered!");
            email.requestFocus();
            return;
        }
        if(personalCode.getText().toString().isEmpty()){
            personalCode.setError("This field is required");
            personalCode.requestFocus();
            return;
        }

        if(personalCode.getText().toString().matches(".*\\D.*")){
            phone.setError("Personal code only contains numbers!");
            phone.requestFocus();
            return;
        }

        if(textView3.getText().toString().isEmpty()){
            button.setError("Birth date is required");
            button.requestFocus();
            return;
        }

        //------------------------------------------------------------
        String dbName = name.getText().toString().trim();
        String dbLastname = lastName.getText().toString().trim();
        String dbLocation = locationText.getText().toString().trim();
        String dbEmail = email.getText().toString().trim();
        String dbCode= personalCode.getText().toString().trim();
        String code = ccp.getSelectedCountryCode().trim();
        String number = code + phoneTextView.getText().toString().trim();
        String dbNumber = textView1.getText().toString().trim();
        String dbBirthdate = textView3.getText().toString().trim();
        String secondCar = textView2.getText().toString().trim();
        String newDeklaracija = dbNumber + "_" + secondCar;

        HashMap<String, String> dataMap = new HashMap<>();

        dataMap.put("FP_Name", dbName);
        dataMap.put("FP_LastName", dbLastname);
        dataMap.put("FP_Birthdate", dbBirthdate);
        dataMap.put("FP_PhoneNumber", number);
        dataMap.put("FP_Location", dbLocation);
        dataMap.put("FP_Email", dbEmail);
        dataMap.put("FP_PersonalCode", dbCode);
        dataMap.put("FP_CarNumber", dbNumber);

        //root.push().setValue(dataMap);
        root.child(newDeklaracija).setValue(dataMap);



        //------------------------------------------------------------
        TextView editText2 = (TextView) findViewById(R.id.textView7);
        String text2 = editText2.getText().toString().trim();
        intent.putExtra(EXTRA_TEXT3, text2);

        TextView editText3 = (TextView) findViewById(R.id.firstPersonNumber);
        String text3 = editText3.getText().toString().trim();
        intent.putExtra(EXTRA_TEXT4, text3);

        //For PDF
        intent.putExtra("pdfData1", dataMap);
        //

        startActivity(intent);

    }
}
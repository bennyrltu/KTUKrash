package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FirstPersonData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private CountryCodePicker ccp;
    private TextView phoneTextView;
    private Button button;

    //Location stuff
    private Button locationButton;
    private EditText locationText;
    FusedLocationProviderClient fusedLocationProviderClient;
    //--------------------------------

    public static final String EXTRA_TEXT3 = "ktu.edu.KTUKrash.EXTRA.TEXT3";

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

        //Location stuff
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationButton = (Button) findViewById(R.id.buttonLocation);
        locationText = (EditText) findViewById(R.id.addressField);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(FirstPersonData.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null){
                                Geocoder geocoder = new Geocoder(FirstPersonData.this,
                                        Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1
                                    );
                                    locationText.setText(addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                else{
                    ActivityCompat.requestPermissions(FirstPersonData.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        //---------------------------------------------------


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
                //Context context = getApplicationContext();
                //CharSequence text = "Country - " + country + ", Value Sent: " + code + " " + number;
                //int duration = Toast.LENGTH_SHORT;
                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();

                OpenNewActivity();

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

    private void OpenNewActivity() {

        Intent intent = new Intent(this, SecondPersonData.class);

        //Validation--------------------------------------------------------------
        EditText name = (EditText) findViewById(R.id.FirstPersonNameField);
        EditText lastName = (EditText) findViewById(R.id.firstPersonLastName);

        EditText phone = (EditText) findViewById(R.id.FirstPersonPhoneNumber);
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText personalCode = (EditText) findViewById(R.id.editTextNumber);

        if(name.getText().toString().isEmpty()){
            name.setError("This field is required");
            name.requestFocus();
            return;
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("This field is required");
            lastName.requestFocus();
            return;
        }
        if(phone.getText().toString().isEmpty()){
            phone.setError("This field is required");
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
        if(personalCode.getText().toString().isEmpty()){
            personalCode.setError("This field is required");
            personalCode.requestFocus();
            return;
        }
        //------------------------------------------------------------

        TextView editText2 = (TextView) findViewById(R.id.textView7);
        String text2 = editText2.getText().toString().trim();
        intent.putExtra(EXTRA_TEXT3, text2);
        startActivity(intent);
    }
}
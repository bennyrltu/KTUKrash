package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.DatePickerDialog;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SecondPersonData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private CountryCodePicker ccp;
    private TextView phoneTextView;
    private Button button;

    //Location stuff
    private Button locationButton;
    private EditText locationText;
    FusedLocationProviderClient fusedLocationProviderClient;
    //--------------------------------

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DatabaseReference root = db.getReference()
            .child("Declaration_Data")
            .child(currentuser)
            .child("Declarations");


    public static final String EXTRA_TEXT5 = "ktu.edu.KTUKrash.EXTRA.TEXT5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_person_data);

        Intent intent = getIntent();
        String text2 = intent.getStringExtra(FirstPersonData.EXTRA_TEXT3);
        String text3 = intent.getStringExtra(FirstPersonData.EXTRA_TEXT4);

        TextView textView1 = (TextView) findViewById(R.id.SecondPersonNumber);
        TextView textView2 = (TextView) findViewById(R.id.textView10);


        textView1.setText(text2);
        textView2.setText(text3);

        initializeViews();
        listeners();

        Button button = (Button) findViewById(R.id.OpenDatePicker);


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
                if(ActivityCompat.checkSelfPermission(SecondPersonData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null){
                                Geocoder geocoder = new Geocoder(SecondPersonData.this,
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
                    ActivityCompat.requestPermissions(SecondPersonData.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        //---------------------------------------------------

    }

    private void initializeViews(){
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneTextView = (EditText) findViewById(R.id.SecondPersonPhoneNumber);
        button = (Button) findViewById(R.id.button2);
    }

    private void listeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Variable
                String code = ccp.getSelectedCountryCode();
                String country = ccp.getSelectedCountryEnglishName();
                String number = code + phoneTextView.getText().toString();

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

        Intent intent = new Intent(this, EventPictures.class);

        //Validation--------------------------------------------------------------
        EditText name = (EditText) findViewById(R.id.SecondPersonNameField);
        EditText lastName = (EditText) findViewById(R.id.SecondPersonLastName);

        EditText phone =(EditText) findViewById(R.id.SecondPersonPhoneNumber);
        EditText email = (EditText) findViewById(R.id.SecondPersonEmail);
        EditText personalCode = (EditText) findViewById(R.id.SecondPersonCode);

        TextView textView1 = (TextView) findViewById(R.id.SecondPersonNumber);
        TextView textView2 = (TextView) findViewById(R.id.textView10);
        TextView textView3 = (TextView) findViewById(R.id.DisplayDate);


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

        if(textView3.getText().toString().isEmpty()){
            textView3.setError("This field is required");
            textView3.requestFocus();
            return;
        }

        //------------------------------------------------------------
        String dbName = name.getText().toString().trim();
        String dbLastname = lastName.getText().toString().trim();
        String dbPhone = phone.getText().toString().trim();
        String dbLocation = locationText.getText().toString().trim();
        String dbEmail = email.getText().toString().trim();
        String dbCode= personalCode.getText().toString().trim();
        String code = ccp.getSelectedCountryCode().trim();
        String number = code + phoneTextView.getText().toString().trim();
        String carnumber2 = textView1.getText().toString().trim();
        String carnumber3 = textView2.getText().toString().trim();
        String temp= carnumber3 +"_" +carnumber2;
        String dbBirthdate = textView3.getText().toString().trim();


        HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put("SP_Name", dbName);
        dataMap.put("SP_LastName", dbLastname);
        dataMap.put("SP_Birthdate", dbBirthdate);
        dataMap.put("SP_PhoneNumber", number);
        dataMap.put("SP_Location", dbLocation);
        dataMap.put("SP_Email", dbEmail);
        dataMap.put("SP_PersonalCode", dbCode);
        dataMap.put("SP_CarNumber", carnumber2);

        //root.push().setValue(dataMap);
        root.child(temp).updateChildren(dataMap);
        //root.child(temp).setValue(dataMap);


        intent.putExtra(EXTRA_TEXT5, temp);
        startActivity(intent);
    }
}
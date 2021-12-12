package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class A_FinalUI_3 extends AppCompatActivity{
    private CompoundButton bmark;
    private TextView textview1,textview2;
    private EditText editText,editText2;
    private EditText countryOfRegistration;
    private Button backButton,frontButton;
    public float x = 385f;

    private EditText makerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui3);

        bmark = (CompoundButton)findViewById(R.id.switch4);


        textview1 = findViewById(R.id.registeredCountryHID);
        textview2 = findViewById(R.id.registeredCountry3HID);
        editText=findViewById(R.id.TrailerRegistrationHID);
        editText2=findViewById(R.id.countryOfRegistration2HID);
        countryOfRegistration=findViewById(R.id.CountryOfRegistration);

        textview1.setVisibility(View.GONE);
        textview2.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        editText2.setVisibility(View.GONE);

        backButton = findViewById(R.id.backButton);
        frontButton = findViewById(R.id.frontButton);

        makerModel = findViewById(R.id.MakerAndModelEditText);

        bmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // checked
                    textview1.setVisibility(View.VISIBLE);
                    textview2.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    editText2.setVisibility(View.VISIBLE);
                } else {
                    // not checked
                    textview1.setVisibility(View.GONE);
                    textview2.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    editText2.setVisibility(View.GONE);
                }
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

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, A_FinalUI_2.class);
        startActivity(intent);
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, A_FinalUI_4.class);
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data1.put("FP_Vehicle_Maker_model", makerModel.getText().toString());
        data1.put("FP_Vehicle_Country", countryOfRegistration.getText().toString());
        data1.put("FP_Trailer_Registration", editText.getText().toString());
        data1.put("FP_Trailer_Country", editText2.getText().toString());
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}
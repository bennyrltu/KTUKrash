package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class A_FinalUI_3 extends AppCompatActivity {
    private Switch bmark;
    private TextView textview1,textview2;
    private EditText editText,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal_ui3);

        bmark = (Switch)findViewById(R.id.switch4);


        textview1 = findViewById(R.id.registeredCountryHID);
        textview2 = findViewById(R.id.registeredCountry3HID);
        editText=findViewById(R.id.TrailerRegistrationHID);
        editText2=findViewById(R.id.countryOfRegistration2HID);

        textview1.setVisibility(View.GONE);
        textview2.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        editText2.setVisibility(View.GONE);


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
    }
}
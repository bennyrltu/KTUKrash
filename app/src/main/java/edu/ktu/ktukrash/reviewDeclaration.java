package edu.ktu.ktukrash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ObjectInputStream;
import java.util.HashMap;

public class reviewDeclaration extends AppCompatActivity {
    HashMap<String, String> data1;
    HashMap<String, Object> data2;
    Button toPDF;
    Button toDashboard;
    TextView fpNameLastName;
    TextView fprevModel;
    TextView fprevNumber;
    TextView fprevDraudimas;
    TextView fprevAge;
    TextView fprevPhone;
    TextView fprevEmail;
    TextView fprevDescription;
    TextView fpSugadinimai;
    TextView FPrevdamage;
    TextView SPrevdamage;
    TextView spNameLastName;
    TextView sprevModel;
    TextView sprevNumber;
    TextView sprevDraudimas;
    TextView sprevAge;
    TextView sprevPhone;
    TextView sprevEmail;
    TextView sprevDescription;
    TextView spSugadinimai;
    TextView revData;
    TextView revVieta;
    TextView accidentInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration_review);

        Bundle bundle = getIntent().getExtras();
        data1 = (HashMap<String, String>) bundle.get("pdfData1");
        data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        toPDF = (Button) findViewById(R.id.nextActivityButton);
        toDashboard = (Button) findViewById(R.id.backToHomeFromReview);

        toPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });

        toDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity2();
            }
        });
        fpNameLastName = findViewById(R.id.fprevNameLastName);
        fprevModel = findViewById(R.id.revModel);
        fprevNumber = findViewById(R.id.revNumber);
        fprevDraudimas = findViewById(R.id.fprevDraudimas);
        fprevAge = findViewById(R.id.fprevAge);
        fprevPhone = findViewById(R.id.fprevPhone);
        fprevEmail = findViewById(R.id.fprevEmail);
        FPrevdamage = findViewById(R.id.FPrevdamage);


        fprevDescription = findViewById(R.id.FPrevDescription);
        fpSugadinimai = findViewById(R.id.revSugadinimai);

        revData = findViewById(R.id.revDateTime);
        revVieta = findViewById(R.id.revLocation);


        accidentInfo = findViewById(R.id.revZala);
        accidentInfo.setText("");

        if(data1.get("FP_Injuries").equals("true") || data2.get("SP_Injuries").equals("true")){
            accidentInfo.setText((accidentInfo.getText() + "There are injured people. ").toString());
        }
        else{
            accidentInfo.setText((accidentInfo.getText() + "There are no injured people. ").toString());
        }

        if((data1.get("FP_other_than_A_B_vehicles").equals("true") || data2.get("SP_other_than_A_B_vehicles").equals("true")) || (data1.get("FP_other_than_A_B").equals("true") || data2.get("SP_other_than_A_B").equals("true"))){

            accidentInfo.setText((accidentInfo.getText() + "The property of third parties (e.g. structures, road installations, etc.) has been damaged.").toString());

        }
        else{
            accidentInfo.setText((accidentInfo.getText() + "The property of third parties (e.g. structures, road installations, etc.) has not been damaged.").toString());
        }

        spNameLastName = findViewById(R.id.sprevNameLastName);
        sprevModel = findViewById(R.id.sprevModel);
        sprevNumber = findViewById(R.id.sprevNumber);
        sprevDraudimas = findViewById(R.id.sprevDraudimas);
        sprevAge = findViewById(R.id.sprevAge);
        sprevPhone = findViewById(R.id.sprevPhone);
        sprevEmail = findViewById(R.id.sprevEmail);

        sprevDescription = findViewById(R.id.sprevDescription);
        spSugadinimai = findViewById(R.id.sprevSugadinimai);

        revData.setText(data1.get("FP_Date") + " " + data1.get("FP_Time"));
        revVieta.setText(data1.get("FP_Accident_Location"));


        fprevModel.setText(data1.get("FP_Vehicle_Maker_model"));
        fprevNumber.setText(data1.get("FP_CarNumber"));
        fprevDraudimas.setText(data1.get("FP_Insurance_Name"));
        fpNameLastName.setText(data1.get("FP_Name") + " " + data1.get("FP_LastName"));
        fprevAge.setText(data1.get("FP_Birthdate"));
        fprevPhone.setText(data1.get("FP_PhoneNumber"));
        fprevEmail.setText(data1.get("FP_Email"));
        fprevDescription.setText("");

        if(data1.get("FP_1").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Parked or stopped\n");
        }
        if(data1.get("FP_2").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Leaving a parking place\n");
        }
        if(data1.get("FP_3").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Entering a parking place\n");
        }
        if(data1.get("FP_4").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Emerging from a place\n");
        }
        if(data1.get("FP_5").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Entering a place\n");
        }
        if(data1.get("FP_6").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Entering a roundabout\n");
        }
        if(data1.get("FP_7").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Circulating a roundabout\n");
        }
        if(data1.get("FP_8").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Striking the rear in the same direction\n");
        }
        if(data1.get("FP_9").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Same direction different lane\n");
        }
        if(data1.get("FP_10").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Changing lanes\n");
        }
        if(data1.get("FP_11").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Overtaking\n");
        }
        if(data1.get("FP_12").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Turning to the right\n");
        }
        if(data1.get("FP_13").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Turning to the left\n");
        }
        if(data1.get("FP_14").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Reversing\n");
        }
        if(data1.get("FP_15").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Encroaching on a lane\n");
        }
        if(data1.get("FP_16").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "coming from the right\n");
        }
        if(data1.get("FP_17").equals("true")){
            fprevDescription.setText(fprevDescription.getText() + "Had not observed a sign\n");
        }

        // fields.get("A_sugadinimai").setValue(data1.get("FP_Remarks_damage")).setFontSize(10).setBorderWidth(0f);
        FPrevdamage.setText(data1.get("FP_Remarks_damage"));

        SPrevdamage = findViewById(R.id.SPrevdamage);
        sprevModel.setText(data2.get("SP_Vehicle_Maker_model").toString());
        sprevNumber.setText(data2.get("SP_CarNumber").toString());
        sprevDraudimas.setText(data2.get("SP_Insurance_Name").toString());
        spNameLastName.setText(data2.get("SP_Name").toString() + " " + data2.get("SP_LastName").toString());
        sprevAge.setText(data2.get("SP_Birthdate").toString());
        sprevPhone.setText(data2.get("SP_PhoneNumber").toString());
        sprevEmail.setText(data2.get("SP_Email").toString());
        sprevDescription.setText("");

        if(data2.get("SP_1").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Parked or stopped\n");
        }
        if(data2.get("SP_2").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Leaving a parking place\n");
        }
        if(data2.get("SP_3").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Entering a parking place\n");
        }
        if(data2.get("SP_4").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Emerging from a place\n");
        }
        if(data2.get("SP_5").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Entering a place\n");
        }
        if(data2.get("SP_6").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Entering a roundabout\n");
        }
        if(data2.get("SP_7").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Circulating a roundabout\n");
        }
        if(data2.get("SP_8").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Striking the rear in the same direction\n");
        }
        if(data2.get("SP_9").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Same direction different lane\n");
        }
        if(data2.get("SP_10").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Changing lanes\n");
        }
        if(data2.get("SP_11").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Overtaking\n");
        }
        if(data2.get("SP_12").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Turning to the right\n");
        }
        if(data2.get("SP_13").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Turning to the left\n");
        }
        if(data2.get("SP_14").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Reversing\n");
        }
        if(data2.get("SP_15").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Encroaching on a lane\n");
        }
        if(data2.get("SP_16").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "coming from the right\n");
        }
        if(data2.get("SP_17").toString().equals("true")){
            sprevDescription.setText(sprevDescription.getText().toString() + "Had not observed a sign\n");
        }

        SPrevdamage.setText(data2.get("SP_Remarks_damage").toString());


    }

    public void OpenActivity(){
        Intent intent = new Intent(this, activity_pdf.class);
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }

    public void OpenActivity2(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}

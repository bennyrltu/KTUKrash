package edu.ktu.ktukrash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class reviewDeclaration extends AppCompatActivity {
    HashMap<String, String> data1;
    HashMap<String, Object> data2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration_review);

        Bundle bundle = getIntent().getExtras();
        data1 = (HashMap<String, String>) bundle.get("pdfData1");
        data2 = (HashMap<String, Object>) bundle.get("pdfData2");
    }
}

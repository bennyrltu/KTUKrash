package edu.ktu.ktukrash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class reviewDeclaration extends AppCompatActivity {
    HashMap<String, String> data1;
    HashMap<String, Object> data2;
    Button toPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration_review);

        Bundle bundle = getIntent().getExtras();
        data1 = (HashMap<String, String>) bundle.get("pdfData1");
        data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        toPDF = (Button) findViewById(R.id.nextActivityButton);

        toPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });
    }

    public void OpenActivity(){
        Intent intent = new Intent(this, activity_pdf.class);
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);
        startActivity(intent);
    }
}

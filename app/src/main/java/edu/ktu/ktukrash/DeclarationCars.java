package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeclarationCars extends AppCompatActivity {

    public static final String EXTRA_TEXT = "ktu.edu.KTUKrash.EXTRA.TEXT";
    public static final String EXTRA_TEXT2 = "ktu.edu.KTUKrash.EXTRA.TEXT2";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_cars);

        Button button = (Button) findViewById(R.id.continueButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenNewActivity();
            }
        });
    }

    private void OpenNewActivity() {

        EditText editText1 = (EditText) findViewById(R.id.firstPersonCar);
        String text = editText1.getText().toString().trim();

        EditText editText2 = (EditText) findViewById(R.id.secondPersonCar);
        String text2 = editText2.getText().toString().trim();

        if(text.isEmpty()){
            editText1.setError("This field is required");
            editText1.requestFocus();
            return;
        }
        if(text2.isEmpty()){
            editText2.setError("This field is required");
            editText2.requestFocus();
            return;
        }

        if(text.length() != 6){
            editText1.setError("Invalid car number");
            editText1.requestFocus();
            return;
        }
        if(text2.length() != 6){
            editText2.setError("Invalid car number");
            editText2.requestFocus();
            return;
        }

        Intent intent = new Intent(this, FirstPersonData.class);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_TEXT2, text2);
        startActivity(intent);
    }
}
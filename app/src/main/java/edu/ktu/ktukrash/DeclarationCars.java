package edu.ktu.ktukrash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class DeclarationCars extends AppCompatActivity {

    public static final String EXTRA_TEXT = "ktu.edu.KTUKrash.EXTRA.TEXT";
    public static final String EXTRA_TEXT2 = "ktu.edu.KTUKrash.EXTRA.TEXT2";
    private Button button, button1;

    public float x = 55f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_cars);


        Button button = (Button) findViewById(R.id.continueButton);
        button1 = (Button) findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenNewActivity();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenNewActivity1();
            }
        });


        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

    }

    private void OpenNewActivity1() {
        Intent intent = new Intent(this, DeclarationStart.class);
        startActivity(intent);
    }

    private void OpenNewActivity() {

        EditText editText1 = (EditText) findViewById(R.id.firstPersonCar);
        String text = editText1.getText().toString().trim();

        EditText editText2 = (EditText) findViewById(R.id.secondPersonCar);
        String text2 = editText2.getText().toString().trim();

        if(text.isEmpty()){
            editText1.setError("This field is required!");
            editText1.requestFocus();
            return;
        }
        if(text2.isEmpty()){
            editText2.setError("This field is required!");
            editText2.requestFocus();
            return;
        }

        if(text.length() != 6){
            editText1.setError("Invalid first car number!");
            editText1.requestFocus();
            return;
        }

        if(!text.toString().matches("^[A-Z]{3}[0-9]{3}$")){
            editText1.setError("Invalid first car number!");
            editText1.requestFocus();
            return;
        }
        if(!text2.toString().matches("^[A-Z]{3}[0-9]{3}$")){
            editText2.setError("Invalid second car number!");
            editText2.requestFocus();
            return;
        }

        Intent intent = new Intent(this, FirstPersonData.class);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_TEXT2, text2);
        startActivity(intent);
    }
}
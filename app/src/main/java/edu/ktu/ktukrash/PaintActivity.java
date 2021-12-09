package edu.ktu.ktukrash;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PaintActivity extends AppCompatActivity {

    int defaultColor;
    SignatureView signatureView;
    ImageButton imgEraser, imgColor, imgSave;
    SeekBar seekBar;
    TextView txtPenSize, textView;
    private static String fileName;
    Button button1, continueButton;
    ImageView imageView;
    Uri fileUri;

    Button revButton;



    private ProgressDialog mProgressDialog;
    private StorageReference mStorageRef;
    private FirebaseAuth auth;




    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference root = db.getReference()
            .child("Declaration_Data")
            .child(currentuser)
            .child("Declarations");


    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pictures");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_activity);

        signatureView = findViewById(R.id.signature_view);
        seekBar = findViewById(R.id.penSize);
        txtPenSize = findViewById(R.id.txtPenSize);
        imgColor = findViewById(R.id.btnColor);
        imgEraser = findViewById(R.id.btnEraser);
        imgSave = findViewById(R.id.btnSave);
        mProgressDialog = new ProgressDialog(PaintActivity.this);
        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        button1 = findViewById(R.id.addImage);
        imageView = findViewById(R.id.photoBackground);
        textView = findViewById(R.id.textView13);

        revButton = findViewById(R.id.paintRevButton);
        revButton.setVisibility(View.GONE);

        continueButton=findViewById(R.id.button4);

        Intent intent = getIntent();
        String text = intent.getStringExtra(EventPictures.EXTRA_TEXT6);

        TextView textView1 = (TextView) findViewById(R.id.textView13);

        textView1.setText(text);

        String stringas = textView1.getText().toString().trim();

        askPermission();
        signatureView.setBackground(Drawable.createFromPath("background.jpg"));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());
        fileName = date;

        if(!path.exists())
        {
            path.mkdirs();
        }


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity2();
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker
                        .Companion
                        .with(PaintActivity.this)
                        .start(1);


            }
        });

        revButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenActivity();
            }
        });


        defaultColor = ContextCompat.getColor(PaintActivity.this, R.color.black);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtPenSize.setText(i + "dp");
                signatureView.setPenSize(i);
                seekBar.setMax(50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        imgEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signatureView.clearCanvas();
            }
        });
        
        imgSave.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
            if(!signatureView.isBitmapEmpty())
            {
                try {
                    saveImage();
                    Toast.makeText(PaintActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(PaintActivity.this,"Couldn't save drawing", Toast.LENGTH_SHORT).show();
                }
            }
            revButton.setVisibility(View.VISIBLE);
            }
        }) ;

    }


    public static Bitmap mergeToPin(Bitmap back, Bitmap front) {
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(result);
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);
        return result;
    }


    private void saveImage() throws IOException {
        File file = new File(fileName);
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        Intent intent = getIntent();
        TextView textView1 = (TextView) findViewById(R.id.textView13);
        String text = intent.getStringExtra(EventPictures.EXTRA_TEXT6);
        textView1.setText(text);
        String stringas = textView1.getText().toString().trim();

        Bitmap bitmap = signatureView.getSignatureBitmap();
        //Bitmap bitmap1 = imageView.getDrawingCache();
        //Bitmap bitmap2 = mergeToPin(bitmap, bitmap1);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapData = bos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://krashapp-d2bad.appspot.com/ReportPaintings/" + userID);
        StorageReference imagesRef = storageRef.child(fileName + ".png");
        UploadTask uploadTask = imagesRef.putBytes(bitmapData);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                Log.i("uri",downloadUrl.toString());

                HashMap<String, Object> dataMap = new HashMap<>();

                Toast.makeText(PaintActivity.this, "Successfully uploaded image", Toast.LENGTH_SHORT).show();
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoStringLink = uri.toString();
                        dataMap.put("Drawing_Link", photoStringLink);
                        root.child(stringas).updateChildren(dataMap);
                    }
                });
                

            }
        });

    }



    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                signatureView.setPenColor(color);

            }
        });
        ambilWarnaDialog.show();
    }

    private void askPermission() {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                Toast.makeText(PaintActivity.this, "Allowed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                    imageView.setImageURI(fileUri);
                    Drawable d = imageView.getDrawable();
                    signatureView.setBackground(d);

                }
            }
        }
    }

    public void OpenActivity(){
        Intent intent = new Intent(this, reviewDeclaration.class);
        startActivity(intent);
    }

    public void OpenActivity2(){
        Intent intent = new Intent(this, A_FinalUI_1.class);
        startActivity(intent);
    }
}

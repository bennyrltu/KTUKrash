package edu.ktu.ktukrash;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.*;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PaintActivity extends AppCompatActivity {

    int defaultColor;
    SignatureView signatureView;
    ImageButton imgEraser, imgColor, imgSave;
    SeekBar seekBar;
    TextView txtPenSize;
    private static String fileName;

    private ProgressDialog mProgressDialog;
    private StorageReference mStorageRef;
    private FirebaseAuth auth;




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

        askPermission();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());
        fileName = date;

        if(!path.exists())
        {
            path.mkdirs();
        }


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
            }
        }) ;

    }


    private void saveImage() throws IOException {
        File file = new File(fileName);
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();

        Bitmap bitmap = signatureView.getSignatureBitmap();

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
}

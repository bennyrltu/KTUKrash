package edu.ktu.ktukrash;

import static android.graphics.drawable.Drawable.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class EventPictures extends AppCompatActivity {

    String fileName1, fileName2, fileName3;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    TextView textView11;

    Uri fileUri;
    Uri fileUri2;
    Uri fileUri3;

    public boolean IMG1 = false;
    public boolean IMG2 = false;
    public boolean IMG3 = false;

    ProgressBar progressBar;
    TextView progressTextView;
    Button uploadButton, backButton;
    public float x = 220f;

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference root = db.getReference()
            .child("Declaration_Data")
            .child(currentuser)
            .child("Declarations");


    public static final String EXTRA_TEXT6 = "ktu.edu.KTUKrash.EXTRA.TEXT6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pictures);

        fileName1 = "empty";
        fileName2 = "empty";
        fileName3 = "empty";

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setEnabled(false);
        uploadButton.setBackground(ContextCompat.getDrawable(this, R.drawable.grayedround));

        backButton = findViewById(R.id.backButton);

        progressBar = findViewById(R.id.progressBar);
        progressTextView = findViewById(R.id.progressTextView);

        textView11 = findViewById(R.id.textView11);
        Intent intent = getIntent();
        String text2 = intent.getStringExtra(SecondPersonData.EXTRA_TEXT5);


        textView11.setText(text2);

        String stringas = textView11.getText().toString().trim();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker
                        .Companion
                        .with(EventPictures.this)
                        .start(1);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                ImagePicker
                        .Companion
                        .with(EventPictures.this)
                        .start(2);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                ImagePicker
                        .Companion
                        .with(EventPictures.this)
                        .start(3);

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference uploadImageRef = storageRef.child("images/" + currentuser + "/" + "EVENT_" + fileUri.getLastPathSegment());
                StorageReference uploadImageRef2 = storageRef.child("images/" + currentuser + "/" + "FIRST_" + fileUri2.getLastPathSegment());
                StorageReference uploadImageRef3 = storageRef.child("images/" + currentuser + "/" + "SECOND_" + fileUri3.getLastPathSegment());

                UploadTask uploadTask = uploadImageRef.putFile(fileUri);
                UploadTask uploadTask2 = uploadImageRef2.putFile(fileUri2);
                UploadTask uploadTask3 = uploadImageRef3.putFile(fileUri3);

                HashMap<String, Object> dataMap = new HashMap<>();

                uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(EventPictures.this, "Successfully uploaded image", Toast.LENGTH_SHORT).show();
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                dataMap.put("Second_Image_Link", photoStringLink);
                                root.child(stringas).updateChildren(dataMap);
                            }
                        });

                    }
                });
                uploadTask2.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        imageView2.requestFocus();
                    }
                });


                uploadTask3.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(EventPictures.this, "Successfully uploaded image", Toast.LENGTH_SHORT).show();
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                dataMap.put("Third_Image_Link", photoStringLink);
                                root.child(stringas).updateChildren(dataMap);
                            }
                        });

                    }
                });

                uploadTask3.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        imageView2.requestFocus();
                    }
                });

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(EventPictures.this, "Successfully uploaded image", Toast.LENGTH_SHORT).show();
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                dataMap.put("First_Image_Link", photoStringLink);
                                root.child(stringas).updateChildren(dataMap);
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventPictures.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                        imageView.requestFocus();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressBar.setProgress((int) progress);
                                String progressString = ((int) progress) + "% done";
                                progressTextView.setText(progressString);
                            }
                        });
                openNext();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreviousActivity();
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "TranslationX", x);

        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

    }

    private void openPreviousActivity() {
        Intent intent = new Intent(this, SecondPersonData.class);
        startActivity(intent);
    }

    private void openNext() {
        Intent intent = new Intent(this, PaintActivity.class);


        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        data1.put("Pic1", fileName1);
        data1.put("Pic2", fileName2);
        data1.put("Pic3", fileName3);
        intent.putExtra("pdfData1", data1);
        intent.putExtra("pdfData2", data2);

        String stringas = textView11.getText().toString().trim();
        intent.putExtra(EXTRA_TEXT6, stringas);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                    try {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                        fileName1 = path + "/" + "pic1" + ".png";
                        File file1 = new File(fileName1);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapData = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(file1);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageURI(fileUri);
                    IMG1 = true;
                    if (IMG1 && IMG2 && IMG3) {
                        uploadButton.setEnabled(true);
                        uploadButton.setBackground(ContextCompat.getDrawable(this, R.drawable.gradientbackr));
                    }

                }
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri2 = data.getData();

                    try {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                        fileName2 = path + "/" + "pic2" + ".png";
                        File file1 = new File(fileName2);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri2);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapData = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(file1);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageView2.setImageURI(fileUri2);
                    IMG2 = true;
                    if (IMG1 && IMG2 && IMG3) {
                        uploadButton.setEnabled(true);
                        uploadButton.setBackground(ContextCompat.getDrawable(this, R.drawable.gradientbackr));
                    }
                }
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri3 = data.getData();

                    try {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                        fileName3 = path + "/" + "pic3" + ".png";
                        File file1 = new File(fileName3);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri3);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bitmapData = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(file1);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageView3.setImageURI(fileUri3);
                    IMG3 = true;
                    if (IMG1 && IMG2 && IMG3) {
                        uploadButton.setEnabled(true);
                        uploadButton.setBackground(ContextCompat.getDrawable(this, R.drawable.gradientbackr));
                    }
                }
            }
        }
    }
}
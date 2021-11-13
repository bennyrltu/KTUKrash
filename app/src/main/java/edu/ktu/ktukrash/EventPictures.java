package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;

public class EventPictures extends AppCompatActivity {

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    TextView textView11;

    Uri fileUri;
    Uri fileUri2;
    Uri fileUri3;

    ProgressBar progressBar;
    TextView progressTextView;
    Button uploadButton, backButton;

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

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        uploadButton = findViewById(R.id.uploadButton);
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
                StorageReference uploadImageRef = storageRef.child("images/" + currentuser + "/"+"EVENT_"+ fileUri.getLastPathSegment());
                StorageReference uploadImageRef2 = storageRef.child("images/" + currentuser + "/"+"FIRST_"+ fileUri2.getLastPathSegment());
                StorageReference uploadImageRef3 = storageRef.child("images/" + currentuser + "/"+"SECOND_"+ fileUri3.getLastPathSegment());

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

    }
    private void openPreviousActivity() {
        Intent intent = new Intent(this, SecondPersonData.class);
        startActivity(intent);
    }
    private void openNext() {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                    imageView.setImageURI(fileUri);

                }
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri2 = data.getData();
                    imageView2.setImageURI(fileUri2);
                }
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileUri3 = data.getData();
                    imageView3.setImageURI(fileUri3);
                }
            }
        }
    }


}
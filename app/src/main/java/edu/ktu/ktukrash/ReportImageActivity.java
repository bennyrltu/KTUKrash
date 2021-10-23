package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ReportImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    private RetrieveDeclarationImages adapter;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;

    private RetrieveDeclarationImages retrieveDeclarationImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        setContentView(R.layout.activity_report_image);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        //FirebaseStorage mStorage = FirebaseStorage.getInstance();
        //StorageReference ref = mStorage.getReferenceFromUrl("gs://krashapp-d2bad.appspot.com/ReportPaintings/" + userID);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        adapter = new RetrieveDeclarationImages(this, list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }

                //list = new Model(ReportImageActivity.this , list);
                //recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(ReportImageActivity.this, "Show", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportImageActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package edu.ktu.ktukrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerV;
    MyAdapter myAdapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_recyclerview);
        recyclerV = findViewById(R.id.recyclerView);

        preferences = this.getSharedPreferences("My preferences", MODE_PRIVATE);

        getMyList();
    }

    private void getMyList(){
        ArrayList<recyclerModel> arr = new ArrayList<>();

        recyclerModel n = new recyclerModel();
        n.setTitle("Profile");
        n.setDescription("Profile section");
        n.setImg(R.drawable.ic_account);
        arr.add(n);

        n = new recyclerModel();
        n.setTitle("Gallery");
        n.setDescription("Picture gallery");
        n.setImg(R.drawable.ic_gallery);
        arr.add(n);

        n = new recyclerModel();
        n.setTitle("Declaration");
        n.setDescription("Declaration filling form");
        n.setImg(R.drawable.ic_fill);
        arr.add(n);

        n = new recyclerModel();
        n.setTitle("Logout");
        n.setDescription("");
        n.setImg(R.drawable.ic_logout);
        arr.add(n);

        //return arr;

        recyclerV.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, arr);
        recyclerV.setAdapter(myAdapter);
    }




}
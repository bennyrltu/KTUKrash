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
        n.setTitle("Draw");
        n.setDescription("Drawing sections");
        n.setImg(R.drawable.ic_draw);
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
        n.setTitle("Upload");
        n.setDescription("Upload pictures");
        n.setImg(R.drawable.ic_upl);
        arr.add(n);

        n = new recyclerModel();
        n.setTitle("Logout");
        n.setDescription("");
        n.setImg(R.drawable.ic_logout);
        arr.add(n);

        //return arr;

        String sortPreferences = preferences.getString("Sort", "ascending");
        if (sortPreferences.equals("ascending")){
            Collections.sort(arr, recyclerModel.By_TITLE_ASCENDING);
        }else if(sortPreferences.equals("descending")){
            Collections.sort(arr, recyclerModel.By_TITLE_DESCENDING);
        }
        recyclerV.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, arr);
        recyclerV.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.sorting){
            sortDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortDialog() {
        String[] options = {"Ascending", "Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sort by: ");
        builder.setIcon(R.drawable.ic_sort);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "ascending");
                    editor.apply();
                    getMyList();
                }
                if (i == 1){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "descending");
                    editor.apply();
                    getMyList();
                }
            }
        });

        builder.create().show();
    }


}
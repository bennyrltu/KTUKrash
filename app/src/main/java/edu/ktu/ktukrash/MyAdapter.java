package edu.ktu.ktukrash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<recyclerModel> arr;
    CustomFilter filter;
    ArrayList<recyclerModel> filteredArr;

    public MyAdapter(Context c, ArrayList<recyclerModel> arr){
        this.c = c;
        this.arr = arr;
        this.filteredArr = arr;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_row, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tView.setText(arr.get(position).getTitle());
        holder.dView.setText(arr.get(position).getDescription());
        holder.imgView.setImageResource(arr.get(position).getImg());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String gTitle = arr.get(position).getTitle();

                if (gTitle == "Profile"){
                    Intent intent = new Intent(c,Profile.class);
                    c.startActivity(intent);
                }
                if (gTitle == "Draw"){
                    Intent intent = new Intent(c, PaintActivity.class);
                    c.startActivity(intent);
                }
                if (gTitle == "Gallery"){
                    Intent intent = new Intent(c, ImageGalleryActivity.class);
                    c.startActivity(intent);
                }
                if (gTitle == "Declaration"){
                    Intent intent = new Intent(c, DeclarationStart.class);
                    c.startActivity(intent);
                }
                if (gTitle == "Upload"){
                    Intent intent = new Intent(c, EventPictures.class);
                    c.startActivity(intent);
                }
                if (gTitle == "Logout"){
                    FirebaseAuth.getInstance().signOut();
                    c.startActivity(new Intent(c, MainActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter(filteredArr, this);
        }

        return filter;
    }
}

package edu.ktu.ktukrash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ImageAdapter extends FirebaseRecyclerAdapter<Upload, ImageAdapter.imageViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ImageAdapter(@NonNull FirebaseRecyclerOptions<Upload> options) {
        super(options);
    }

    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new imageViewHolder(v);
    }


    @NonNull
    @Override
    protected void onBindViewHolder(@NonNull imageViewHolder holder, int position, @NonNull Upload model) {
        Glide.with(holder.First_Image_Link.getContext())
                .load(model.getFirst_Image_Link())
                .into(holder.First_Image_Link);

        Glide.with(holder.Second_Image_Link.getContext())
                .load(model.getSecond_Image_Link())
                .into(holder.Second_Image_Link);

        Glide.with(holder.Third_Image_Link.getContext())
                .load(model.getThird_Image_Link())
                .into(holder.Third_Image_Link);

        Glide.with(holder.Drawing_Image_Link.getContext())
                .load(model.getDrawing_Link())
                .into(holder.Drawing_Image_Link);
    }

    class imageViewHolder extends RecyclerView.ViewHolder {
        ImageView First_Image_Link;
        ImageView Second_Image_Link;
        ImageView Third_Image_Link;
        ImageView Drawing_Image_Link;

        public imageViewHolder(@NonNull View itemView) {
            super(itemView);
            First_Image_Link = (ImageView)itemView.findViewById(R.id.image_view_upload);
            Second_Image_Link = (ImageView)itemView.findViewById(R.id.image_view_upload2);
            Third_Image_Link = (ImageView)itemView.findViewById(R.id.image_view_upload2);
            Drawing_Image_Link = (ImageView)itemView.findViewById(R.id.image_view_upload4);
        }
    }
}
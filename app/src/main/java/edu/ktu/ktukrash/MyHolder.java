package edu.ktu.ktukrash;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imgView;
    TextView tView, dView;
    ItemClickListener itemClickListener;
    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.imgView = itemView.findViewById(R.id.recImage);
        this.tView = itemView.findViewById(R.id.recTitle);
        this.dView = itemView.findViewById(R.id.recDesc);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClickListener(view, getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}

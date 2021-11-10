package edu.ktu.ktukrash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.FP_Header.setText("First person data: ");
        holder.FP_Name.setText("First name: " + model.getFP_Name());
        holder.FP_LastName.setText("Last name: "+ model.getFP_LastName());
        holder.FP_Birthdate.setText("Birthdate: " + model.getFP_Birthdate());
        holder.FP_PhoneNumber.setText("Phonenumber: " +model.getFP_PhoneNumber());
        holder.FP_Location.setText("Address: "+ model.getFP_Location());
        holder.FP_Email.setText("Email: "+model.getFP_Email());
        holder.FP_PersonalCode.setText("Personal code: "+model.getFP_PersonalCode());
        holder.FP_CarNumber.setText("Car number: " + model.getFP_CarNumber());
        Glide.with(holder.First_Image_Link.getContext())
                .load(model.getFirst_Image_Link())
                .into(holder.First_Image_Link);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.First_Image_Link.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,2000)
                        .create();



                View view = dialogPlus.getHolderView();
                EditText name= view.findViewById(R.id.txtName);
                EditText lastname= view.findViewById(R.id.txtLastName);
                EditText birthdate= view.findViewById(R.id.txtBirthdate);
                EditText phonenumber= view.findViewById(R.id.txtPhoneNumber);
                EditText address= view.findViewById(R.id.txtAdress);
                EditText email= view.findViewById(R.id.txtEmail);
                EditText personalcode= view.findViewById(R.id.txtPersonalCode);

                Button btnUpdate=view.findViewById(R.id.btnUpdate);

                name.setText(model.getFP_Name());
                lastname.setText(model.getFP_LastName());
                birthdate.setText(model.getFP_Birthdate());
                phonenumber.setText(model.getFP_PhoneNumber());
                address.setText(model.getFP_Location());
                email.setText(model.getFP_Email());
                personalcode.setText(model.getFP_PersonalCode());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("FP_Name", name.getText().toString().trim());
                        dataMap.put("FP_LastName", lastname.getText().toString().trim());
                        dataMap.put("FP_Birthdate", birthdate.getText().toString().trim());
                        dataMap.put("FP_PhoneNumber",phonenumber.getText().toString().trim());
                        dataMap.put("FP_Location", address.getText().toString().trim());
                        dataMap.put("FP_Email", email.getText().toString().trim());
                        dataMap.put("FP_PersonalCode", personalcode.getText().toString().trim());

                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


                        FirebaseDatabase.getInstance().getReference().child("Declaration_Data").child(currentuser).child("Declarations").child(getRef(position).getKey()).updateChildren(dataMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.FP_Name.getContext(), "Data updaded succesfully!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.FP_Name.getContext(), "Error when updating data!", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();

                            }
                        });

                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.FP_Name.getContext());
                builder.setTitle("Are you sure you want to delete?");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference().child("Declaration_Data").child(currentuser).child("Declarations").child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.FP_Name.getContext(), "Cancelled.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        //Glide.with(holder.First_Image_Link).load(model.getFirst_Image_Link());

        holder.SP_Header.setText("Second person data: ");
        holder.SP_Name.setText("First name: " + model.getSP_Name());
        holder.SP_LastName.setText("Last name: "+ model.getSP_LastName());
        holder.SP_Birthdate.setText("Birthdate: " + model.getSP_Birthdate());
        holder.SP_PhoneNumber.setText("Phonenumber: " +model.getSP_PhoneNumber());
        holder.SP_Location.setText("Address: "+ model.getSP_Location());
        holder.SP_Email.setText("Email: "+model.getSP_Email());
        holder.SP_PersonalCode.setText("Personal code: "+model.getSP_PersonalCode());
        holder.SP_CarNumber.setText("Car number: " + model.getSP_CarNumber());
        Glide.with(holder.Second_Image_Link.getContext())
                .load(model.getSecond_Image_Link())
                .into(holder.Second_Image_Link);

        Glide.with(holder.Drawing_Link.getContext())
                .load(model.getDrawing_Link())
                .into(holder.Drawing_Link);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }


    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView First_Image_Link;
        TextView FP_Name, FP_LastName, FP_Birthdate, FP_PhoneNumber, FP_Location, FP_Email, FP_PersonalCode, FP_Header, FP_CarNumber;


        Button btnEdit, btnDelete;
        ImageView Second_Image_Link;
        TextView SP_Name, SP_LastName, SP_Birthdate, SP_PhoneNumber, SP_Location, SP_Email, SP_PersonalCode, SP_Header, SP_CarNumber;
        ImageView Drawing_Link;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            FP_Name = (TextView)itemView.findViewById(R.id.FPnameText);
            FP_LastName = (TextView)itemView.findViewById(R.id.FPlastnameText);
            FP_Birthdate = (TextView)itemView.findViewById(R.id.FPbirthdateText);
            FP_PhoneNumber = (TextView)itemView.findViewById(R.id.FPphoneText);
            FP_Location = (TextView)itemView.findViewById(R.id.FPaddressText);
            FP_Email = (TextView)itemView.findViewById(R.id.FPemailText);
            FP_PersonalCode = (TextView)itemView.findViewById(R.id.FPpersonalText);
            FP_Header = (TextView)itemView.findViewById(R.id.FPheaderext);
            FP_CarNumber = (TextView)itemView.findViewById(R.id.FPcarnumbertext);

            First_Image_Link = (ImageView)itemView.findViewById(R.id.img1);
            Second_Image_Link = (ImageView)itemView.findViewById(R.id.img2);
            Drawing_Link = (ImageView)itemView.findViewById(R.id.img3);

            SP_Name = (TextView)itemView.findViewById(R.id.SPnameText);
            SP_LastName = (TextView)itemView.findViewById(R.id.SPlastnameText);
            SP_Birthdate = (TextView)itemView.findViewById(R.id.SPbirthdateText);
            SP_PhoneNumber = (TextView)itemView.findViewById(R.id.SPphoneText);
            SP_Location = (TextView)itemView.findViewById(R.id.SPaddressText);
            SP_Email = (TextView)itemView.findViewById(R.id.SPemailText);
            SP_PersonalCode = (TextView)itemView.findViewById(R.id.SPpersonalText);
            SP_Header = (TextView)itemView.findViewById(R.id.SPheaderext);
            SP_CarNumber = (TextView)itemView.findViewById(R.id.SPcarnumbertext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);

        }
    }
}

package com.example.covidbot.DoctorConnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.covidbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MultipleChoiceDialogFragment1  extends DialogFragment {
    DatabaseReference mRef,gRef,bRef;
    FirebaseAuth mAuth;
    String counts;
    public  interface onMultiChoiceListener{
        void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList);
        void onNegativeButtonClicked();
    }
    onMultiChoiceListener mListener1;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            mListener= (MultipleChoiceDialogFragment1.onMultiChoiceListener) context;
//        } catch (Exception e){
//            throw new ClassCastException(getActivity().toString()+"onMultiChoiceListener must implemented");
//        }
//
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener1= (onMultiChoiceListener) context;
        } catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        final ArrayList<String> selectItemList = new ArrayList<>();
        gRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
        bRef=FirebaseDatabase.getInstance().getReference("patientdetails").child(mAuth.getCurrentUser().getUid());
        gRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status=dataSnapshot.child("currentstatus").getValue().toString();
                counts=dataSnapshot.child("count").getValue().toString();
                mRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AlertDialog.Builder builder1= new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.duration);
        builder1.setTitle("Duration of problem").setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    String id=mRef.push().getKey();
                    String id1=bRef.push().getKey();
                    mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child(counts).child("duration").child(id).setValue(list[which]);
                    bRef.child("bookeddetails").child(counts).child("duration").child(id1).setValue(list[which]);
                    selectItemList.add(list[which]);

                }else{
                    selectItemList.remove(list[which]);
                }
            }
        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener1.onPositiveButtonClicked(list,selectItemList);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener1.onNegativeButtonClicked();
                    }
                });
        return builder1.create();
    }
}


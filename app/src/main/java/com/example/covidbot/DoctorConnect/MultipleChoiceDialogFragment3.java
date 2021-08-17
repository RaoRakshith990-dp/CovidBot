package com.example.covidbot.DoctorConnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

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

public class MultipleChoiceDialogFragment3 extends DialogFragment {
    DatabaseReference mRef,gRef,bRef;
    FirebaseAuth mAuth;
    String counts;
    public  interface onMultiChoiceListener{
        void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList);
        void onNegativeButtonClicked();
    }
    onMultiChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener= (onMultiChoiceListener) context;
        } catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"onMultiChoiceListener must implemented");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        final ArrayList<String> selectItemList = new ArrayList<>();
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
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
        final String[] list=getActivity().getResources().getStringArray(R.array.anyknown);
        builder.setTitle("Any known problems").setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    String id=mRef.push().getKey();
                    String id1=bRef.push().getKey();
                    mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child(counts).child("otherknown").child(id).setValue(list[which]);
                    bRef.child("bookeddetails").child(counts).child("otherknown").child(id1).setValue(list[which]);
                    if(list[which].equals("others")){
                        bRef.child("othersdisp").setValue("1");
                    }
                    selectItemList.add(list[which]);


                }else{
                    selectItemList.remove(list[which]);
                }
            }
        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(list,selectItemList);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }
}


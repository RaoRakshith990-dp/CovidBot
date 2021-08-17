package com.example.covidbot.DoctorConnect;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Booking4 extends AppCompatActivity {
Button selectdate,confirm;
TextView datetexts;
DatePickerDialog datePickerDialog;
int year;
int month;
int dayofmonth;
int counts=0;
Calendar calendar;
Spinner times;
ArrayAdapter<String> adapter;
ArrayList<String> spinnerDataList;
DatabaseReference mRef,gRef,bRef,mbRef;
FirebaseAuth mAuth;
EditText namepat,emailpat;
String timing,timeid;
String status,count,types;
ImageView back4;
Button call;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking4);
       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Booking4.this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Date currentTime = Calendar.getInstance().getTime();
        String type=getIntent().getStringExtra("appttype");
        timeid= String.valueOf(currentTime);
        //call=findViewById(R.id.virtualcall);
        namepat=findViewById(R.id.entername);
        back4=findViewById(R.id.booking4back);
        if(type.equals("physical")){
            types="Physical Checkup";
        }else {
            types="Virtual Checkup";
        }
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        emailpat=findViewById(R.id.entermail);
        selectdate= findViewById(R.id.select);
        confirm=findViewById(R.id.confirmappoint);
        datetexts=findViewById(R.id.datetext);
        times=findViewById(R.id.timespin);
        mAuth=FirebaseAuth.getInstance();
        gRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
        bRef=FirebaseDatabase.getInstance().getReference("patientdetails").child(mAuth.getCurrentUser().getUid());
        gRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count= dataSnapshot.child("count").getValue().toString();
                status=dataSnapshot.child("currentstatus").getValue().toString();
                mRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status).child("timings");

                spinnerDataList=new ArrayList<>();
                adapter= new ArrayAdapter<String>(Booking4.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
                times.setAdapter(adapter);
                retrieveData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog= new DatePickerDialog(Booking4.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datetexts.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,dayofmonth);
                datePickerDialog.show();
            }
        });
        times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timing=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(datetexts.getText().toString())){
                    datetexts.setError("This is required field");
                }else if(TextUtils.isEmpty(timing)){
                    Toast.makeText(Booking4.this, "Please select the time", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(namepat.getText().toString())){
                    namepat.setError("This is required field");
                }else if(TextUtils.isEmpty(emailpat.getText().toString())){
                   emailpat.setError("This is required field");
                }
                else{
                    //String messagephone=datetexts.getText().toString()+timing+namepat.getText().toString()+emailpat.getText().toString()+types;
                    String messagetodoc="New Booking through ZeroHourHealthCare"+"       "+"An"+" "+types+" "+"has been booked by "+" "+namepat.getText().toString()+" "+"at"+" "+timing+" "+"on"+" "+datetexts.getText().toString()+"."+" "+"You can further contact with the patient on mail"+" "+emailpat.getText().toString()+"."+" "+"For any clarifications contact our helpline.";
                    editor.putString("messagetodoctor",messagetodoc);
                    editor.commit();
                    add();

                }

            }
        });


    }
    public void retrieveData(){
        ValueEventListener listener = mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for(DataSnapshot item : dataSnapshot.getChildren()){
               spinnerDataList.add(item.getValue().toString());
           }
           adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void add(){
        Booking4details details=new Booking4details(datetexts.getText().toString(),timing,namepat.getText().toString(),emailpat.getText().toString(),types);
        String id=bRef.push().getKey();
        bRef.child("bookeddetails").child(count).setValue(details);
        mbRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status);
        String id1=mbRef.push().getKey();
        mbRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).setValue(details);
        Intent intent= new Intent(Booking4.this,Booking5.class);
        intent.putExtra("typ",types);
        startActivity(intent);
    }
}

package com.example.covidbot.DoctorConnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.covidbot.R;
import com.example.covidbot.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoDoc extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
ViewPager viewPager;
LinearLayout linears;
DatabaseReference gRef;
String count;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_doc);
        mAuth=FirebaseAuth.getInstance();
        linears=findViewById(R.id.booklinear);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
         viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        gRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
       // bRef=FirebaseDatabase.getInstance().getReference("patientdetails").child(mAuth.getCurrentUser().getUid());
        gRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("count")){
                    count= dataSnapshot.child("count").getValue().toString();
                }else {
                    gRef.child("count").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        linears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  counts = Integer.parseInt(count) + 1;
                gRef.child("count").setValue(counts);
                PopupMenu popup= new PopupMenu(InfoDoc.this,v);
                popup.setOnMenuItemClickListener(InfoDoc.this);
                popup.inflate(R.menu.menu_popup);
                popup.show();
//                Intent intent=new Intent(InfoDoc.this,Booking4.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opt1:
//                Intent intent=new Intent(InfoDoc.this,Booking4.class);
//                intent.putExtra("appttype","physical");
//                startActivity(intent);
                Toast.makeText(this, "Due to covid-19 physical checkup services are currently dismissed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opt2:
                Intent intents=new Intent(InfoDoc.this,Booking4.class);
                intents.putExtra("appttype","videocall");

                startActivity(intents);
               // Toast.makeText(this, "inperson", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}
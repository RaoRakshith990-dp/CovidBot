package com.example.covidbot.DoctorConnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Tab1 extends Fragment {
    LinearLayout linearLayout;
    private RecyclerView recyclerView,recyclerView1,recyclerview2;
    private RecyclerView.Adapter adapter,adapter1,adapter2;
    private List<DayItem> listItems;
    private List<LangItem> langItems;
    private List<SpecialItem> speItems;
    DatabaseReference tRef,dRef,gRef,lRef,sRef;
    TextView year,money,qualify,adds,name;
    FirebaseAuth mAuth;
    String status;
    ImageView profimg;
    ScrollView mScrollView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.tab1,container,false);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=sharedPreferences.edit();
        linearLayout=view .findViewById(R.id.booklinear);
        recyclerView = view.findViewById(R.id.recyclerview);
        //mScrollView=view.findViewById(R.id.scrolling);
        name=view.findViewById(R.id.showdocname);
        qualify=view.findViewById(R.id.qualified);
        year=view.findViewById(R.id.years);
        profimg=view.findViewById(R.id.docprofileimg);
        money=view.findViewById(R.id.rupees);
        adds=view.findViewById(R.id.addressessss);
        recyclerview2=view.findViewById(R.id.recyclerviews1);
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setLayoutManager(new LinearLayoutManager(getContext()));
        speItems = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        listItems = new ArrayList<>();
        recyclerView1= view.findViewById(R.id.recyclerviews);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        langItems = new ArrayList<>();

        mAuth= FirebaseAuth.getInstance();
        gRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
        gRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("currentstatus").getValue().toString();
                tRef = FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status).child("days");
                lRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status).child("languages");
                sRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status).child("speciality");
                dRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status);
                dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String exps=dataSnapshot.child("docexperience").getValue().toString();
                        String price=dataSnapshot.child("consultationfee").getValue().toString();
                        String add=dataSnapshot.child("docaddress").getValue().toString();
                        String names=dataSnapshot.child("docname").getValue().toString();
                        String imgs=dataSnapshot.child("mimageuri").getValue().toString();
                        String qualif=dataSnapshot.child("docqualify").getValue().toString();
                        Picasso.with(getContext()).load(imgs).resize(120,120).transform(new CropCircleTransformation()).into(profimg);
                        year.setText(exps);
                        qualify.setText(qualif);
                        money.setText("Rs."+price);
                        adds.setText(add);
                        name.setText(names);
                        editor.putString("docfee", price);
                        editor.commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                tRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            DayItem com = postSnapshot.getValue(DayItem.class);
                            listItems.add(com);
                        }
                        adapter = new DayAdapter(getContext(), listItems);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(Tab1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                lRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsSnapshot : dataSnapshot.getChildren()) {
                            LangItem lang = postsSnapshot.getValue(LangItem.class);
                            langItems.add(lang);
                        }
                        adapter1 = new LangAdapter(getContext(), langItems);
                        recyclerView1.setAdapter(adapter1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(Tab1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                sRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsSnapshot1 : dataSnapshot.getChildren()) {
                            SpecialItem sp = postsSnapshot1.getValue(SpecialItem.class);
                            speItems.add(sp);
                        }
                        adapter2 = new SpAdapter(getContext(), speItems);
                        recyclerview2.setAdapter(adapter2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(Tab1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //lRef = FirebaseDatabase.getInstance().getReference("languages");


//        dRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String exps=dataSnapshot.child("docexperience").getValue().toString();
//                String price=dataSnapshot.child("consultationfee").getValue().toString();
//                String add=dataSnapshot.child("docaddress").getValue().toString();
//                year.setText(exps);
//                money.setText(price);
//                adds.setText(add);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        tRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                   DayItem com = postSnapshot.getValue(DayItem.class);
//                    listItems.add(com);
//                }
//                adapter = new DayAdapter(getContext(), listItems);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//              //  Toast.makeText(Tab1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView1= view.findViewById(R.id.recyclerviews);
//        recyclerView1.setHasFixedSize(true);
//        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
//        langItems = new ArrayList<>();
//        lRef = FirebaseDatabase.getInstance().getReference("languages");
//        lRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postsSnapshot : dataSnapshot.getChildren()) {
//                    LangItem lang = postsSnapshot.getValue(LangItem.class);
//                    langItems.add(lang);
//                }
//                adapter1 = new LangAdapter(getContext(), langItems);
//                recyclerView1.setAdapter(adapter1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //  Toast.makeText(Tab1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;

    }
}

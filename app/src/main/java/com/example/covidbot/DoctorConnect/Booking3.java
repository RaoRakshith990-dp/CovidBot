package com.example.covidbot.DoctorConnect;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidbot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Booking3 extends AppCompatActivity implements List<ListItem> {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter,adapter1;
    private List<ListItem> listItems;
    DatabaseReference mRef;
    ImageView back3;
    SearchView searchView;
    TextView inv;
    String doctypes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctypes=getIntent().getStringExtra("doctortype");
        if(doctypes==null){
            doctypes="General Physician";
        }
        setContentView(R.layout.activity_booking3);
        recyclerView = findViewById(R.id.recyclerview);
        searchView=findViewById(R.id.searchbar);
        inv=findViewById(R.id.invis);
        searchView.setQueryHint("Search for doctors");
        back3=findViewById(R.id.booking3back);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        mRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(doctypes);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ListItem com = postSnapshot.getValue(ListItem.class);
                    listItems.add(com);
                }
                adapter = new ReAdapter(Booking3.this, listItems);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Booking3.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if(searchView!=null){
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inv.setVisibility(View.INVISIBLE);
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    search(newText);
                    return true;
                }
            });
        }

    }

    private void search(String string){

        ArrayList<ListItem> myList= new ArrayList<>();
        for (ListItem object:listItems){
            if(object.getDocname().toLowerCase().contains(string.toLowerCase())){
                myList.add(object);
            }
        }
        adapter1=new ReAdapter(Booking3.this,myList);
        recyclerView.setAdapter(adapter1);
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<ListItem> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean add(ListItem listItem) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends ListItem> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends ListItem> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ListItem get(int index) {
        return null;
    }

    @Override
    public ListItem set(int index, ListItem element) {
        return null;
    }

    @Override
    public void add(int index, ListItem element) {

    }

    @Override
    public ListItem remove(int index) {
        return null;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<ListItem> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<ListItem> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<ListItem> subList(int fromIndex, int toIndex) {
        return null;
    }
}
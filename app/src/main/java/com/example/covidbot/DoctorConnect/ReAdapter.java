package com.example.covidbot.DoctorConnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    public ReAdapter(Context context,List<ListItem> listItems) {
        this.listItems = listItems;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mAuth=FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
        final ListItem listItem=listItems.get(position);
        holder.name.setText(listItem.getDocname());
        holder.address.setText(listItem.getDoccity());
        holder.experience.setText(listItem.getDocexperience());
        holder.fee.setText(listItem.getConsultationfee());
        final String code=listItem.getCode();
        Picasso.with(context).load(listItem.getMimageuri()).resize(80,80).transform(new CropCircleTransformation()).into(holder.profile);
        holder.books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("currentstatus").setValue(code);
                Intent intent= new Intent(context,InfoDoc.class);
                intent.putExtra("code",code);
                context.startActivity(intent);
            }
        });
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"YouViewed"+listItem.getUser(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView address;
        public  TextView experience;
        public TextView fee;
        public ImageView profile;
        public Button books;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.namedoct);
            address= itemView.findViewById(R.id.addressdoct);
            experience= itemView.findViewById(R.id.expdoct);
            fee= itemView.findViewById(R.id.feedoct);
            profile= itemView.findViewById(R.id.imgdoct);
            books= itemView.findViewById(R.id.bookdoct);
        }
    }
}

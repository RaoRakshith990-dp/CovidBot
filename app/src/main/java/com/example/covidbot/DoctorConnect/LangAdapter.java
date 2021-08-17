package com.example.covidbot.DoctorConnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidbot.R;

import java.util.List;

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {
    private List<LangItem> listItems;
    private Context context;

    public LangAdapter(Context context, List<LangItem> listItems) {
        this.listItems = listItems;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lang_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LangItem listItem=listItems.get(position);
        holder.day.setText(listItem.getLanguages());
//        holder.name.setText(listItem.getName());
//        holder.address.setText(listItem.getAddress());
//        holder.experience.setText(listItem.getExperience());
//        holder.fee.setText(listItem.getFees());
//        Picasso.with(context).load(listItem.getMimageuri()).fit().centerCrop().into(holder.profile);
//        holder.books.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(context,InfoDoc.class);
//                context.startActivity(intent);
//            }
//        });
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
        public TextView day;

        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day= itemView.findViewById(R.id.language12);

        }
    }
}


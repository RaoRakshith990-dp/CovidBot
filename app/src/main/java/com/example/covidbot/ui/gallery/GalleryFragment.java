package com.example.covidbot.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidbot.ChatInterface.DisplayUsers;
import com.example.covidbot.ChatInterface.RecentChats;
import com.example.covidbot.DoctorConnect.Booking3;
import com.example.covidbot.R;
import com.example.covidbot.VideoCall;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Button btn1,btn2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        btn1 = root.findViewById(R.id.display);
        btn2 = root.findViewById(R.id.recent);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VideoCall.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), RecentChats.class));
                startActivity(new Intent(getContext(), Booking3.class));
            }
        });
        return root;
    }
}
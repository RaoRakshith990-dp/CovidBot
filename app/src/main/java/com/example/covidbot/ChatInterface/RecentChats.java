package com.example.covidbot.ChatInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covidbot.Adapter.UserAdapter;
import com.example.covidbot.Model.Chat;
import com.example.covidbot.Model.Chatlist;
import com.example.covidbot.Model.User;
import com.example.covidbot.Notifications.Token;
import com.example.covidbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class RecentChats extends AppCompatActivity {
private RecyclerView recyclerView;
private UserAdapter userAdapter;
private List<User> mUsers;
FirebaseUser fUser;
DatabaseReference reference;
private List<Chatlist> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chats);
        recyclerView = findViewById(R.id.recycler_view_recent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecentChats.this));
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                usersList.clear();
//                for(DataSnapshot snapshot1: snapshot.getChildren()){
//                    Chat chat = snapshot1.getValue(Chat.class);
//                    if(chat.getSender().equals(fUser.getUid())){
//                        usersList.add(chat.getReciever());
//                    }
//                    if(chat.getReciever().equals(fUser.getUid())){
//                        usersList.add(chat.getSender());
//                    }
//                }
//                readChats();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           usersList.clear();
           for(DataSnapshot snapshot1 : snapshot.getChildren()){
               Chatlist chatlist = snapshot1.getValue(Chatlist.class);
               usersList.add(chatlist);
           }
           chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fUser.getUid()).setValue(token1);
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    for(Chatlist chatlist : usersList){
                        if(user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(RecentChats.this,mUsers,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void readChats() {
//        mUsers = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("Uders");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUsers.clear();
//                for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                    User user = snapshot1.getValue(User.class);
//                    for(String id:usersList){
//                        if(user.getId().equals(id)){
//                            if(mUsers.size()!=0){
//                                for(User user1 : mUsers){
//                                    if(!user.getId().equals(user1.getId())){
//                                        mUsers.add(user);
//                                    }
//                                }
//                            }else {
//                                mUsers.add(user);
//                            }
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(RecentChats.this,mUsers,true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
package com.example.covidbot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
private EditText signph,signpass,signotp;
private Button signup,nxt;
FirebaseAuth mAuth;
DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        init();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void init() {
        signph = findViewById(R.id.phsignup);
        signpass = findViewById(R.id.passsignup);
        signup = findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    private void CreateNewAccount() {
        final String emails=signph.getText().toString();
        String passwords=signpass.getText().toString();
        //final String name=username.getText().toString();
        //phones=ccp.getFullNumberWithPlus();
        if(TextUtils.isEmpty(passwords)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(emails)){
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String separated[] = emails.split("@");
                        mRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid()).child("Profile");
                        mRef.child("useremail").setValue(emails);
                        mRef.child("username").setValue(separated[0]);
                        startActivity(new Intent(RegisterPage.this,MainActivity.class));
                        Toast.makeText(RegisterPage.this, "Registere Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String message=task.getException().getMessage();
                        Toast.makeText(RegisterPage.this, "Error occured"+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
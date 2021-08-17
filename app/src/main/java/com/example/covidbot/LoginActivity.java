package com.example.covidbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidbot.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private SignInButton loginbtn;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText phlog,passlog;
    private Button login;
    private LinearLayout gotos;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String name ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.login);
        phlog = findViewById(R.id.phnumlogin);
        passlog = findViewById(R.id.passwordlogin);
        login=findViewById(R.id.btn_login);
        gotos = findViewById(R.id.btn_goto_signup);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor=sharedPreferences.edit();
        mAuth= FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAccount();
            }
        });
        gotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,UserType.class));
            }
        });
    }
    private void signIn(){
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // mCallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc=completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(this, "Signed In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(LoginActivity.this,NavActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void updateUI(FirebaseUser firebaseUser){
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null){
            mRef= FirebaseDatabase.getInstance().getReference("Users");
            String personName=account.getDisplayName();
            String personGivenName=account.getGivenName();
            String personFamilyName=account.getFamilyName();
            String personEmail=account.getEmail();
            String personId=account.getId();
            String personNum="N/A";
            Uri personPhoto=account.getPhotoUrl();
            //String perphoto=personPhoto;
            //ProfileDetails profde= new ProfileDetails(personName,personEmail, String.valueOf(personPhoto),personNum);
            User user = new User(personName,String.valueOf(personPhoto),mAuth.getCurrentUser().getUid(),"offline");
            mRef.child(mAuth.getCurrentUser().getUid()).setValue(user);
            Toast.makeText(this, "The user details are:"+personName+","+personGivenName+","+personFamilyName+","+personEmail+","+personId, Toast.LENGTH_SHORT).show();
        }
    }
    private void SignInAccount() {
        final String emails = phlog.getText().toString();
        String pass = passlog.getText().toString();

        if (TextUtils.isEmpty(emails)) {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(emails, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid()).child("Profile");
                        editor.putString("useremail",phlog.getText().toString());
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Successful LogIn", Toast.LENGTH_SHORT).show();
                        SendUserTodetails();
                        //progressDialog.dismiss();

                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, "Error occured" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void SendUserTodetails() {
        startActivity(new Intent(LoginActivity.this, NavActivity.class));

    }
}
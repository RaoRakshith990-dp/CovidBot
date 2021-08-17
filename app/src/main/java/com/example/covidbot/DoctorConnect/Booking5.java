package com.example.covidbot.DoctorConnect;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.covidbot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class  Booking5 extends AppCompatActivity implements MultipleChoiceDialogFragment.onMultiChoiceListener , MultipleChoiceDialogFragment1.onMultiChoiceListener,MultipleChoiceDialogFragment2.onMultiChoiceListener,MultipleChoiceDialogFragment3.onMultiChoiceListener {
TextView text1,text2,text3,text4,prot;
Button book5;
ImageView back5;
EditText ed1,ed2,edot;
ProgressBar progressBar;
DatabaseReference mRef,gRef,bRef,mdatabaseReference;
FirebaseAuth mAuth;
LinearLayout addinfo;
String name,docname,counts,docphone;
String stas="0",type,messagesdoc,docfee,docfeeconv;
StorageReference mstorageReference;
final static int PICK_PDF_CODE= 2342;
ListView listView;
List<PdfUpload> uploadList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking5);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Booking5.this);
        messagesdoc=sharedPreferences.getString("messagetodoctor","Hello");
        docfee=sharedPreferences.getString("docfee","2000");
        Toast.makeText(this, ""+messagesdoc, Toast.LENGTH_SHORT).show();
        type=getIntent().getStringExtra("typ");
        mAuth=FirebaseAuth.getInstance();
        prot=findViewById(R.id.progtxt);
        listView=findViewById(R.id.fileslist);
        progressBar=findViewById(R.id.progbar);
        edot=findViewById(R.id.othersss);
        ed1=findViewById(R.id.Enterage);
        ed2=findViewById(R.id.allergies);
        addinfo=findViewById(R.id.addinformant);
        back5=findViewById(R.id.booking5back);
        uploadList=new ArrayList<>();
        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Booking5.this,Booking4.class);
//                intent.putExtra("appttype",type);
//                startActivity(intent);
                onBackPressed();
            }
        });
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF();
            }
        });
        text1=findViewById(R.id.complain1);
        text2=findViewById(R.id.complain2);
        text3=findViewById(R.id.complain3);
        text4=findViewById(R.id.complain4);
        book5=findViewById(R.id.bookingg);
        gRef= FirebaseDatabase.getInstance().getReference("userdata").child(mAuth.getCurrentUser().getUid());
        bRef=FirebaseDatabase.getInstance().getReference("patientdetails").child(mAuth.getCurrentUser().getUid());
        mdatabaseReference=FirebaseDatabase.getInstance().getReference("PDF").child(Constants.DATABASE_PATH_UPLOADS);
        mstorageReference= FirebaseStorage.getInstance().getReference();
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(dataSnapshot.hasChild("othersdisp")){
               stas=dataSnapshot.child("othersdisp").getValue().toString();
               if(stas.equals("1")){
                   edot.setVisibility(View.VISIBLE);
               }else {
                   edot.setVisibility(View.GONE);
               }
           }else {
               bRef.child("othersdisp").setValue("0");
               stas="0";
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        gRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status=dataSnapshot.child("currentstatus").getValue().toString();
                counts=dataSnapshot.child("count").getValue().toString();
                mRef= FirebaseDatabase.getInstance().getReference("doctor").child("docdetails").child(status);
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("docname")) {
                            docname = dataSnapshot.child("docname").getValue().toString();
                        }
                        if (dataSnapshot.hasChild("docphone")) {
                            docphone = dataSnapshot.child("docphone").getValue().toString();
                        }else {
                            docphone="8431753735";
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment multiChoiceDialog = new MultipleChoiceDialogFragment();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(),"Multichoice Dialog");
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment multiChoiceDialog1 = new MultipleChoiceDialogFragment1();
                multiChoiceDialog1.setCancelable(false);
                multiChoiceDialog1.show(getSupportFragmentManager(),"Multichoice Dialog1");
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment multiChoiceDialog2 = new MultipleChoiceDialogFragment2();
                multiChoiceDialog2.setCancelable(false);
                multiChoiceDialog2.show(getSupportFragmentManager(),"Multichoice Dialog1");
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment multiChoiceDialog3 = new MultipleChoiceDialogFragment3();
                multiChoiceDialog3.setCancelable(false);
                multiChoiceDialog3.show(getSupportFragmentManager(),"Multichoice Dialog1");
            }
        });
        book5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                double totfee= Double.parseDouble(docfee)*100;
                docfeeconv= String.valueOf(totfee);
                String id=mRef.push().getKey();
                String id1=bRef.push().getKey();
                //  Booking5Details b5det=new Booking5Details(ed1.getText().toString(),ed2.getText().toString());
                mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child("age").setValue(ed1.getText().toString());
                mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child("allergy").setValue(ed2.getText().toString());
                mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child("docname").setValue(docname);
                bRef.child("bookeddetails").child(counts).child("docname").setValue(docname);
                bRef.child("bookeddetails").child(counts).child("age").setValue(ed1.getText().toString());
                bRef.child("bookeddetails").child(counts).child("allergy").setValue(ed2.getText().toString());

                if(stas.equals("1")){
                    mRef.child("bookeddetails").child(mAuth.getCurrentUser().getUid()).child(counts).child("otherinfostas").setValue(edot.getText().toString());
                    bRef.child("bookeddetails").child(counts).child("otherinfostas").setValue(edot.getText().toString());
                    bRef.child("othersdisp").setValue("0");
                }
                Toast.makeText(Booking5.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                try {
                    String apiKey="apikey=" + "5iz1Csxf6SA-B8K7FCM1bACQjyzoNRcKst9cZeT9dx";
                    String message="&message="+messagesdoc;
                    String sender="&sender="+"TXTLCL";
                    String numbers="&numbers="+docphone;
                    HttpURLConnection connection= (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data=apiKey+numbers+message+sender;
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Length",Integer.toString(data.length()));
                    connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
                    final BufferedReader rd=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line=rd.readLine())!=null){
                        Toast.makeText(Booking5.this, ""+line, Toast.LENGTH_SHORT).show();
                    }
                    rd.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Booking5.this, "message not sent"+e, Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(Booking5.this, Bookhistory.class));
                Toast.makeText(Booking5.this, "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });
        ThreadPolicy policy=new ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PdfUpload upload=uploadList.get(position);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });
        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    PdfUpload upload=postSnapshot.getValue(PdfUpload.class);
                    uploadList.add(upload);
                }
                String[] uploads=new String[uploadList.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i]=uploadList.get(i).getPdfname();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Select Choices = ");
        for(String str:selectedItemList){
            stringBuilder.append(str+"");
        }
        Toast.makeText(this, ""+stringBuilder, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendtextMessage(){
        try {
            String apiKey="apikey=" + "5iz1Csxf6SA-B8K7FCM1bACQjyzoNRcKst9cZeT9dx";
            String message="&message="+"Hello Doc";
            String sender="&sender="+"ZHHC";
            String numbers="&numbers="+"8431753735";
            HttpURLConnection connection= (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data=apiKey+numbers+message+sender;
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length",Integer.toString(data.length()));
            connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
            final BufferedReader rd=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=rd.readLine())!=null){
                Toast.makeText(this, ""+line, Toast.LENGTH_SHORT).show();
            }
            rd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getPDF(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
            startActivity(intent);
            return;
        }
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"),PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PDF_CODE && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            if(data.getData()!=null){
                uploadFile(data.getData());
            }else {
                Toast.makeText(this,"No File Chosen",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadFile(Uri data){
        String myUrl;
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef=mstorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis()+".pdf");
        sRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       Uri downloadUri=uri;
                        progressBar.setVisibility(View.GONE);
                        prot.setText("File Uploaded Successfully");
                        Toast.makeText(Booking5.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        PdfUpload upload=new PdfUpload("PDF1",downloadUri.toString());
                        mdatabaseReference.child(mdatabaseReference.push().getKey()).setValue(upload);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress= (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                prot.setText(String.valueOf(progress)+"% Uploading...");
            }
        });
    }
}

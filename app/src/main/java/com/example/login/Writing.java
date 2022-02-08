package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Writing extends AppCompatActivity{
    private Button btn_register, btn_cancel;
    private EditText content, title;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebasedatabase;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        firebasedatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= firebasedatabase.getReference();
        setContentView(R.layout.writing);
        content=findViewById(R.id.content);
        title=findViewById(R.id.title);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_register=findViewById(R.id.btn_register);
        final CheckBox sepa = findViewById(R.id.checksepa);
        final CheckBox envi = findViewById(R.id.checkenviron);

        if(firebaseAuth.getUid().equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            sepa.setVisibility(View.VISIBLE);
            envi.setVisibility(View.VISIBLE);
        }
        else{
            sepa.setVisibility(View.INVISIBLE);
            envi.setVisibility(View.INVISIBLE);
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Writing.this, Community.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = firebaseAuth.getUid();
                String c = content.getText().toString();
                String t = title.getText().toString();
                if (c.equals("") || t.equals("")) {
                    Toast.makeText(Writing.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                    if (uid.equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")) { //관리자일 때
                        if (sepa.isChecked() && !envi.isChecked()) {
                            WriteOn_sepa(t,c,uid);
                        } else if (envi.isChecked() && !sepa.isChecked()) {
                            WriteOn_envi(t,c,uid);
                        }else if(envi.isChecked()&&sepa.isChecked()){
                            Toast.makeText(Writing.this, "하나의 게시판만 선택해주세요", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Writing.this, "게시판을 선택해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                         WriteOn_comu(t,c,uid);
                        }
                }
        });
    }

    private void WriteOn_envi(String title, String content, String uid){
        databaseReference.child("envi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference = firebasedatabase.getReference("envi");
                WriteInfo writeInfo = new WriteInfo(title, content, "admin","envi",uid);
                databaseReference.push().setValue(writeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Writing.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Writing.this, Environment.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Writing.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void WriteOn_sepa(String title, String content, String uid){
        databaseReference.child("sepa").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference = firebasedatabase.getReference("sepa");
                WriteInfo writeInfo = new WriteInfo(title, content, "admin","sepa", uid);
                databaseReference.push().setValue(writeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Writing.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Writing.this, Separate.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Writing.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void WriteOn_comu(String title, String content, String uid){
        databaseReference = firebasedatabase.getReference("user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if(snapshot.getKey().equals(uid)){
                        User user = snapshot.getValue(User.class);
                        name=user.getUsername();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //오류 발생 시
                Toast.makeText(Writing.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Writing.this, Community.class);
                startActivity(intent);
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference = firebasedatabase.getReference("comu");
                WriteInfo writeInfo = new WriteInfo(title, content, name,"comu",uid);
                databaseReference.push().setValue(writeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Writing.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Writing.this, Community.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

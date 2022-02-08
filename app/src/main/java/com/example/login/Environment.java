package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Environment extends AppCompatActivity {
    private Button btn_write;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<WriteInfo> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.environment);
        btn_write=findViewById(R.id.write);
        firebaseAuth = FirebaseAuth.getInstance();
        envi_list();
        if(firebaseAuth.getUid().equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            btn_write.setVisibility(View.VISIBLE);
        }
        else{
            btn_write.setVisibility(View.INVISIBLE);
        }
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envi_write();
            }
        });
    }

    private void envi_list(){
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("envi");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    WriteInfo Writeinfo = snapshot.getValue(WriteInfo.class);
                    arrayList.add(Writeinfo);    //데이터 추가
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        adapter = new WritingAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }

    private void envi_write(){
        Intent intent =new Intent(Environment.this, Writing.class);
        startActivity(intent);
    }
}

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

public class Separate extends AppCompatActivity {
    private Button btn_write;
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WriteInfo> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.separate);
        btn_write=findViewById(R.id.write);
        firebaseAuth = firebaseAuth.getInstance();
        String uid=firebaseAuth.getUid();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        sepa_list();
        if(firebaseAuth.getUid().equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            btn_write.setVisibility(View.VISIBLE);
        }
        else{
            btn_write.setVisibility(View.INVISIBLE);
        }
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sepa_write();
            }
        });
    }

    private void sepa_list(){
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sepa");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    WriteInfo writeInfo = snapshot.getValue(WriteInfo.class);
                    arrayList.add(writeInfo);
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

    private void sepa_write(){
        Intent intent = new Intent(Separate.this, Writing.class);
        startActivity(intent);
    }
}

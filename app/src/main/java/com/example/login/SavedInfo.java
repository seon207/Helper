package com.example.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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

public class SavedInfo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WriteInfo> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        recyclerView=findViewById(R.id.recyclerView);
        text=findViewById(R.id.text);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        infolist();
    }

    void infolist(){
        recyclerView.setLayoutManager(layoutManager);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(uid).child("save");
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
}

package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Community extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WriteInfo> arrayList;
    private Button btn_info, btn_community, btn_page, btn_write, btn_environ, btn_separate;
    private FrameLayout frame;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btn_info=findViewById(R.id.btn_info);
        btn_community=findViewById(R.id.btn_community);
        btn_page=findViewById(R.id.btn_page);
        btn_write=findViewById(R.id.write);
        firebaseAuth = FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        frame = findViewById(R.id.frame);
        btn_environ=findViewById(R.id.btn_environ);
        btn_separate=findViewById(R.id.btn_separate);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();


        if(uid.equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            btn_write.setVisibility(View.INVISIBLE);
        }else{
            btn_write.setVisibility(View.VISIBLE);
        }
        btn_environ.setVisibility(View.INVISIBLE);
        btn_separate.setVisibility(View.INVISIBLE);
        frame.setVisibility(View.VISIBLE);
        get_list();
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //정보 눌렀을 때
                go_info();
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_comu();
                if(uid.equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
                    btn_write.setVisibility(View.INVISIBLE);
                }else{
                    btn_write.setVisibility(View.VISIBLE);
                }
                btn_environ.setVisibility(View.INVISIBLE);
                btn_separate.setVisibility(View.INVISIBLE);
                frame.setVisibility(View.VISIBLE);
                get_list();
            }
        });

        btn_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_page(uid);
            }
        });
        btn_write.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                comu_write();
            }
        }
        );
    }

    private void go_info(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment information = new Fragment();
        transaction.replace(R.id.frame,information);
        transaction.commit();
        btn_write.setVisibility(View.INVISIBLE);
        btn_environ.setVisibility(View.VISIBLE);
        btn_separate.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        frame.setVisibility(View.INVISIBLE);
        btn_environ.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Community.this, Environment.class);
                startActivity(intent);
            }
        });

        btn_separate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community.this, Separate.class);
                startActivity(intent);
            }
        });
    }

    private void go_comu(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment community = new Fragment();
        transaction.replace(R.id.frame,community);
        transaction.detach(community).attach(community).commit();
        btn_write.setVisibility(View.VISIBLE);
        btn_environ.setVisibility(View.INVISIBLE);
        btn_separate.setVisibility(View.INVISIBLE);
        frame.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community.this, Writing.class);
                startActivity(intent);
            }
        });
    }

    private void comu_write(){
        Intent intent =new Intent(Community.this, Writing.class);
        intent.putExtra("uid",firebaseAuth.getCurrentUser().getUid());
        startActivity(intent);
    }

    private void go_page(String uid){
        Intent intent;
        if(uid.equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            intent = new Intent(Community.this, ManagerMypage.class);
        }
        else {
            intent = new Intent(Community.this, UserMypage.class);
        }
        startActivity(intent);
    }
    private void get_list(){
        arrayList.clear();
        databaseReference = database.getReference("comu");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    WriteInfo Writeinfo = snapshot.getValue(WriteInfo.class);
                    arrayList.add(Writeinfo);    //데이터 추가
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //오류 발생 시
            }
        });
        adapter = new WritingAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }
}




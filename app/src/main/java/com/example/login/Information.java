package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Information extends AppCompatActivity {
    private String title,content,writer,board,number;
    private ImageView heart, delete;
    private TextView ctitle, ccontent, cwriter;
    FirebaseDatabase firebasedatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference mbase;
    FirebaseUser firebaseUser;
    private WritingAdapter adapter;
    private String name;
    public int position = -1;
    ArrayList<WriteInfo> arrayList = new ArrayList<WriteInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebasedatabase = FirebaseDatabase.getInstance();  //파이어베이스의 데이터베이스
        String uid = firebaseUser.getUid();
        ctitle=findViewById(R.id.ctitle);
        ccontent=findViewById(R.id.ccontent);
        cwriter = findViewById(R.id.cwriter);
        heart = findViewById(R.id.heart);
        delete = findViewById(R.id.delete);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        writer = intent.getStringExtra("writer");
        board = intent.getStringExtra("board");
        ctitle.setText(title);
        ccontent.setText(content);
        cwriter.setText("작성자 : " + writer);
        if(firebaseAuth.getUid().equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")){
            delete.setVisibility(View.VISIBLE);
            heart.setVisibility(View.INVISIBLE);
        }else{
            delete.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
        }
        get_list();
        databaseReference = firebasedatabase.getReference().child("user"); //db테이블 연결
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                name = Objects.requireNonNull(datasnapshot.getValue(User.class)).getUsername();

                if(firebaseAuth.getUid().equals("AzyAtxlDCUZ4a5JR5wLXZOJa1mq2")) {
                    delete.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.INVISIBLE);
                }else if(name.equals(writer)){
                        delete.setVisibility(View.VISIBLE);
                        heart.setVisibility(View.VISIBLE);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //오류 발생 시
            }
        });
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                press_heart(uid);
        };
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                press_delete(uid, title, content, writer, board);
            }
        });

    }

    private void press_heart(String uid){
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("확인").setMessage("정보를 저장할까요?").setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = firebasedatabase.getReference("user").child(uid).child("save");
                WriteInfo writeInfo = new WriteInfo(title, content, name, "save",uid);
                databaseReference.push().setValue(writeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Information.this, "정보를 저장합니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create().show();
    }

    private void press_delete(String uid, String title, String content, String writer, String board){

        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("확인").setMessage("해당 글을 삭제하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = firebasedatabase.getReference(board);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            WriteInfo writeInfo = dataSnapshot.getValue(WriteInfo.class);
                            position++;
                            if(writeInfo.getContent().equals(content)&&writeInfo.getTitle().equals(title)&&writeInfo.getUsername().equals(writer)){
                                String key = dataSnapshot.getKey();
                                mbase=firebasedatabase.getReference(board);
                                mbase.child(key).setValue(null);
                                arrayList.remove(position);
                                adapter.update(arrayList);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                        Toast.makeText(Information.this, "게시글을 삭제합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Information.this, Community.class);
                        startActivity(intent);
                    }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create().show();
    }

    private void get_list(){
        databaseReference = firebasedatabase.getReference(board);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    WriteInfo Writeinfo = snapshot.getValue(WriteInfo.class);
                    arrayList.add(Writeinfo);    //데이터 추가
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //오류 발생 시
            }
        });
        adapter = new WritingAdapter(arrayList,this);
    }
}

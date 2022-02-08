package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.widget.Toast.makeText;


public class Signup extends AppCompatActivity {
    private EditText user_email, user_passwd, user_passwdck, user_name;
    private Button btn_signup, btn_cancel, btn_idck;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebasedatabase;
    DatabaseReference databasereference;
    private Boolean check = false;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        firebaseAuth = FirebaseAuth.getInstance();
        user_email = (EditText) findViewById(R.id.user_email);
        user_passwd = (EditText) findViewById(R.id.user_passwd);
        user_passwdck = (EditText) findViewById(R.id.user_passwdck);
        user_name = (EditText) findViewById(R.id.user_name);
        btn_idck = (Button) findViewById(R.id.btn_idck);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        firebasedatabase = FirebaseDatabase.getInstance();  //파이어베이스의 데이터베이스
        btn_idck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasedatabase = FirebaseDatabase.getInstance();
                databasereference = firebasedatabase.getReference("user");
                databasereference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String email = user_email.getText().toString().trim();
                        checkemail(email);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
                });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paswd= user_passwd.getText().toString().trim();
                String pasck = user_passwdck.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String name = user_name.getText().toString().trim();
                signup(email,paswd,name,pasck);
            }});

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void signup(String email, String paswd, String name, String pasck){
        if(paswd.equals(pasck)){
            if(email.equals("")||paswd.equals("")||pasck.equals("")||name.equals("")){
                Toast.makeText(Signup.this,"모두 작성해주세요.",Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.createUserWithEmailAndPassword(email,paswd).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        user = firebaseAuth.getCurrentUser();
                        String uid = user.getUid();
                        User user = new User();
                        user.setUsername(name);
                        user.setId(email);
                        user.setPassword(paswd);
                        firebasedatabase = FirebaseDatabase.getInstance();
                        firebasedatabase.getReference("user").child(uid).setValue(user);
                        makeText(Signup.this,user.getUsername() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Signup.this, Login.class);
                        startActivity(intent);
                    }else{
                        if(user_passwd.length()<6){
                            makeText(Signup.this,"비밀번호는 6자 이상이어야 합니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!check){
                            Toast.makeText(Signup.this,"이메일 중복확인이 필요합니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });
        }else{
            Toast.makeText(Signup.this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void checkemail(String email){  //이메일 중복 체크
        databasereference = firebasedatabase.getReference("user");
        databasereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    arrayList.add(user.getId());
                }
                if(arrayList.contains(email)){
                    makeText(Signup.this, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    makeText(Signup.this, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                    check=true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //오류 발생 시
            }
        });
    }

}

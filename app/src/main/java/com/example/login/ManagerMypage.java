package com.example.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerMypage extends AppCompatActivity {

    private Button btn_check, btn_logout;
    FirebaseAuth firebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        btn_check=findViewById(R.id.btn_check);
        btn_logout=findViewById(R.id.btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usercheck();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             logout();
            }
        });
    }

   private void usercheck(){
        Intent intent =  new Intent(ManagerMypage.this, UserCheck.class);
        startActivity(intent);
    }

    private void logout(){
        firebaseAuth.signOut();
        Toast.makeText(ManagerMypage.this, "로그아웃합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ManagerMypage.this, Login.class);
        startActivity(intent);
    }
}
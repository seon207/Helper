package com.example.login;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class UserMypage extends AppCompatActivity {

    private Button btn_favorite, btn_logout;
    FirebaseAuth firebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermypage);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        btn_favorite= (Button)findViewById(R.id.favorite);
        btn_logout=(Button)findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedinfo();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_logout();
            }
        });
    }

     private void savedinfo(){
        Intent intent = new Intent(UserMypage.this, SavedInfo.class);
        startActivity(intent);
    }
    private void user_logout(){
        firebaseAuth.signOut();
        Toast.makeText(UserMypage.this, "로그아웃합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserMypage.this, Login.class);
        startActivity(intent);
    }
}
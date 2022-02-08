package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText user_email, user_passwd;
    private Button btn_login, btn_signup;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        user_email= findViewById(R.id.user_email);
        user_passwd = findViewById(R.id.user_passwd);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){   //로그인 확인
        String email = user_email.getText().toString().trim();
        String pwd = user_passwd.getText().toString().trim();
        if (email.equals("") || pwd.equals("")) {
            Toast.makeText(Login.this, "모두 작성해주세요", Toast.LENGTH_SHORT).show();
        } else{
            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(email.equals("admin77@gmail.com")){
                            Toast.makeText(Login.this,"관리자입니다",Toast.LENGTH_SHORT).show();
                        }
                        // FirebaseUser user=firebaseAuth.getCurrentUser();
                        Toast.makeText(Login.this, "환영합니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Community.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "이메일이나 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
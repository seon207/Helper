package com.example.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class Startscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Handler hd = new Handler(Looper.getMainLooper());
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
protected void onPause(){
        super.onPause();
        finish();
}
}
package com.akramlebcir.mac.drivinglicense.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akramlebcir.mac.drivinglicense.R;
import com.google.firebase.auth.FirebaseAuth;

public class splashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(splashActivity.this, MainActivity.class));
                    finish();
                }
                Intent homeIntent = new Intent(splashActivity.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}

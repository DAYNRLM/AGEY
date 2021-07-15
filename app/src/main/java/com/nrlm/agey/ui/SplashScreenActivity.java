package com.nrlm.agey.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.nrlm.agey.MainActivity;
import com.nrlm.agey.R;
import com.nrlm.agey.ui.login.AuthActivity;
import com.nrlm.agey.ui.mpin.MpinActivity;
import com.nrlm.agey.utils.AppSharedPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=4000;

    AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        appSharedPreferences =AppSharedPreferences.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                goToNextScreen();

            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

    private void goToNextScreen() {
        String loginStatus ="";
        loginStatus = appSharedPreferences.getLoginStatus();

        if(loginStatus.isEmpty()){
            Intent i = new Intent(SplashScreenActivity.this, AuthActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(SplashScreenActivity.this, MpinActivity.class);
            startActivity(i);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        }



    }
}
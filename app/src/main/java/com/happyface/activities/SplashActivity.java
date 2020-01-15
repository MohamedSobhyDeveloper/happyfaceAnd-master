package com.happyface.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.happyface.R;
import com.happyface.baseactivity.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            new Thread() {
                public void run() {

                    try {
                        sleep(SPLASH_TIME_OUT);
                        Intent i;
                       /* if (FirebaseAuth.getInstance().getUid() == null) {
                            i = new Intent(getBaseContext(), SignInActivity.class);
                        } else */
                        {
                            i = new Intent(getBaseContext(), MainActivity.class);
                        }
                        startActivity(i);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

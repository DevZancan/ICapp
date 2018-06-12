/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 11.17
 */

package infocamere.it.icapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import infocamere.it.icapp.R;
import infocamere.it.icapp.home.HomeActivity;
import infocamere.it.icapp.unlocker.UnlockerActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        Intent intent = new Intent(getApplicationContext(),
                UnlockerActivity.class);
                */
        Intent intent = new Intent(getApplicationContext(),
                HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
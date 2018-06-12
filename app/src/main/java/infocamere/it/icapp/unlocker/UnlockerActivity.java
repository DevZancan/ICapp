/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 11.17
 */

package infocamere.it.icapp.unlocker;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import infocamere.it.icapp.R;
import infocamere.it.icapp.home.HomeActivity;
import infocamere.it.icapp.util.UnlockerSettings;

public class UnlockerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlocker);

        AssetManager am = getAssets();
        InputStream is = null;
        try {
            is = am.open("ic_logo_app.png");
        } catch(IOException e) {
            e.printStackTrace();
        }

        ((ImageView)findViewById(R.id.logo_img)).setImageBitmap(BitmapFactory.decodeStream(is));

        findViewById(R.id.unlock_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editText = findViewById(R.id.code_unlocker);
                String code = editText.getText().toString();
                if (UnlockerSettings.tryUnlock(UnlockerActivity.this, code)){
                    startMainActivity();
                }
                else {
                    Toast.makeText(
                            UnlockerActivity.this,
                            "Codice errato",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void startMainActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}

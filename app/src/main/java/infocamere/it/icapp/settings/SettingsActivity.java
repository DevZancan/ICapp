package infocamere.it.icapp.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import infocamere.it.icapp.R;
import infocamere.it.icapp.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button account, logout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        account = findViewById(R.id.account);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent vIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(vIntent);
            }
        });
    }
}

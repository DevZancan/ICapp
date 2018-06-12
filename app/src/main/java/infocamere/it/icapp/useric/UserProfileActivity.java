package infocamere.it.icapp.useric;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import infocamere.it.icapp.R;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int i = R.attr.actionBarSize;

        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView nameSurname = (TextView) findViewById(R.id.txt_nameSurnameUserIC);
        TextView email = (TextView) findViewById(R.id.txt_emailUserIC);
        TextView phoneMobile = (TextView) findViewById(R.id.txtPhoneMobile);
        TextView phoneFix = (TextView) findViewById(R.id.txtPhoneFix);
        TextView cdr = (TextView) findViewById(R.id.txtCdr);
        TextView matricola = (TextView) findViewById(R.id.txtMatricola);
        TextView office = (TextView) findViewById(R.id.txtOffice);
        TextView sede = (TextView) findViewById(R.id.txtSede);
        JSONObject json = null;
        String data = getIntent().getStringExtra("jsonData");
        String call = getIntent().getStringExtra("doCall");

        try {
            json = new JSONObject(data);
            nameSurname.setText(json.getString("name") + " " +
                    json.getString("surname"));
            email.setText(json.getString("email"));
            phoneFix.setText(json.getString("phone_fix"));
            phoneMobile.setText(json.getString("phone_mobile"));
            cdr.setText(json.getString("cdr"));
            matricola.setText(json.getString("matricola"));
            office.setText(json.getString("office"));
            sede.setText(json.getString("sede"));

            if (call != null && call.equals("true")) {
                btnCallPhone(null);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        String titleCollapse = "";

        try {
            titleCollapse =  json.getString("name") + " " +
                    json.getString("surname");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        final String finalTitleCollapse = titleCollapse;

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(finalTitleCollapse);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    public void btnCallPhone(View view) {
        Log.i("CALL", "PHONE");
        TextView t = (TextView) findViewById(R.id.txtPhoneFix);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(
                "tel:" + "0498288" + t.getText().toString().substring(1)));
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {
            Log.i("CALL", "PHONE OK");
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

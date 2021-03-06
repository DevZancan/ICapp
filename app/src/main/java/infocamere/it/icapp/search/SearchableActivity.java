package infocamere.it.icapp.search;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import infocamere.it.icapp.R;
import infocamere.it.icapp.model.UserIC;
import infocamere.it.icapp.settings.SettingsActivity;
import infocamere.it.icapp.sipert.SipertTabActivity;
import infocamere.it.icapp.useric.UserProfileActivity;
import infocamere.it.icapp.util.GeneralUtils;
import infocamere.it.icapp.util.adapter.CustomAdapter;
import infocamere.it.icapp.util.db.UserICRepo;

public class SearchableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    UserIC useric = null;
    UserICRepo userICRepo;
    private final static String TAG = SearchableActivity.class.getName().toString();
    private Toolbar toolbar;
    private DrawerLayout drawer;

    String name;
    String email;
    String surname;
    String cdr;
    String matricola;
    String office;
    String sede;
    String phoneFix;
    String phoneMobile;
    String id;

    GeneralUtils generalUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        userICRepo = new UserICRepo(this);
        cursor = userICRepo.getUsersList();
        customAdapter = new CustomAdapter(SearchableActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.lstUser);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView.setAdapter(customAdapter);
        setSupportActionBar(toolbar);

        Log.i("SEARCHABLE", "ONCREATE");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Cursor mycursor = (Cursor) adapter.getItemAtPosition(position);
                // selected item

                Log.i("Cursor Position: ", String.valueOf(cursor.getPosition()));

                name = ((TextView) v.findViewById(R.id.txtName)).getText().toString();
                surname = ((TextView) v.findViewById(R.id.txtSurname)).getText().toString();
                id = ((TextView) v.findViewById(R.id.txtId)).getText().toString();
                email = ((TextView) v.findViewById(R.id.txtEmail)).getText().toString();
                cdr = ((TextView) v.findViewById(R.id.txtCdr)).getText().toString();
                matricola = ((TextView) v.findViewById(R.id.txtMatricola)).getText().toString();
                office = ((TextView) v.findViewById(R.id.txtOffice)).getText().toString();
                sede = ((TextView) v.findViewById(R.id.txtSede)).getText().toString();
                phoneFix = ((TextView) v.findViewById(R.id.txtPhoneFix)).getText().toString();
                phoneMobile = ((TextView) v.findViewById(R.id.txtPhoneMobile)).getText().toString();

                Intent profile = new Intent(getApplicationContext(), UserProfileActivity.class);

                useric = new UserIC();

                useric.setUserIC_id(Integer.valueOf(id));
                useric.setName(name);
                useric.setSurname(surname);
                useric.setEmail(email);
                useric.setSede(sede);
                useric.setOffice(office);
                useric.setPhone_mobile(phoneMobile);
                useric.setPhone_fix(phoneFix);
                useric.setMatricola(matricola);
                useric.setCdr(cdr);

                try {
                    profile.putExtra("jsonData", useric.toJSON().toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(profile);
            }
        });

        Log.i("SearchableActivity", "onCreate");
        handleIntent(getIntent());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
        Log.i("SearchableActivity", "onNewIntent");
    }

    private void handleIntent(Intent intent) {
        Log.i("SEARCHABLE", "handleIntent");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("SearchableActivity", "handleIntent --> " + query);

            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        Log.i("doMySearch", "ciao");

        this.generalUtils = new GeneralUtils();
        TypicalSearch typicalSearch = this.generalUtils.getTypicalSearch(query);

        if (typicalSearch.isDoCall()) {
            Log.i("Search result", "Chiamare");
            cursor = userICRepo.getUserListByKeyword(typicalSearch.getQuery());
            if (cursor != null && cursor.getCount() == 1) {
                String i = cursor.getString(0);
                int index = Integer.valueOf(i);
                index = index - 1;
                Log.i("Search result", "Mostrare unico risultato " + index);
                View v = listView.getAdapter().getView(
                        index, null, null);
                openProfileSingleResult(v, true);
            }
        }
        else {
            cursor = userICRepo.getUserListByKeyword(query);
            if (cursor != null && cursor.getCount() == 1) {
                String i = cursor.getString(0);
                int index = Integer.valueOf(i);
                index = index - 1;
                Log.i("Search result", "Mostrare unico risultato " + index);
                View v = listView.getAdapter().getView(
                        index, null, null);
                openProfileSingleResult(v, false);
            }
        }

        if (cursor == null) {
            Toast.makeText(
                    SearchableActivity.this,"No records found!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    SearchableActivity.this,
                    cursor.getCount() + " records found!",
                    Toast.LENGTH_LONG).show();
        }

        customAdapter.swapCursor(cursor);
    }

    public void openProfileSingleResult(View v, boolean call) {

        name = ((TextView) v.findViewById(R.id.txtName)).getText().toString();
        surname = ((TextView) v.findViewById(R.id.txtSurname)).getText().toString();
        id = ((TextView) v.findViewById(R.id.txtId)).getText().toString();
        email = ((TextView) v.findViewById(R.id.txtEmail)).getText().toString();
        cdr = ((TextView) v.findViewById(R.id.txtCdr)).getText().toString();
        matricola = ((TextView) v.findViewById(R.id.txtMatricola)).getText().toString();
        office = ((TextView) v.findViewById(R.id.txtOffice)).getText().toString();
        sede = ((TextView) v.findViewById(R.id.txtSede)).getText().toString();
        phoneFix = ((TextView) v.findViewById(R.id.txtPhoneFix)).getText().toString();
        phoneMobile = ((TextView) v.findViewById(R.id.txtPhoneMobile)).getText().toString();

        Intent profile = new Intent(getApplicationContext(), UserProfileActivity.class);

        useric = new UserIC();

        useric.setUserIC_id(Integer.valueOf(id));
        useric.setName(name);
        useric.setSurname(surname);
        useric.setEmail(email);
        useric.setSede(sede);
        useric.setOffice(office);
        useric.setPhone_mobile(phoneMobile);
        useric.setPhone_fix(phoneFix);
        useric.setMatricola(matricola);
        useric.setCdr(cdr);

        try {
            profile.putExtra("jsonData", useric.toJSON().toString());
            if (call) {
                profile.putExtra("doCall", "true");
            }
            else {
                profile.putExtra("doCall", "false");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_nav_main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
            Intent vIntent = new Intent(SearchableActivity.this, SettingsActivity.class);
            startActivity(vIntent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }
}
/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 11.17
 */

package infocamere.it.icapp.home;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import android.view.WindowManager;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infocamere.it.icapp.R;
import infocamere.it.icapp.model.ItemUI;
import infocamere.it.icapp.model.ServiceIC;
import infocamere.it.icapp.model.UserIC;
import infocamere.it.icapp.settings.SettingsActivity;
import infocamere.it.icapp.util.ItemTouchHelperCallback;
import infocamere.it.icapp.util.adapter.OnStartDragListener;
import infocamere.it.icapp.util.adapter.RVAdapter;
import infocamere.it.icapp.util.db.ServiceICRepo;
import infocamere.it.icapp.util.db.UserICRepo;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnStartDragListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Cursor cursor;
    private UserICRepo userICRepo;
    private Cursor cursorSvcIC;
    private ServiceICRepo svcICRepo;
    private final static String TAG = HomeActivity.class.getName().toString();
    private ItemTouchHelper mItemTouchHelper;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        toolbar.setTitle("IC APP");
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        Gestione inserimento utenti
         */
        userICRepo = new UserICRepo(this);
        cursor = userICRepo.getUsersList();
        if(cursor == null) {
            insertDummy();
        }

        /*
        Gestione inserimento Servici IC
         */
        svcICRepo = new ServiceICRepo(this);
        cursorSvcIC = svcICRepo.getServiceICList();
        if (cursorSvcIC == null) {
            insertServicesIC();
        }

        /*
        Genero il token dell'app
         */
        try {
            InputStream is=getResources().openRawResource(R.raw.service_fcm);
            File tempfile=File.createTempFile("tempfile",".json",getDir("filez",0));

            FileOutputStream os=new FileOutputStream(tempfile);
            byte[] buffer=new byte[16000];
            int length=0;
            while((length=is.read(buffer))!=-1){
                os.write(buffer,0,length);
            }
            FileInputStream fis =new FileInputStream(tempfile);
            Log.d("oauth", "token app " + getAccessToken(fis));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Firebase", "token device "+ FirebaseInstanceId.getInstance().getToken());

        /*
        Inizia la costruzione del contenuto della pagina
        */
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adapter = new RVAdapter(generateItemUi(), this);
        adapter = new RVAdapter(HomeActivity.this, generateItemUiPref(), this);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);

        //new RendererServicesTask(mRecyclerView, mLayoutManager, this).execute();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // mLayoutManager = new LinearLayoutManager(this);
        // mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        // RVAdapter adapter = new RVAdapter(generateItemUi());
        //
        // mRecyclerView.setAdapter(adapter);
    }

    private static String getAccessToken(FileInputStream fis) throws IOException {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GoogleCredential googleCredential = GoogleCredential
                .fromStream(fis)
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    private void insertDummy(){
        UserIC user = new UserIC();

        user.yyi = "yyi4216";
        user.email = "luca.visentin@infocamere.it";
        user.name = "Luca";
        user.surname = "Visentin";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setMatricola("2019");
        user.setOffice("PD-423");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0866";
        user.email = "marina.angelini@infocamere.it";
        user.name = "Marina";
        user.surname = "Angelini";
        user.phone_fix = "8639";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0791";
        user.email = "marzia.menegazzo@infocamere.it";
        user.name = "Marzia";
        user.surname = "Menegazzo";
        user.phone_fix = "8064";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi1159";
        user.email = "andrea.gabana@infocamere.it";
        user.name = "Andrea";
        user.surname = "Gabana";
        user.phone_fix = "8538";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("59100");
        user.setOffice("PD-414 open");
        user.setMatricola("0623");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0192";
        user.email = "giovanni.secchiati@infocamere.it";
        user.name = "Giovanni";
        user.surname = "Secchiati";
        user.phone_fix = "8641";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setMatricola("2019");
        user.setOffice("PD-423");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0939";
        user.email = "simonetta.finoli@infocamere.it";
        user.name = "Simonetta";
        user.surname = "Finoli";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi1152";
        user.email = "cristiana.trolio@infocamere.it";
        user.name = "Cristiana";
        user.surname = "Trolio";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setMatricola("2019");
        user.setOffice("PD-423");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0869";
        user.email = "stefano.ferrari@infocamere.it";
        user.name = "Stefano";
        user.surname = "Ferrari";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0785";
        user.email = "stefano.catullo@infocamere.it";
        user.name = "Stefano";
        user.surname = "Catullo";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setOffice("PD-423");
        user.setCdr("20040");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi2096";
        user.email = "carmine.quitadamo@infocamere.it";
        user.name = "Carmine";
        user.surname = "Quitadamo";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi1063";
        user.email = "angela.costantino@infocamere.it";
        user.name = "Angela";
        user.surname = "Costantino";
        user.phone_fix = "5633";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi4408";
        user.email = "donato.altamura@infocamere.it";
        user.name = "Donato";
        user.surname = "Altamura";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setOffice("PD-423");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0080";
        user.email = "antonio.ometto@infocamere.it";
        user.name = "Antonio";
        user.surname = "Ometto";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setOffice("PD-423");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0567";
        user.email = "marina.tommasi@infocamere.it";
        user.name = "Marina";
        user.surname = "Tommasi";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setOffice("PD-423");
        user.setCdr("20040");
        user.setMatricola("2019");
        userICRepo.insert(user);

        userICRepo = new UserICRepo(this);

        user.yyi = "yyi0675";
        user.email = "attilio.sfragara@infocamere.it";
        user.name = "Attilio";
        user.surname = "Sfragara";
        user.phone_fix = "8182";
        user.setPhone_mobile("3401594597");
        user.setSede("Padova");
        user.setCdr("20040");
        user.setOffice("PD-423");
        user.setMatricola("2019");
        userICRepo.insert(user);
    }

    private void insertServicesIC() {
        ServiceIC svcIC = new ServiceIC();
        svcIC.setSvcId("1");
        svcIC.setSvcPref(0);
        svcIC.setSvcName("Presenze");
        svcIC.setSvcVisible(true);
        svcICRepo.insert(svcIC);

        svcIC = new ServiceIC();
        svcIC.setSvcId("2");
        svcIC.setSvcPref(1);
        svcIC.setSvcName("Trasferte");
        svcIC.setSvcVisible(true);
        svcICRepo.insert(svcIC);

        svcIC = new ServiceIC();
        svcIC.setSvcId("3");
        svcIC.setSvcPref(2);
        svcIC.setSvcName("News");
        svcIC.setSvcVisible(true);
        svcICRepo.insert(svcIC);

        svcIC = new ServiceIC();
        svcIC.setSvcId("4");
        svcIC.setSvcPref(3);
        svcIC.setSvcName("Uscita");
        svcIC.setSvcVisible(true);
        svcICRepo.insert(svcIC);
    }

    private List<ItemUI> generateItemUiPref() {

        Cursor c = svcICRepo.getServiceICFromPref();
        ServiceIC svc = new ServiceIC();
        List<ItemUI> itemUIModelList = new ArrayList<>();
        ItemUI itemUI = null;

        if (c.moveToFirst()) {
            do {
                svc.setSvcId(c.getString(c.getColumnIndex(ServiceIC.KEY_ID)));
                svc.setSvcPref(c.getInt(c.getColumnIndex(ServiceIC.KEY_PREFID)));
                svc.setSvcName(c.getString(c.getColumnIndex(ServiceIC.KEY_serviceName)));
                svc.setSvcVisible(
                        c.getInt(c.getColumnIndex(ServiceIC.KEY_serviceVisible)) > 0);

                if (svc.getSvcName().contains("Presenze")) {
                    Log.i("GENERATE", "PRESENZE");
                    itemUI = new ItemUI("#393185", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }
                else if (svc.getSvcName().contains("Trasferte")) {
                    Log.i("GENERATE", "TRASFERTE");
                    itemUI = new ItemUI("#E83E00", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci le tue trasferte", "trasferte");
                }
                else {
                    Log.i("GENERATE", "ALTRO");
                    itemUI = new ItemUI("#3F9CDC", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }

                itemUIModelList.add(itemUI);
            } while (c.moveToNext());
        }

        /*
        itemUI = new ItemUI("#393185", "item_1", "Il mio profilo",
                "Presenze", "Gestisci il tuo foglio presenze. Tocca per vedere " +
                "timbrature, anomalie, salidi e giustificativi.", "tempo2");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#E83E00", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#496A8D", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#3F9CDC", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);
        */

        return itemUIModelList;
    }

    private List<ItemUI> generateItemUi() {

        Cursor c = svcICRepo.getServiceICList();
        ServiceIC svc = new ServiceIC();
        List<ItemUI> itemUIModelList = new ArrayList<>();
        ItemUI itemUI = null;

        if (c.moveToFirst()) {
            do {
                svc.setSvcId(c.getString(c.getColumnIndex(ServiceIC.KEY_ID)));
                svc.setSvcName(c.getString(c.getColumnIndex(ServiceIC.KEY_serviceName)));
                svc.setSvcVisible(
                        c.getInt(c.getColumnIndex(ServiceIC.KEY_serviceVisible)) > 0);

                if (svc.getSvcName().contains("Presenze")) {
                    Log.i("GENERATE", "PRESENZE");
                    itemUI = new ItemUI("#393185", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }
                else if (svc.getSvcName().contains("Trasferte")) {
                    Log.i("GENERATE", "TRASFERTE");
                    itemUI = new ItemUI("#E83E00", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci le tue trasferte", "trasferte");
                }
                else {
                    Log.i("GENERATE", "ALTRO");
                    itemUI = new ItemUI("#3F9CDC", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }

                itemUIModelList.add(itemUI);
            } while (c.moveToNext());
        }

        /*
        itemUI = new ItemUI("#393185", "item_1", "Il mio profilo",
                "Presenze", "Gestisci il tuo foglio presenze. Tocca per vedere " +
                "timbrature, anomalie, salidi e giustificativi.", "tempo2");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#E83E00", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#496A8D", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);

        itemUI = new ItemUI("#3F9CDC", "item_2", "Il mio profilo",
                "Trasferte", "", "trasferte");

        itemUIModelList.add(itemUI);
        */

        return itemUIModelList;
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
            Intent vIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(vIntent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (mItemTouchHelper!=null)
            mItemTouchHelper.startDrag(viewHolder);
    }
}

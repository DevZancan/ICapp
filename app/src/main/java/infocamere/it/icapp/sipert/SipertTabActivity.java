/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 31/05/18 15.05
 */

package infocamere.it.icapp.sipert;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import infocamere.it.icapp.AppBaseActivity;
import infocamere.it.icapp.R;
import infocamere.it.icapp.home.HomeActivity;
import infocamere.it.icapp.model.presenze.Saldi;
import infocamere.it.icapp.model.presenze.Timbratura;
import infocamere.it.icapp.model.presenze.Timbrature;
import infocamere.it.icapp.settings.SettingsActivity;
import infocamere.it.icapp.util.GeneralUtils;

import static infocamere.it.icapp.R.color.redic;

public class SipertTabActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sipert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        setTitle("IC APP");

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        callSipertSaldi();
        // callSipertTimbrature("today");
        callSipertTimbrature("20180608");
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
            Intent vIntent = new Intent(SipertTabActivity.this, SettingsActivity.class);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        View rootView;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Log.i("Sipert", "On create view");

            // View rootView = null;
            TextView tv_saldoFerie;

            if (this.rootView == null) {
                if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                    rootView = inflater.inflate(
                            R.layout.fragment_sipert_saldi, container, false);
                }
                else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                    rootView = inflater.inflate(
                            R.layout.fragment_sipert_timbrature, container, false);
                }
                else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                    rootView = inflater.inflate(
                            R.layout.fragment_sipert_anomalie, container, false);
                }
            }
            else {
                Log.i("Fragment tabbed", "Not create another view");
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void detailFerie_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailFerieCV = (CardView) findViewById(R.id.detail_ferie_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText("Dettagli");
            detailFerieCV.setVisibility(View.GONE);
        }
        else {
            detail.setText("Nascondi");
            detailFerieCV.setVisibility(View.VISIBLE);
        }
    }

    public void detailPar_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailParCV = (CardView) findViewById(R.id.detail_par_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText(R.string.detail_txt);
            detailParCV.setVisibility(View.GONE);
        }
        else {
            detail.setText(R.string.hide_txt);
            detailParCV.setVisibility(View.VISIBLE);
        }
    }

    public void detailBancaore_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailBancaoreCV = (CardView) findViewById(R.id.detail_bancaore_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText(R.string.detail_txt);
            detailBancaoreCV.setVisibility(View.GONE);
        }
        else {
            detail.setText(R.string.hide_txt);
            detailBancaoreCV.setVisibility(View.VISIBLE);
        }
    }

    public void callSipertSaldi() {
        Log.i("REQUEST", "START");

        String url = "https://aru10.intra.infocamere.it/wssip/saldi/YYI4216";

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject request = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject sipertResponse) {
                ArrayList<Saldi> saldi = null;
                try {
                    saldi = SipertResponseFactory.buildSaldi(sipertResponse);
                    setValueForSaldi(saldi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // response
                Log.d("Response", sipertResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("ERROR","error => "+error.toString());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("wssipapikey", "1qaz2wsx");
                params.put("Content-type", "application/json");

                return params;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
        Log.i("Saldi", "Added to queue!");


    }

    public void callSipertTimbrature(String day) {
        Log.i("REQUEST", "START call sipert " + day);
        String today;
        String url;

        if (day.equals("today")) {
            today = GeneralUtils.getTodayForRequest();
            url = "https://aru10.intra.infocamere.it/wssip/timb/YYI4216/" + today;
            Log.i("callSipertTimbrature", url);
        }
        else {
            url = "https://aru10.intra.infocamere.it/wssip/timb/YYI4216/" + day;
            Log.i("callSipertTimbrature", url);
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject request = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject sipertResponse) {

                ArrayList<Timbratura> timb = null;
                try {
                    timb = SipertResponseFactory.buildTimbrature(sipertResponse);
                    if (timb != null) {
                        setValueForTimbrature(timb, false);
                    }
                    else {
                        setValueForTimbrature(
                                timb, SipertResponseFactory.getGogone(sipertResponse));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // response
                Log.i("Response", sipertResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("ERROR","error => "+error.toString());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("wssipapikey", "1qaz2wsx");
                params.put("Content-type", "application/json");

                return params;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
        Log.i("Saldi", "Added to queue!");

    }

    public void setValueForSaldi(ArrayList<Saldi> saldi) {

        if (saldi != null) {
            Saldi s;
            for (int i=0; i<saldi.size(); i++) {
                s = saldi.get(i);
                if (s.getProgr() == 1) {
                   TextView ferie = (TextView) findViewById(R.id.saldo_ferie);
                   if (s.getSaldo().contains("-")) {
                       ferie.setTextColor(getResources().getColor(redic));
                   }
                   ferie.setText(s.getSaldo());
                   ferie = (TextView) findViewById(R.id.residuo_ferie);
                   ferie.setText(s.getResiduoAnnoPrec());
                   ferie = (TextView) findViewById(R.id.anno_corrente_ferie);
                   ferie.setText(s.getAnnoCorrente());
                   ferie = (TextView) findViewById(R.id.godute_corrente_ferie);
                   ferie.setText(s.getGoduteCorrente());
                }
                else if (saldi.get(i).getProgr() == 2) {
                    TextView par = (TextView) findViewById(R.id.saldo_par);
                    if (s.getSaldo().contains("-")) {
                        par.setTextColor(getResources().getColor(redic));
                    }
                    par.setText(s.getSaldo());
                    par = (TextView) findViewById(R.id.residuo_par);
                    par.setText(s.getResiduoAnnoPrec());
                    par = (TextView) findViewById(R.id.anno_corrente_par);
                    par.setText(s.getAnnoCorrente());
                    par = (TextView) findViewById(R.id.godute_corrente_par);
                    par.setText(s.getGoduteCorrente());

                }
                else if (saldi.get(i).getProgr() == 3) {
                    TextView banca = (TextView) findViewById(R.id.saldo_banca_ore);
                    banca.setText(s.getSaldo());
                    banca = (TextView) findViewById(R.id.residuo_banca_ore);
                    banca.setText(s.getResiduoAnnoPrec());
                    banca = (TextView) findViewById(R.id.anno_corrente_banca_ore);
                    banca.setText(s.getAnnoCorrente());
                    banca = (TextView) findViewById(R.id.godute_corrente_banca_ore);
                    banca.setText(s.getGoduteCorrente());

                }
            }
        }
    }


    public void setValueForTimbrature(ArrayList<Timbratura> timb, boolean gogone) {

        if (timb != null && timb.size() > 0) {
            CardView cv;

            // TIMB 1
            if (timb.get(0).getHour().equals("0:") || timb.get(0).getHour() == "" || gogone) {
                TextView timb1 = (TextView) findViewById(R.id.timb1_type);
                timb1.setText("");
                timb1 = (TextView) findViewById(R.id.timb1_h);
                timb1.setText("Nessuna timbratura oggi");
                timb1.setTextSize(20);
            }
            else {
                TextView timb1 = (TextView) findViewById(R.id.timb1_type);
                timb1.setText(timb.get(0).getType());
                timb1 = (TextView) findViewById(R.id.timb1_h);
                timb1.setText(timb.get(0).getHour());
            }

            // TIMB 2
            if (timb.get(1).getHour().equals("0:") || timb.get(1).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb2);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb2 = (TextView) findViewById(R.id.timb2_type);
                timb2.setText(timb.get(1).getType());
                timb2 = (TextView) findViewById(R.id.timb2_h);
                timb2.setText(timb.get(1).getHour());
            }

            // TIMB 3
            if (timb.get(2).getHour().equals("0:") || timb.get(2).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb3);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb3 = (TextView) findViewById(R.id.timb3_type);
                timb3.setText(timb.get(2).getType());
                timb3 = (TextView) findViewById(R.id.timb3_h);
                timb3.setText(timb.get(2).getHour());
            }

            // TIMB 4
            if (timb.get(3).getHour().equals("0:") || timb.get(3).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb4);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb4 = (TextView) findViewById(R.id.timb4_type);
                timb4.setText(timb.get(3).getType());
                timb4 = (TextView) findViewById(R.id.timb4_h);
                timb4.setText(timb.get(3).getHour());
            }

            // TIMB 5
            if (timb.get(4).getHour().equals("0:") || timb.get(4).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb5);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb5 = (TextView) findViewById(R.id.timb5_type);
                timb5.setText(timb.get(4).getType());
                timb5 = (TextView) findViewById(R.id.timb5_h);
                timb5.setText(timb.get(4).getHour());
            }

            // TIMB 6
            if (timb.get(5).getHour().equals("0:") || timb.get(5).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb6);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb6 = (TextView) findViewById(R.id.timb6_type);
                timb6.setText(timb.get(5).getType());
                timb6 = (TextView) findViewById(R.id.timb6_h);
                timb6.setText(timb.get(5).getHour());
            }

            // TIMB 7
            if (timb.get(6).getHour().equals("0:") || timb.get(6).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb7);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb7 = (TextView) findViewById(R.id.timb7_type);
                timb7.setText(timb.get(6).getType());
                timb7 = (TextView) findViewById(R.id.timb7_h);
                timb7.setText(timb.get(6).getHour());
            }

            // TIMB 8
            if (timb.get(7).getHour().equals("0:") || timb.get(7).getHour() == "" || gogone) {
                cv = (CardView) findViewById(R.id.cv_timb8);
                cv.setVisibility(View.GONE);
            }
            else {
                TextView timb8 = (TextView) findViewById(R.id.timb8_type);
                timb8.setText(timb.get(7).getType());
                timb8 = (TextView) findViewById(R.id.timb8_h);
                timb8.setText(timb.get(7).getHour());
            }
        }
    }
}
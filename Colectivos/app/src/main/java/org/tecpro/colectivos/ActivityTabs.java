package org.tecpro.colectivos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tecpro.colectivos.lineas.LineasFragment;
import org.tecpro.colectivos.mapa.MapsActivity;
import org.tecpro.colectivos.saldo.SaldoFragment;

/**
 * Created by nico on 13/03/16.
 */
public class ActivityTabs extends AppCompatActivity implements ViewPager.OnPageChangeListener,SearchView.OnQueryTextListener {
    ViewPager mViewPager;
    TabLayout tabs;
    SectionsPagerAdapter adapter;
    private SearchView sv;
    private MenuItem searchItem;

    private LineasFragment lineasFragment;
    private LineasFragment favsFragment;
    private SaldoFragment saldoFragment;
    //menu lateral
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private LinearLayout drawer;
    private FloatingActionButton btnHowArrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        loadNavigationDrawer();
        setToolbar(); // Añadir la toolbar
        setTabs();
        SharedPreferences prefs = getSharedPreferences("preferencias", MODE_PRIVATE);
        if(!prefs.getBoolean("showed",false))
            showAlert();
        btnHowArrive = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
        return true;
    }
    private void loadNavigationDrawer(){
        drawer = (LinearLayout) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Integracion boton oficial
        toggle = new ActionBarDrawerToggle(
                this, // Activity
                drawerLayout, // Panel del Navigation Drawer
                R.drawable.ic_menu, // Icono que va a utilizar
                R.string.contactate, // Descripcion al abrir el drawer
                R.string.app_name // Descripcion al cerrar el drawer
        ) {

            public void onDrawerClosed(View view) {
                // Drawer cerrado
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // Drawer abierto
                //since the drawer might have opened as a results of
                //a click on the left menu, we need to make sure
                //to close it right after the drawer opens, so that
                //it is closed when the drawer is  closed.
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(toggle);
    }

    private void setTabs() {
        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);
        // Preparar las pestañas
        tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_bus_white);
        tabs.getTabAt(1).setIcon(R.drawable.ic_heart_green);
        tabs.getTabAt(2).setIcon(R.drawable.ic_card_green);
        mViewPager.setOnPageChangeListener(this);
        //tabs.setTabMode(TabLayout.MODE_SCROLLABLE);// modo para scroll en la definicion de tabs
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

        }

    }

    /**
     * Crea una instancia del view pager con los datos
     * predeterminados
     *
     * @param viewPager Nueva instancia
     */
    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        lineasFragment = new LineasFragment();
        adapter.addFragment(lineasFragment, "LINEAS");
        favsFragment = new LineasFragment();
        favsFragment.setIsForFavorites(true);
        adapter.addFragment(favsFragment, "FAVORITOS");
        saldoFragment = new SaldoFragment();
        adapter.addFragment(saldoFragment, "TARJETA");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                tabs.getTabAt(0).setIcon(R.drawable.ic_bus_white);
                tabs.getTabAt(1).setIcon(R.drawable.ic_heart_green);
                tabs.getTabAt(2).setIcon(R.drawable.ic_card_green);
                searchItem.setVisible(true);
                btnHowArrive.setVisibility(View.VISIBLE);
                lineasFragment.reload();

                break;
            case 1:
                tabs.getTabAt(0).setIcon(R.drawable.ic_bus_green);
                tabs.getTabAt(1).setIcon(R.drawable.ic_heart_white);
                tabs.getTabAt(2).setIcon(R.drawable.ic_card_green);
                searchItem.setVisible(false);
                btnHowArrive.setVisibility(View.VISIBLE);
                favsFragment.reload();
                break;
            case 2:
                tabs.getTabAt(0).setIcon(R.drawable.ic_bus_green);
                tabs.getTabAt(1).setIcon(R.drawable.ic_heart_green);
                tabs.getTabAt(2).setIcon(R.drawable.ic_card_white);
                searchItem.setVisible(false);
                btnHowArrive.setVisibility(View.GONE);
                saldoFragment.getlastCode();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void onFabClick(View v) {
        showProgressBar(true);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        lineasFragment.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        lineasFragment.filter(newText);
        return true;
    }


    /**
     * Un {@link FragmentPagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);

        searchItem = menu.findItem(R.id.action_search);
        sv = (SearchView) MenuItemCompat.getActionView(searchItem);
        sv.setOnQueryTextListener(this);
        sv.setQueryHint("Buscar lineas");
        searchItem.setVisible(false);
        return true;
    }

    public void clickSend(View v) {

        Uri uri = Uri.parse("mailto:" + "softwaretecpro@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", ((TextView) drawer.findViewById(R.id.txt_asunto)).getText().toString())
                .appendQueryParameter("body", ((TextView) drawer.findViewById(R.id.txt_msg)).getText().toString())
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        String[] to = {"softwaretecpro@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        startActivity(Intent.createChooser(emailIntent, "Elija un cliente"));


    }


    public void pgWeb(View view) {
        Uri uri;
        PackageManager pm = this.getPackageManager();
        try {
            pm.getPackageInfo("com.facebook.katana", 0);
            // http://stackoverflow.com/a/24547437/1048340
            uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/TecProSoftware");
        } catch (PackageManager.NameNotFoundException e) {
            uri = Uri.parse("https://www.facebook.com/TecProSoftware");
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(false);
    }

    private void showProgressBar(boolean show){
        findViewById(R.id.coordinator).setVisibility(show ? View.GONE : View.VISIBLE);
        findViewById(R.id.progressLayout).setVisibility(show ? View.VISIBLE : View.GONE);

    }

    public void showAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Aviso importante");

        // set dialog message
        alertDialogBuilder
                .setMessage(("Esta aplicación es desarrollada VOLUNTARIAMENTE por TEC-PRO,  NO pertenecemos a la empresa" +
                        " de transporte SATCRC.\nNO nos hacemos responsables por información incorrecta.\nLos datos son extraídos de la página oficial www.satcrc.com.ar."))
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        SharedPreferences prefs = getSharedPreferences("preferencias", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("showed", true);
                        editor.commit();
                        dialog.cancel();
                    }
                });



        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
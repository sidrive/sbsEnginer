package id.geekgarden.esi.listtiket;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import id.geekgarden.esi.R;

public class ListTiket extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "ListTiket";
  public static final String KEY = "key";
  private Toolbar toolbar;
  private FloatingActionButton fab;
  private DrawerLayout drawer;
  private ActionBarDrawerToggle toggle;
  private NavigationView navigationView;
  private FragmentManager fm;
  private FragmentTransaction ft;
  private String key = "all";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_tiket);
    initToolbar();
    //initFab();
    initDrawer();
    openTiket(key);
  }

  private void initFragment() {
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
  }

  private void initDrawer() {
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void initFab() {
    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  private void initToolbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override
  public void onBackPressed() {
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.list_tiket, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {

    int id = item.getItemId();
    switch (id){
      case R.id.nav_all_tiket:
        key = "all";
        openTiket(key);
        break;
      case R.id.nav_open_tiket:
        key = "open";
        openTiket(key);
        break;
      case R.id.nav_conf_tiket:
        key = "confirm";
        openTiket(key);
        break;
      case R.id.nav_progress_tiket:
        key = "progres";
        openTiket(key);
        break;
      case R.id.nav_hold_tiket:
        key = "hold";
        openTiket(key);
        break;
      case R.id.nav_ended_tiket:
        key = "ended";
        openTiket(key);
        break;
      case R.id.nav_all_alih:
        key = "all";
        openAlih(key);
        break;
      case R.id.nav_open_alih:
        key = "open";
        openAlih(key);
        break;
      case R.id.nav_conf_alih:
        key = "confirm";
        openAlih(key);
        break;
      case R.id.nav_progress_alih:
        key = "progres";
        openAlih(key);
        break;
      case R.id.nav_hold_alih:
        key = "hold";
        openAlih(key);
        break;
      case R.id.nav_ended_alih:
        key = "ended";
        openAlih(key);
        break;
      case R.id.nav_all_tugas:
        key = "all";
        openTugas(key);
        break;
      case R.id.nav_open_tugas:
        key = "open";
        openTugas(key);
        break;
      case R.id.nav_conf_tugas:
        key = "confirm";
        openTugas(key);
        break;
      case R.id.nav_progress_tugas:
        key = "progres";
        openTugas(key);
        break;
      case R.id.nav_hold_tugas:
        key = "hold";
        openTugas(key);
        break;
      case R.id.nav_ended_tugas:
        key = "ended";
        openTugas(key);
        break;


    }
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void openTugas(String key) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY,key);
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    PenugasanFragment f = new PenugasanFragment();
    f.setArguments(bundle);
    ft.replace(R.id.frame_main,f);
    ft.isAddToBackStackAllowed();
    ft.commit();
  }

  private void openAlih(String key) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY,key);
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    DialihkanFragment f = new DialihkanFragment();
    f.setArguments(bundle);
    ft.replace(R.id.frame_main,f);
    ft.isAddToBackStackAllowed();
    ft.commit();
  }

  private void openTiket(String key) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY,key);
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    MyTiketFragment f = new MyTiketFragment();
    f.setArguments(bundle);
    ft.replace(R.id.frame_main,f);
    ft.isAddToBackStackAllowed();
    ft.commit();
  }

}

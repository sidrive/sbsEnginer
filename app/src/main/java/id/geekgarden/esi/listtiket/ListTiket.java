package id.geekgarden.esi.listtiket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.listtiket.fragment.DialihkanFragment;
import id.geekgarden.esi.listtiket.fragment.MyTiketFragment;
import id.geekgarden.esi.listtiket.fragment.MyTiketFragmentSupervisor;
import id.geekgarden.esi.listtiket.fragment.PenugasanFragment;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;

public class ListTiket extends AppCompatActivity
    implements OnNavigationItemSelectedListener {
  private static final String TAG = "ListTiket";
  public static final String KEY = "key";
  @BindView(R.id.menu_labels_right)
  FloatingActionMenu menuLabelsRight;
  @BindView(R.id.nav_view)
  NavigationView navView;
  private Toolbar toolbar;
  private FloatingActionButton fab;
  private DrawerLayout drawer;
  private ActionBarDrawerToggle toggle;
  private NavigationView navigationView;
  private FragmentManager fm;
  private FragmentTransaction ft;
  private String key;
  private String key_fab = null;
  private String position_name;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_tiket);
    ButterKnife.bind(this);
    initToolbar();
    /*initFab();*/
    View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
    GlobalPreferences glpref = new GlobalPreferences(getApplicationContext());
    ((TextView) header.findViewById(R.id.email)).setText(glpref.read(PrefKey.email,String.class));
    ((TextView) header.findViewById(R.id.name)).setText(glpref.read(PrefKey.full_name,String.class));
    position_name = glpref.read(PrefKey.position_name, String.class);
    initDrawer();
    Menu m = navView.getMenu();
    if (position_name.equals("staff")) {
      menuLabelsRight.setVisibility(View.GONE);
      m.removeItem(R.id.dialihkan);
      m.removeItem(R.id.penugasan);
      m.removeItem(R.id.tiketspv);
      m.removeItem(R.id.complain);
      if (getIntent().getExtras() != null) {
        key = getIntent().getStringExtra(KEY);
        openTiket(key);
      } else {
        key = "open";
        openTiket(key);
      }
    } else {
      menuLabelsRight.setVisibility(View.VISIBLE);
      m.removeItem(R.id.dialihkanstaff);
      m.removeItem(R.id.tiketcust);
      if (getIntent().getExtras() != null) {
        key = getIntent().getStringExtra(KEY);
        openTiketSpv(key);
      } else {
        key = "open";
        openTiketSpv(key);
      }
    }
  }

  @OnClick(R.id.fabService)
  void OpenTiketHanter(View view) {
    Intent i = new Intent(this, OpenTiketServiceActivity.class);
    key_fab = "Service";
    i.putExtra(OpenTiketServiceActivity.KEY, key_fab);
    startActivity(i);
  }

  @OnClick(R.id.fabOther)
  void OpenTiketIT(View view) {
    Intent i = new Intent(this, OpenTiketOtherActivity.class);
    key_fab = "Other";
    i.putExtra(OpenTiketOtherActivity.KEY, key_fab);
    startActivity(i);
  }

  private void initDrawer() {
    drawer = findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    navView.setNavigationItemSelectedListener(this);
  }

  /*private void initFab() {
    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }*/

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override
  public void onBackPressed() {
    drawer = findViewById(R.id.drawer_layout);
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
    switch (id) {
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
      case R.id.nav_progress_new_tiket:
        key = "progres new";
        openTiket(key);
        break;
      case R.id.nav_hold_tiket:
        key = "hold";
        openTiket(key);
        break;
      case R.id.nav_progress_hold_tiket:
        key = "progres hold";
        openTiket(key);
        break;
      case R.id.nav_ended_tiket:
        key = "ended";
        openTiket(key);
        break;
      case R.id.nav_closed_tiket:
        key = "close";
        openTiket(key);
        break;
      case R.id.nav_canceled_tiket:
        key = "cancel";
        openTiket(key);
        break;
      case R.id.nav_dialihkan_staff:
        key = "dialihkan_staff";
        openTiket(key);
        break;
      case R.id.nav_all_tiket_spv:
        key = "all";
        openTiketSpv(key);
        break;
      case R.id.nav_open_tiket_spv:
        key = "open";
        openTiketSpv(key);
        break;
      case R.id.nav_conf_tiket_spv:
        key = "confirm";
        openTiketSpv(key);
        break;
      case R.id.nav_progress_new_tiket_spv:
        key = "progres new";
        openTiketSpv(key);
        break;
      case R.id.nav_hold_tiket_spv:
        key = "hold";
        openTiketSpv(key);
        break;
      case R.id.nav_progress_hold_tiket_spv:
        key = "progres hold";
        openTiketSpv(key);
        break;
      case R.id.nav_ended_tiket_spv:
        key = "ended";
        openTiketSpv(key);
        break;
      case R.id.nav_closed_tiket_spv:
        key = "close";
        openTiketSpv(key);
        break;
      case R.id.nav_canceled_tiket_spv:
        key = "cancel";
        openTiketSpv(key);
        break;
      case R.id.nav_complain:
        key = "complain";
        openTiketSpv(key);
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
      case R.id.nav_progress_new_alih:
        key = "progres new";
        openAlih(key);
        break;
      case R.id.nav_hold_alih:
        key = "hold";
        openAlih(key);
        break;
      case R.id.nav_progress_hold_alih:
        key = "progres hold";
        openAlih(key);
        break;
      case R.id.nav_ended_alih:
        key = "ended";
        openAlih(key);
        break;
      case R.id.nav_all_tugas:
        key = "all";
        openTugas(key);
        Log.e("nav_all_tugas", "ListTiket");
        break;
      case R.id.nav_open_tugas:
        key = "open";
        openTugas(key);
        break;
      case R.id.nav_conf_tugas:
        key = "confirm";
        openTugas(key);
        break;
      case R.id.nav_progress_new_tugas:
        key = "progres new";
        openTugas(key);
        break;
      case R.id.nav_hold_tiket_tugas:
        key = "hold";
        openTugas(key);
        break;
      case R.id.nav_progress_hold_tugas:
        key = "progres hold";
        openTugas(key);
        break;
      case R.id.nav_ended_tugas:
        key = "ended";
        openTugas(key);
        break;
    }
    drawer = findViewById(R.id.drawer_layout);
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

  public void openTiket(String key) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY, key);
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    MyTiketFragment f = new MyTiketFragment();
    f.setArguments(bundle);
    ft.replace(R.id.frame_main, f, key);
    ft.isAddToBackStackAllowed();
    ft.commit();
  }

  public void openTiketSpv(String key) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY, key);
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    MyTiketFragmentSupervisor f = new MyTiketFragmentSupervisor();
    f.setArguments(bundle);
    ft.replace(R.id.frame_main, f, key);
    ft.isAddToBackStackAllowed();
    ft.commit();
  }

}

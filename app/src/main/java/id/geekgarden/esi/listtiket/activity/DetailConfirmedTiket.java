package id.geekgarden.esi.listtiket.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;

public class DetailConfirmedTiket extends AppCompatActivity {
  public static String EXTRA_KEY = "key_push";
  private ActionBar actionBar;
  @OnClick(R.id.btnStart)void ConfirmTiket(View view){
   onBackPressed();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_confirmed_tiket);
    ButterKnife.bind(this);
    initActionbar();
    if (getIntent()!=null){
    String key_push = getIntent().getStringExtra(EXTRA_KEY);
    }

  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Confirmed Tiket");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}

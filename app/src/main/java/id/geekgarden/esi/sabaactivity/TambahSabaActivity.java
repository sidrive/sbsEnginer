package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;

public class TambahSabaActivity extends AppCompatActivity {
  private ActionBar actionBar;
  @OnClick(R.id.btnStart)void AddActivity(View view){
    finish();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tambah_saba);
    ButterKnife.bind(this);
    initActionBar();

  }

  private void showDetailDummyData() {

  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Tambah Activity");
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

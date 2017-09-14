package id.geekgarden.esi.listtiket.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;

public class Sparepart extends AppCompatActivity {
  private ActionBar actionBar;
  @OnClick(R.id.btnDone)void actionDone(View view){

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sparepart);
    ButterKnife.bind(this);
    initActionbar();
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Tambah Sparepart");
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

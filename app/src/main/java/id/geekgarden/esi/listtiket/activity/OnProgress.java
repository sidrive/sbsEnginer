package id.geekgarden.esi.listtiket.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;

public class OnProgress extends AppCompatActivity {
  private ActionBar actionBar;
  @OnClick(R.id.bntHold)void holdTiket(){
    finish();
  }
  @OnClick(R.id.btnEnd)void endTiket(){
    finish();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_onprogress_service_report);
    ButterKnife.bind(this);
    initActionBar();
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("On Progress");
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

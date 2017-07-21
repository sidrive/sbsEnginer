package id.geekgarden.esi.smom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import id.geekgarden.esi.R;

public class SmOmActivity extends AppCompatActivity {
  private ActionBar actionBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sm_om);
    initActionBar();
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
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

package id.geekgarden.esi.listtiket.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import id.geekgarden.esi.R;

public class DetailOnProgresvisitPmOther extends AppCompatActivity {
  private ActionBar actionBar;
  @OnClick(R.id.btnStart)void ConfirmTiket(){
    finish();
  }
  @OnCheckedChanged(R.id.cbSparepart)void openAddSparepart(CheckBox checkBox, boolean checked){
    Intent i = new Intent(getApplicationContext(),Sparepart.class);
    startActivity(i);
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_on_progresvisit_pm_other);
    ButterKnife.bind(this);
    initActionbar();
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Tiket");
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

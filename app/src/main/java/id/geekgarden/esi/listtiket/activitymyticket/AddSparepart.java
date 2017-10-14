package id.geekgarden.esi.listtiket.activitymyticket;

import static id.geekgarden.esi.listtiket.activitymyticket.DetailOnHold.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.part.partstatus.Datum;
import id.geekgarden.esi.data.model.tikets.part.partstatus.ResponseSpinnerPartStatus;
import rx.Observable;

/**
 * Created by komuri on 20/09/2017.
 */

public class AddSparepart extends AppCompatActivity implements OnItemSelectedListener {

  @BindView(R.id.spnpartstatus)
  Spinner spnpartstatus;
  private ActionBar actionBar;
  private String itemnumber;
  Api mApi;
  public AddSparepart() {

  }

  @BindView(R.id.tvpartnumber)
  EditText tvpartnumber;
  @BindView(R.id.tvdesc)
  EditText tvdesc;
  @BindView(R.id.tvqty)
  EditText tvqty;
  @BindView(R.id.tvketerangan)
  EditText tvketerangan;
  @BindView(R.id.btnStart)
  Button btnStart;

  String partnumber;
  String description;
  String qty;
  String status;
  String keterangan;

  DatabaseHandler db = new DatabaseHandler(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_add_sparepart);
    ButterKnife.bind(this);
    initActionBar();
    iniSpinnerPartStatus();
  }

  private void iniSpinnerPartStatus() {
    Observable<ResponseSpinnerPartStatus> responseSpinnerPartStatus =
  }

  @OnClick(R.id.btnStart)
  public void saveIntoSQLite() {
    this.partnumber = tvpartnumber.getText().toString();
    this.description = tvdesc.getText().toString();
    this.qty = tvqty.getText().toString();
    this.status = itemnumber;
    this.keterangan = tvketerangan.getText().toString();
    db.addSparepart(new SQLiteSparepart(partnumber, description, qty, status, keterangan));
    Log.e(TAG, "saveIntoSQLite: " + partnumber);
    Intent i = new Intent(getApplicationContext(), Sparepart.class);
    startActivity(i);
    finish();
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Tiket Confirm");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Datum selecteditem = (Datum) adapterView.getItemAtPosition(i);
    Log.e("onItemSelected", "DetailSpinner" + selecteditem.getName());
    itemnumber = selecteditem.getName().toString();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}




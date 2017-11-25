package id.geekgarden.esi.listtiket.activityticketstaff;

import static id.geekgarden.esi.listtiket.activityticketstaff.DetailOnHold.TAG;

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
import id.geekgarden.esi.data.DatabaseSparepart;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.AdapterSpinnerPartStatus;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.partstatus.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.partstatus.ResponseSpinnerPartStatus;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by komuri on 20/09/2017.
 */

public class AddSparepart extends AppCompatActivity implements OnItemSelectedListener {

  @BindView(R.id.spnpartstatus)
  Spinner spnpartstatus;
  String accesstoken;
  private ActionBar actionBar;
  private String itemnumber;
  private Api mApi;
  private GlobalPreferences glpref;
  private AdapterSpinnerPartStatus adapterSpinnerPartStatus;
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

  DatabaseSparepart db = new DatabaseSparepart(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getService();
    setContentView(R.layout.activity_add_sparepart);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accesstoken = glpref.read(PrefKey.accessToken,String.class);
    initActionBar();
    iniSpinnerPartStatus();
  }

  private void iniSpinnerPartStatus() {
    Spinner spinner = findViewById(R.id.spnpartstatus);
    spinner.setOnItemSelectedListener(this);
    adapterSpinnerPartStatus = new AdapterSpinnerPartStatus(this,
        android.R.layout.simple_spinner_item, new ArrayList<Datum>());
    adapterSpinnerPartStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapterSpinnerPartStatus);
    Observable<ResponseSpinnerPartStatus> responseSpinnerPartStatus = mApi
        .getpartstatus(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerPartStatus.subscribe(new Observer<ResponseSpinnerPartStatus>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerPartStatus responseSpinnerPartStatus) {
      adapterSpinnerPartStatus.UpdateOption(responseSpinnerPartStatus.getData());
      }
    });
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
    actionBar.setTitle("Add Sparepart");
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




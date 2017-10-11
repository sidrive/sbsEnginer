package id.geekgarden.esi.listtiket;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.Datum;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenTiketServiceActivity extends AppCompatActivity implements OnItemSelectedListener {

  private final static String TAG = OpenTiketServiceActivity.class.getSimpleName();
  public final static String KEY = "key";
  @BindView(R.id.spndivision)
  Spinner spndivision;
  @BindView(R.id.spncustomer)
  Spinner spncustomer;
  @BindView(R.id.spninstrument)
  Spinner spninstrument;
  @BindView(R.id.spnengineer)
  Spinner spnengineer;
  @BindView(R.id.tvdescription)
  TextInputEditText tvdescription;
  @BindView(R.id.tvanalis)
  TextInputEditText tvanalis;
  @BindView(R.id.tvhpanalis)
  TextInputEditText tvhpanalis;
  @BindView(R.id.spnPriority)
  Spinner spnPriority;
  @BindView(R.id.btnOpenTiket)
  Button btnOpenTiket;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;
  private AdapterSpinnerDivision adapterSpinnerDivision;
  private AdapterSpinnerPriority adapterSpinnerPriority;
  String accesstoken;
  String itemnumber;

  @OnClick(R.id.btnOpenTiket)
  void OpenTiket() {
    finish();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_tiket_service);
    ButterKnife.bind(this);
    key = getIntent().getStringExtra(KEY);
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(getApplicationContext());
    accesstoken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initSpinnerDivision();
    initSpinnerPriority();
  }

  private void initSpinnerPriority() {
    spnPriority.setOnItemSelectedListener(this);
    adapterSpinnerPriority = new AdapterSpinnerPriority(this, android.R.layout.simple_spinner_item, new ArrayList<ResponseSpinnerPriority>());
    adapterSpinnerPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnPriority.setAdapter(adapterSpinnerPriority);
    Observable<ResponseSpinnerPriority> responseSpinnerPriority = mApi.getspinnerpriority(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerPriority.subscribe(new Observer<ResponseSpinnerPriority>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerPriority responseSpinnerPriority) {
      }
    });
  }

  private void initSpinnerDivision() {
    spndivision.setOnItemSelectedListener(this);
    adapterSpinnerDivision = new AdapterSpinnerDivision(this, android.R.layout.simple_spinner_item, new ArrayList<Datum>());
    adapterSpinnerDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spndivision.setAdapter(adapterSpinnerDivision);
    Observable<ResponseSpinnerDivision> responseSpinnerDivision = mApi
        .getspinnerdivision(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerDivision.subscribe(new Observer<ResponseSpinnerDivision>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerDivision responseSpinnerDivision) {
        adapterSpinnerDivision.UpdateOption(responseSpinnerDivision.getData());
      }
    });
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Open Tiket " + key);
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
    getSupportFragmentManager().findFragmentByTag("open");
    finish();
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Datum selecteditem = (Datum) adapterView.getItemAtPosition(i);
    Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
    itemnumber = selecteditem.getId().toString();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

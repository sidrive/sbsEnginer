package id.geekgarden.esi.listtiket;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerInstrument;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.Datum;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.ResponseSpinnerInstrument;
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
  @BindView(R.id.tvIT)
  TextInputEditText tvIT;
  @BindView(R.id.lytTvIT)
  TextInputLayout lytTvIT;
  @BindView(R.id.lytSpnInstrument)
  TextInputLayout lytSpnInstrument;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;
  private AdapterSpinnerDivision adapterSpinnerDivision;
  private AdapterSpinnerPriority adapterSpinnerPriority;
  private AdapterSpinnerCustomer adapterSpinnerCustomer;
  private AdapterSpinnerEngineer adapterSpinnerEngineer;
  private AdapterSpinnerInstrument adapterSpinnerInstrument;
  String accesstoken;
  String itemnumberdivision;
  String itemnumberpriority;
  String itemnumbercustomer;
  String itemnumberinstrument;
  String itemnumberengineer;

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
  private void initSpinnerEngineer() {
    spnengineer.setOnItemSelectedListener(this);
    adapterSpinnerEngineer = new AdapterSpinnerEngineer(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum>());
    adapterSpinnerEngineer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnengineer.setAdapter(adapterSpinnerEngineer);
    Observable<ResponseSpinnerEngineer> responseSpinnerEngineer = mApi
        .getspinnerengineer(accesstoken,itemnumberdivision,itemnumbercustomer,itemnumberinstrument).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerEngineer.subscribe(new Observer<ResponseSpinnerEngineer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerEngineer responseSpinnerEngineer) {
      adapterSpinnerEngineer.UpdateOption(responseSpinnerEngineer.getData());
      }
    });
  }

  private void initSpinnerInstrument() {
    spninstrument.setOnItemSelectedListener(this);
    adapterSpinnerInstrument = new AdapterSpinnerInstrument(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum>());
    adapterSpinnerInstrument.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninstrument.setAdapter(adapterSpinnerInstrument);
    Observable <ResponseSpinnerInstrument> responseSpinnerInstrument = mApi
        .getspinnerinstrument(accesstoken,itemnumberdivision,itemnumbercustomer).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerInstrument.subscribe(new Observer<ResponseSpinnerInstrument>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerInstrument responseSpinnerInstrument) {
      adapterSpinnerInstrument.UpdateOption(responseSpinnerInstrument.getData());
      }
    });
  }
  private void initSpinnerCustomer() {
    spncustomer.setOnItemSelectedListener(this);
    adapterSpinnerCustomer = new AdapterSpinnerCustomer(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnercustomer.Datum>());
    adapterSpinnerCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spncustomer.setAdapter(adapterSpinnerCustomer);
    Observable<ResponseSpinnerCustomer> responseSpinnerCustomer = mApi
        .getspinnercustomer(accesstoken,itemnumberdivision).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerCustomer.subscribe(new Observer<ResponseSpinnerCustomer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(ResponseSpinnerCustomer responseSpinnerCustomer) {
      adapterSpinnerCustomer.UpdateOption(responseSpinnerCustomer.getData());
      }
    });
  }

  private void initSpinnerPriority() {
    spnPriority.setOnItemSelectedListener(this);
    adapterSpinnerPriority = new AdapterSpinnerPriority(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum>());
    adapterSpinnerPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnPriority.setAdapter(adapterSpinnerPriority);
    Observable<ResponseSpinnerPriority> responseSpinnerPriority = mApi
        .getspinnerpriority(accesstoken).subscribeOn(Schedulers.newThread())
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
        adapterSpinnerPriority.UpdateOption(responseSpinnerPriority.getData());
      }
    });
  }

  private void initSpinnerDivision() {
    spndivision.setOnItemSelectedListener(this);
    adapterSpinnerDivision = new AdapterSpinnerDivision(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<Datum>());
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
    Log.e("onItemSelected", "OpenTiketServiceActivity" + adapterView.getItemAtPosition(i));
    Spinner spinner = (Spinner) adapterView;
    Log.e("onItemSelected", "OpenTiketServiceActivity" + spinner.getId());
    switch (spinner.getId()) {
      case R.id.spndivision:
        Datum selecteditemdivision = (Datum) adapterView.getItemAtPosition(i);
        itemnumberdivision = selecteditemdivision.getId().toString();
        initSpinnerCustomer();
        Log.e("onItemSelected", "OpenTiketServiceActivity" + itemnumberdivision);
        if (itemnumberdivision.equals("3")) {
          lytTvIT.setVisibility(View.VISIBLE);
          lytSpnInstrument.setVisibility(View.GONE);
        } else {
          lytTvIT.setVisibility(View.GONE);
          lytSpnInstrument.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.spnPriority:
        id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum selecteditempriority =
            (id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum) adapterView
            .getItemAtPosition(i);
        itemnumberpriority = selecteditempriority.getName();
        break;
      case R.id.spncustomer:
          id.geekgarden.esi.data.model.openticket.responsespinnercustomer.Datum selecteditemcustomer =
              (id.geekgarden.esi.data.model.openticket.responsespinnercustomer.Datum) adapterView
              .getItemAtPosition(i);
          itemnumbercustomer = selecteditemcustomer.getId().toString();
          initSpinnerInstrument();
          break;
      case R.id.spninstrument:
        id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum selectediteminstrument =
            (id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum) adapterView
            .getItemAtPosition(i);
        itemnumberinstrument = selectediteminstrument.getId().toString();
        initSpinnerEngineer();
        break;
      case R.id.spnengineer:
        id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum selecteditenengineer =
            (id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum) adapterView
            .getItemAtPosition(i);
        itemnumberengineer = selecteditenengineer.getId().toString();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

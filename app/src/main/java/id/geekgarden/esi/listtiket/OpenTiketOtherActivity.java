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
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenOther;
import id.geekgarden.esi.data.model.openticket.responseopenticketservice.ResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenTiketOtherActivity extends AppCompatActivity implements OnItemSelectedListener {

  private final static String TAG = OpenTiketOtherActivity.class.getSimpleName();
  public final static String KEY = "key";
  @BindView(R.id.spnother)
  Spinner spnother;
  @BindView(R.id.tvDivision)
  TextView tvDivision;
  @BindView(R.id.spncustomer)
  Spinner spncustomer;
  @BindView(R.id.spnengineer)
  Spinner spnengineer;
  @BindView(R.id.tvdescription)
  TextInputEditText tvdescription;
  @BindView(R.id.spnPriority)
  Spinner spnPriority;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;
  private AdapterSpinnerPriority adapterSpinnerPriority;
  private AdapterSpinnerCustomer adapterSpinnerCustomer;
  private AdapterSpinnerEngineer adapterSpinnerEngineer;
  private AdapterSpinnerOnProgress adapterSpinnerOnProgress;
  String accesstoken;
  int itemnumberdivision;
  String itemnumberpriority;
  int itemnumbercustomer;
  int itemnumber;
  int itemnumberengineer;
  int Division;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_tiket_other);
    ButterKnife.bind(this);
    key = getIntent().getStringExtra(KEY);
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(getApplicationContext());
    accesstoken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initSpinnerDivision();
    initSpinnerPriority();
  }

  @OnClick(R.id.btnOpenTiket)
  void OpenTiket() {
    updateTiketOther();
  }

  private void updateTiketOther() {
    BodyResponseOpenOther bodyResponseOpenOther = new BodyResponseOpenOther();
    bodyResponseOpenOther.setTicketTypeId(2);
    bodyResponseOpenOther.setDivisionId(Division);
    bodyResponseOpenOther.setCustomerId(itemnumbercustomer);
    bodyResponseOpenOther.setTicketActivityId(itemnumber);
    bodyResponseOpenOther.setStaffId(itemnumberengineer);
    bodyResponseOpenOther.setPriority(itemnumberpriority);
    bodyResponseOpenOther.setDescription(tvdescription.getText().toString());
    Observable<ResponseOpenservice> openservice = mApi
        .openticketother(accesstoken, bodyResponseOpenOther)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    openservice.subscribe(responseOpenservice -> {
          UiUtils.showToast(getApplicationContext(), "Sucess Open Ticket");
          onBackPressed();
        }
        , throwable -> {
        });
  }

  private void initSpinnerEngineer() {
    spnengineer.setOnItemSelectedListener(this);
    adapterSpinnerEngineer = new AdapterSpinnerEngineer(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum>());
    adapterSpinnerEngineer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnengineer.setAdapter(adapterSpinnerEngineer);
    Observable<ResponseSpinnerEngineer> responseSpinnerEngineer = mApi
        .getspinnerengineer(accesstoken, Division, itemnumbercustomer, 0)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerEngineer.subscribe(responseSpinnerEngineer1 -> {
      adapterSpinnerEngineer.UpdateOption(responseSpinnerEngineer1.getData());
    }, throwable -> {
    });
  }

  private void initSpinnerOther() {
    spnother.setOnItemSelectedListener(this);
    adapterSpinnerOnProgress = new AdapterSpinnerOnProgress(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Datum>());
    adapterSpinnerOnProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnother.setAdapter(adapterSpinnerOnProgress);
    Observable<Responsespinneronprogress> responseSpinnerOther = mApi
        .getSpinneronprogress(accesstoken)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerOther.subscribe(responseSpinnerOther1 -> {
      adapterSpinnerOnProgress.UpdateOption(responseSpinnerOther1.getData());
    }, throwable -> {
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
        .getspinnercustomer(accesstoken, itemnumberdivision)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerCustomer.subscribe(
        responseSpinnerCustomer1 -> adapterSpinnerCustomer
            .UpdateOption(responseSpinnerCustomer1.getData())
        , throwable -> {
        });
  }

  private void initSpinnerPriority() {
    spnPriority.setOnItemSelectedListener(this);
    adapterSpinnerPriority = new AdapterSpinnerPriority(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<Datum>());
    adapterSpinnerPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnPriority.setAdapter(adapterSpinnerPriority);
    Observable<ResponseSpinnerPriority> responseSpinnerPriority = mApi
        .getspinnerpriority(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerPriority.subscribe(responseSpinnerPriority1 ->
            adapterSpinnerPriority.UpdateOption(responseSpinnerPriority1.getData())
        , throwable -> {
        });
  }

  private void initSpinnerDivision() {
    Observable<ResponseSpinnerDivision> responseSpinnerDivision = mApi
        .getspinnerdivision(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerDivision.subscribe(
        responseSpinnerDivision1 -> {
          for (int i = 0; i < responseSpinnerDivision1.getData().size(); i++) {
            id.geekgarden.esi.data.model.openticket.responsespinnerdivision.Datum datum = new id.geekgarden.esi.data.model.openticket.responsespinnerdivision.Datum();
            datum.setName(responseSpinnerDivision1.getData().get(i).getName());
            datum.setId(responseSpinnerDivision1.getData().get(i).getId());
            tvDivision.setText(datum.getName());
            Division = datum.getId();
          }
          initSpinnerCustomer();
          initSpinnerOther();
        }
        , throwable -> {
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
    Spinner spinner = (Spinner) adapterView;
    Log.e("onItemSelected", "OpenTiketServiceActivity" + spinner.getId());
    switch (spinner.getId()) {
      case R.id.spnPriority:
        Datum selecteditempriority =
            (Datum) adapterView
                .getItemAtPosition(i);
        itemnumberpriority = selecteditempriority.getName();
        break;
      case R.id.spncustomer:
        id.geekgarden.esi.data.model.openticket.responsespinnercustomer.Datum selecteditemcustomer =
            (id.geekgarden.esi.data.model.openticket.responsespinnercustomer.Datum) adapterView
                .getItemAtPosition(i);
        itemnumbercustomer = selecteditemcustomer.getId();
        initSpinnerEngineer();
        break;
      case R.id.spnother:
        id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Datum selecteditem = (id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
        itemnumber = selecteditem.getId();
        break;
      case R.id.spnengineer:
        id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum selectedengineer = (id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selectedengineer.getId());
        itemnumberengineer = selectedengineer.getId();
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerHardware;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerInstrument;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerInterface;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerSoftware;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenService;
import id.geekgarden.esi.data.model.openticket.responseopenticketservice.ResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.Datum;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.ResponseSpinnerInstrument;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenTiketServiceActivity extends AppCompatActivity implements OnItemSelectedListener {

  private final static String TAG = OpenTiketServiceActivity.class.getSimpleName();
  public final static String KEY = "key";
  @BindView(R.id.spninterface)
  Spinner spninterface;
  @BindView(R.id.spnHardware)
  Spinner spnHardware;
  @BindView(R.id.spnSoftware)
  Spinner spnSoftware;
  @BindView(R.id.lytTvSoftware)
  TextInputLayout lytTvSoftware;
  @BindView(R.id.lytTvHardware)
  TextInputLayout lytTvHardware;
  private List<Datum> listarray = new ArrayList<Datum>();
  @BindView(R.id.spncustomer)
  Spinner spncustomer;
  @BindView(R.id.spninstrument)
  Spinner spninstrument;
  @BindView(R.id.spnengineer)
  Spinner spnengineer;
  @BindView(R.id.tvdescription)
  TextInputEditText tvdescription;
  @BindView(R.id.spnPriority)
  Spinner spnPriority;
  @BindView(R.id.btnOpenTiket)
  Button btnOpenTiket;
  @BindView(R.id.lytTvIT)
  TextInputLayout lytTvIT;
  @BindView(R.id.lytSpnInstrument)
  TextInputLayout lytSpnInstrument;
  @BindView(R.id.tvDivision)
  TextView tvDivision;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;
  private AdapterSpinnerPriority adapterSpinnerPriority;
  private AdapterSpinnerCustomer adapterSpinnerCustomer;
  private AdapterSpinnerEngineer adapterSpinnerEngineer;
  private AdapterSpinnerInstrument adapterSpinnerInstrument;
  private AdapterSpinnerInterface adapterSpinnerInterface;
  private AdapterSpinnerHardware adapterSpinnerHardware;
  private AdapterSpinnerSoftware adapterSpinnerSoftware;
  String accesstoken;
  int itemnumberdivision;
  String itemnumberpriority;
  int itemnumbercustomer;
  int itemnumberinstrument;
  int itemnumberengineer;
  int Division;
  String itemnumberinterface;
  int itemnumberhardware;
  int itemnumbersoftware;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_tiket_service);
    ButterKnife.bind(this);
    key = getIntent().getStringExtra(KEY);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    accesstoken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initSpinnerDivision();
    initSpinnerPriority();
  }

  @OnClick(R.id.btnOpenTiket)
  void OpenTiket() {
    onOpenTicket();
  }

  private void onOpenTicket() {
    BodyResponseOpenService bodyresponseOpenService = new BodyResponseOpenService();
    bodyresponseOpenService.setTicketTypeId(1);
    bodyresponseOpenService.setDivisionId(Division);
    bodyresponseOpenService.setCustomerId(itemnumbercustomer);
    if (Division == 3 && itemnumberinterface.equals("Software")) {
      bodyresponseOpenService.setCategory(itemnumberinterface);
      bodyresponseOpenService.setVersionId(itemnumbersoftware);
    } else
    if (Division == 3 && itemnumberinterface.equals("Hardware")){
      bodyresponseOpenService.setCategory(itemnumberinterface);
      bodyresponseOpenService.setInterfaceId(itemnumberhardware);
    } else {
      bodyresponseOpenService.setInstrumentId(itemnumberinstrument);
    }
    bodyresponseOpenService.setStaffId(itemnumberengineer);
    bodyresponseOpenService.setPriority(itemnumberpriority);
    bodyresponseOpenService.setDescription(tvdescription.getText().toString());
    Observable<ResponseOpenservice> openservice = mApi
        .openticketservice(accesstoken, bodyresponseOpenService)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    openservice.subscribe(responseOpenservice -> {
          UiUtils.showToast(getApplicationContext(), "Success Open Ticket");
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
        .getspinnerengineer(accesstoken, Division, itemnumbercustomer)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerEngineer.subscribe(
        responseSpinnerEngineer1 -> adapterSpinnerEngineer
            .UpdateOption(responseSpinnerEngineer1.getData())
        , throwable -> {
        });
  }

  private void initSpinnerInstrument() {
    spninstrument.setOnItemSelectedListener(this);
    adapterSpinnerInstrument = new AdapterSpinnerInstrument(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum>());
    adapterSpinnerInstrument.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninstrument.setAdapter(adapterSpinnerInstrument);
    Observable<ResponseSpinnerInstrument> responseSpinnerInstrument = mApi
        .getspinnerinstrument(accesstoken, Division, itemnumbercustomer)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerInstrument.subscribe(
        responseSpinnerInstrument1 -> adapterSpinnerInstrument
            .UpdateOption(responseSpinnerInstrument1.getData())
        , throwable -> {
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
        .getspinnercustomer(accesstoken, Division)
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
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum>());
    adapterSpinnerPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnPriority.setAdapter(adapterSpinnerPriority);
    Observable<ResponseSpinnerPriority> responseSpinnerPriority = mApi
        .getspinnerpriority(accesstoken).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerPriority.subscribe(
        responseSpinnerPriority1 -> adapterSpinnerPriority
            .UpdateOption(responseSpinnerPriority1.getData())
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
            Datum datum = new Datum();
            datum.setName(responseSpinnerDivision1.getData().get(i).getName());
            datum.setId(responseSpinnerDivision1.getData().get(i).getId());
            tvDivision.setText(datum.getName());
            Division = datum.getId();
          }
          if (Division == 3) {
            lytTvIT.setVisibility(View.VISIBLE);
            lytSpnInstrument.setVisibility(View.GONE);
          } else {
            lytTvIT.setVisibility(View.GONE);
            lytSpnInstrument.setVisibility(View.VISIBLE);
          }
          initSpinnerCustomer();
        }
        , throwable -> {
        });
  }

  private void initSpinnerCategory() {
    spninterface.setOnItemSelectedListener(this);
    adapterSpinnerInterface = new AdapterSpinnerInterface(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responseinterface.Datum>());
    adapterSpinnerInterface.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninterface.setAdapter(adapterSpinnerInterface);
    mApi.getcategory(accesstoken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapterSpinnerInterface.UpdateOption(object.getData());
        }, throwable -> {
        });
  }

  private void initSpinnerHardware() {
    spnHardware.setOnItemSelectedListener(this);
    adapterSpinnerHardware = new AdapterSpinnerHardware(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsehardware.Datum>());
    adapterSpinnerHardware.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnHardware.setAdapter(adapterSpinnerHardware);
    mApi.getHardware(accesstoken,itemnumbercustomer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapterSpinnerHardware.UpdateOption(object.getData());
        }, throwable -> {
        });
  }

  private void initSpinnerSoftware() {
    spnSoftware.setOnItemSelectedListener(this);
    adapterSpinnerSoftware = new AdapterSpinnerSoftware(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsesoftware.Datum>());
    adapterSpinnerSoftware.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnSoftware.setAdapter(adapterSpinnerSoftware);
    mApi.getSoftware(accesstoken,itemnumbercustomer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapterSpinnerSoftware.UpdateOption(object.getData());
        }, throwable -> {
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
    switch (spinner.getId()) {
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
        itemnumbercustomer = selecteditemcustomer.getId();
        initSpinnerInstrument();
        initSpinnerEngineer();
        initSpinnerCategory();
        initSpinnerHardware();
        initSpinnerSoftware();
        break;
      case R.id.spninstrument:
        id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum selectediteminstrument =
            (id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.Datum) adapterView
                .getItemAtPosition(i);
        itemnumberinstrument = selectediteminstrument.getId();
        break;
      case R.id.spnengineer:
        id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum selecteditenengineer =
            (id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum) adapterView
                .getItemAtPosition(i);
        itemnumberengineer = selecteditenengineer.getId();
        break;
      case R.id.spninterface:
        id.geekgarden.esi.data.model.openticket.responseinterface.Datum selectediteminterface =
            (id.geekgarden.esi.data.model.openticket.responseinterface.Datum) adapterView
                .getItemAtPosition(i);
        itemnumberinterface = selectediteminterface.getCategory();
        Log.e("onItemSelected", "OpenTiketServiceActivity" + itemnumberinterface);
        if (itemnumberinterface.equals("Hardware")){
          lytTvHardware.setVisibility(View.VISIBLE);
          spnHardware.setVisibility(View.VISIBLE);
          spnSoftware.setVisibility(View.GONE);
          lytTvSoftware.setVisibility(View.GONE);
        } else
        if (itemnumberinterface.equals("Software")){
          lytTvSoftware.setVisibility(View.VISIBLE);
          lytTvHardware.setVisibility(View.GONE);
          spnHardware.setVisibility(View.GONE);
          spnSoftware.setVisibility(View.VISIBLE);
        } else {
          lytTvHardware.setVisibility(View.GONE);
          lytTvSoftware.setVisibility(View.GONE);
        }
        break;
      case R.id.spnHardware:
        id.geekgarden.esi.data.model.openticket.responsehardware.Datum selecteditemhardware =
            (id.geekgarden.esi.data.model.openticket.responsehardware.Datum) adapterView
                .getItemAtPosition(i);
        itemnumberhardware = selecteditemhardware.getId();
        break;
      case R.id.spnSoftware:
        id.geekgarden.esi.data.model.openticket.responsesoftware.Datum selecteditemsoftware =
            (id.geekgarden.esi.data.model.openticket.responsesoftware.Datum) adapterView
                .getItemAtPosition(i);
        itemnumbersoftware = selecteditemsoftware.getId();
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

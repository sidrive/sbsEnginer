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
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerInstallInstrument;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerInterface;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerRequest;
import id.geekgarden.esi.data.model.openticket.AdapterSpinnerReturnInstrument;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenOther;
import id.geekgarden.esi.data.model.openticket.responseinstrumentinstall.ResponseInstrumentInstall;
import id.geekgarden.esi.data.model.openticket.responseopenticketservice.ResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.Datum;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
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
  @BindView(R.id.lytspninstrument)
  TextInputLayout lytspninstrument;
  @BindView(R.id.spninstallinstrument)
  Spinner spninstallinstrument;
  @BindView(R.id.spnreturninstrument)
  Spinner spnreturninstrument;
  @BindView(R.id.spninterface)
  Spinner spninterface;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;
  private AdapterSpinnerPriority adapterSpinnerPriority;
  private AdapterSpinnerCustomer adapterSpinnerCustomer;
  private AdapterSpinnerEngineer adapterSpinnerEngineer;
  private AdapterSpinnerInterface adapterSpinnerInterface;
  private AdapterSpinnerRequest adapterSpinnerRequest;
  private AdapterSpinnerInstallInstrument adapterSpinnerInstallInstrument;
  private AdapterSpinnerReturnInstrument adapterSpinnerReturnInstrument;
  String accesstoken;
  int itemnumberdivision;
  String itemnumberpriority;
  int itemnumbercustomer;
  int itemnumber;
  int itemnumberengineer;
  int Division;
  String itemactivity;
  int itemnumberinstallinstrument;
  int itemnumberreturninstrument;
  int itemnumberinterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_tiket_other);
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
    updateTiketOther();
  }

  private void updateTiketOther() {
    BodyResponseOpenOther bodyResponseOpenOther = new BodyResponseOpenOther();
    bodyResponseOpenOther.setTicketTypeId(2);
    bodyResponseOpenOther.setDivisionId(Division);
    bodyResponseOpenOther.setCustomerId(itemnumbercustomer);
    bodyResponseOpenOther.setRequestId(itemnumber);
    if (itemactivity.equals("Installation")) {
      if (Division == 3) {
        bodyResponseOpenOther.setInterfaceId(itemnumberinterface);
      } else {
        bodyResponseOpenOther.setInstrumentId(itemnumberinstallinstrument);
      }
    } else if (itemactivity.equals("Return")) {
      bodyResponseOpenOther.setInstrumentId(itemnumberreturninstrument);
    }
    bodyResponseOpenOther.setStaffId(itemnumberengineer);
    bodyResponseOpenOther.setPriority(itemnumberpriority);
    bodyResponseOpenOther.setDescription(tvdescription.getText().toString());
    Observable<ResponseOpenservice> openservice = mApi
        .openticketother(accesstoken, bodyResponseOpenOther)
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
    responseSpinnerEngineer.subscribe(responseSpinnerEngineer1 -> {
      adapterSpinnerEngineer.UpdateOption(responseSpinnerEngineer1.getData());
    }, throwable -> {
    });
  }

  private void initSpinnerOther() {
    spnother.setOnItemSelectedListener(this);
    adapterSpinnerRequest = new AdapterSpinnerRequest(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responsespinnerrequest.Datum>());
    adapterSpinnerRequest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnother.setAdapter(adapterSpinnerRequest);
    mApi.getspinnerrequest(accesstoken, Division)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseRequestSpv -> {
          adapterSpinnerRequest.UpdateOption(responseRequestSpv.getData());
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

  private void initSpinnerInstrumentInstall() {
    spninstallinstrument.setOnItemSelectedListener(this);
    adapterSpinnerInstallInstrument = new AdapterSpinnerInstallInstrument(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responseinstrumentinstall.Datum>());
    adapterSpinnerInstallInstrument
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninstallinstrument.setAdapter(adapterSpinnerInstallInstrument);
    Observable<ResponseInstrumentInstall> getinstallinstrument = mApi
        .getspinnerinstall(accesstoken, itemnumbercustomer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getinstallinstrument.subscribe(responseInstrumentInstall -> {
      adapterSpinnerInstallInstrument.UpdateOption(responseInstrumentInstall.getData());
    }, throwable -> {
    });
  }

  private void initSpinnerInstrumentReturn() {
    spnreturninstrument.setOnItemSelectedListener(this);
    adapterSpinnerReturnInstrument = new AdapterSpinnerReturnInstrument(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responseinstrumentreturn.Datum>());
    adapterSpinnerReturnInstrument
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnreturninstrument.setAdapter(adapterSpinnerReturnInstrument);
    mApi.getspinnerreturn(accesstoken, itemnumbercustomer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseInstrumentInstall -> {
          adapterSpinnerReturnInstrument.UpdateOption(responseInstrumentInstall.getData());
        }, throwable -> {
        });
  }

  private void initSpinnerInterface() {
    spninterface.setOnItemSelectedListener(this);
    adapterSpinnerInterface = new AdapterSpinnerInterface(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<id.geekgarden.esi.data.model.openticket.responseinterface.Datum>());
    adapterSpinnerInterface.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninterface.setAdapter(adapterSpinnerInterface);
    mApi.getinterface(accesstoken, itemnumbercustomer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapterSpinnerInterface.UpdateOption(object.getData());
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
        initSpinnerInstrumentInstall();
        initSpinnerInstrumentReturn();
        initSpinnerInterface();
        break;
      case R.id.spnother:
        id.geekgarden.esi.data.model.openticket.responsespinnerrequest.Datum selecteditem = (id.geekgarden.esi.data.model.openticket.responsespinnerrequest.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
        Log.e("onItemSelected", "OpenTiketOtherActivity" + selecteditem.getName());
        itemnumber = selecteditem.getId();
        itemactivity = selecteditem.getName();
        if (itemactivity.equals("Installation")) {
          if (Division == 3){
            lytspninstrument.setVisibility(View.VISIBLE);
            spninterface.setVisibility(View.VISIBLE);
            spninstallinstrument.setVisibility(View.GONE);
            spnreturninstrument.setVisibility(View.GONE);
          } else {
            lytspninstrument.setVisibility(View.VISIBLE);
            spnreturninstrument.setVisibility(View.GONE);
            spninstallinstrument.setVisibility(View.VISIBLE);
            spninterface.setVisibility(View.GONE);
          }
        } else if (itemactivity.equals("Return")) {
          lytspninstrument.setVisibility(View.VISIBLE);
          spninstallinstrument.setVisibility(View.GONE);
          spnreturninstrument.setVisibility(View.VISIBLE);
          spninterface.setVisibility(View.GONE);
      } else {
          lytspninstrument.setVisibility(View.GONE);
        }
        break;
      case R.id.spnengineer:
        id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum selectedengineer = (id.geekgarden.esi.data.model.openticket.responsespinnerengineer.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selectedengineer.getId());
        itemnumberengineer = selectedengineer.getId();
        break;
      case R.id.spninstallinstrument:
        id.geekgarden.esi.data.model.openticket.responseinstrumentinstall.Datum selectedinstallinstrument = (id.geekgarden.esi.data.model.openticket.responseinstrumentinstall.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selectedinstallinstrument.getId());
        itemnumberinstallinstrument = selectedinstallinstrument.getId();
        break;
      case R.id.spnreturninstrument:
        id.geekgarden.esi.data.model.openticket.responseinstrumentreturn.Datum selectedreturninstrument = (id.geekgarden.esi.data.model.openticket.responseinstrumentreturn.Datum) adapterView
            .getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selectedreturninstrument.getId());
        itemnumberreturninstrument = selectedreturninstrument.getId();
        break;
      case R.id.spninterface:
        id.geekgarden.esi.data.model.openticket.responseinterface.Datum selectediteminterface =
            (id.geekgarden.esi.data.model.openticket.responseinterface.Datum) adapterView
                .getItemAtPosition(i);
        itemnumberinterface  = selectediteminterface.getId();
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
  }
}

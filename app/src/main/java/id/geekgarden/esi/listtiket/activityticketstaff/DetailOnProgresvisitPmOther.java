package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerPMInstrument;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ResponseChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticketother.ResponseTicketDetailOther;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.ResponsePMInstrument;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgresvisitPmOther extends AppCompatActivity implements
    OnItemSelectedListener {

  public static final String KEY_URI = "id";
  public static final String KEY_CUST = "customer_id";
  public static final String KEY_CAT = "category_other";
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  @BindView(R.id.tvticketcategory)
  TextView tvticketcategory;
  @BindView(R.id.rcvchecklist)
  RecyclerView rcvchecklist;
  private AdapterSpinnerPMInstrument adapterSpinnerPMInstrument;
  private AdapterChecklist adapterChecklist;
  String accessToken;
  String idtiket;
  String id_customer;
  @BindView(R.id.spninstrument)
  Spinner spninstrument;
  private Api mApi;
  private GlobalPreferences glpref;
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvnotelp)
  TextView tvnotelp;
  @BindView(R.id.tvurgency)
  TextView tvurgency;
  @BindView(R.id.tvnumber)
  TextView tvnumber;
  @BindView(R.id.tvkategori)
  TextView tvkategori;
  @BindView(R.id.textInputEditText)
  TextInputEditText textInputEditText;
  private ActionBar actionBar;
  private String key;
  private String category;
  int itemnumberinstrument;

    /*@OnCheckedChanged(R.id.cbSparepart)
    void openAddSparepart(CheckBox checkBox, boolean checked) {
        Intent i = new Intent(getApplicationContext(), Sparepart.class);
        startActivity(i);
    }*/;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getervice();
    setContentView(R.layout.activity_detail_on_progresvisit_pm_other);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);

    } else {

    }
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {

    }
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {

    }
    initActionbar();
    initViewData();
    initRecycleview();
    initSpinnerInstrument();
  }

  private void initRecycleview() {
    rcvchecklist.setHasFixedSize(true);
    rcvchecklist.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvchecklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
  }

  private void initSpinnerInstrument() {
    spninstrument.setOnItemSelectedListener(this);
    adapterSpinnerPMInstrument = new AdapterSpinnerPMInstrument(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<Datum>());
    adapterSpinnerPMInstrument
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spninstrument.setAdapter(adapterSpinnerPMInstrument);
    Observable<ResponsePMInstrument> responseSpinnerPMInstrument = mApi
        .getspinnerpminstrument(accessToken, id_customer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    responseSpinnerPMInstrument.subscribe(
        responseSpinnerPMInstrument1 -> {
          adapterSpinnerPMInstrument.UpdateOption(responseSpinnerPMInstrument1.getData());
        }
        , throwable -> {
        });
  }

  @OnClick(R.id.btnStart)
  void ConfirmTiket() {
/*    onendclick();*/
  }

  private void getDataChecklist() {
    adapterChecklist = new AdapterChecklist
        (new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.Datum>(0), getApplicationContext(),
        (int id) -> {});
    Observable<ResponseChecklist> getchecklist = mApi
        .getpmchecklist(accessToken, itemnumberinstrument, category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getchecklist.subscribe(responseChecklist -> {adapterChecklist.UpdateTikets(responseChecklist.getData());

    }, throwable -> {});
    rcvchecklist.setAdapter(adapterChecklist);
  }

  private void onendclick() {
    BodyOnProgress bodyOnProgress = new BodyOnProgress();
    Observable<ResponseOnProgressEnd> respononprogressend = mApi
        .updateonendtiket(accessToken, idtiket, bodyOnProgress).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    respononprogressend.subscribe(
        responseOnProgressEnd -> onBackPressed()
        , throwable -> {
        });
  }

  private void initViewData() {
    Observable<ResponseTicketDetailOther> responsedetailtiketother = mApi
        .detailticketother(accessToken, idtiket)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responsedetailtiketother.subscribe(responseDetailTiket -> {
      tvnamaanalis.setText(responseDetailTiket.getData().getStaffName());
      tvnotelp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
      tvurgency.setText(responseDetailTiket.getData().getPriority());
      tvnumber.setText(responseDetailTiket.getData().getNumber());
      tvDescTiket.setText(responseDetailTiket.getData().getDescription());
      tvticketcategory.setText(responseDetailTiket.getData().getRequest().getData().getName());
    }, throwable -> {
    });
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Tiket Other");
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
    Datum selectediteminstrument = (Datum) adapterView.getItemAtPosition(i);
    itemnumberinstrument = selectediteminstrument.getInstrumentTypeId();
    getDataChecklist();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

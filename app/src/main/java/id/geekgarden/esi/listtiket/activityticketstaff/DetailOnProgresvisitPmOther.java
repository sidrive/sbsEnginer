package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklist.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerPMInstrument;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist.BodyChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist.Datum_;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistItem;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ResponseChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticketother.ResponseTicketDetailOther;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.ResponsePMInstrument;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import java.util.List;
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
  private BodyChecklist bodyChecklist;
  int itemnumberinstrument;
  private ArrayList<ChecklistGroup> listarray = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();
  private ArrayList<Datum_> listbodychecklist = new ArrayList<Datum_>();

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
    bodyChecklist = new BodyChecklist();
    Log.e("onCreate", "DetailOnProgresvisitPmOther" + bodyChecklist.toString());
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
    onendclick();
  }

  private void getDataChecklist() {
    adapterChecklist = new AdapterChecklist
        (new ArrayList<ChecklistItem>(0), getApplicationContext(), new onCheckboxchecked() {
          @Override
          public void onCheckboxcheckedlistener(int id, String id_checklist_group,Boolean is_checked){
            Log.e("id", "DetailOnProgresvisitPmOther" + id);
            Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
            Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
            Datum_ datum_ = new Datum_();
            datum_.setChecklistItemId(String.valueOf(id));
            datum_.setValue(is_checked);
            listbodychecklist.add(datum_);
          }
        });
    Observable<ResponseChecklist> getchecklist = mApi
        .getpmchecklist(accessToken, itemnumberinstrument, category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getchecklist.subscribe(responseChecklist -> {
      bodyChecklist.setChecklistId(responseChecklist.getData().getId());
      for (int i = 0; i < responseChecklist.getData().getChecklistGroup().size(); i++) {
        ChecklistGroup chg = new ChecklistGroup();
        chg.setName(responseChecklist.getData().getChecklistGroup().get(i).getName());
        chg.setId(responseChecklist.getData().getChecklistGroup().get(i).getId());
        listarray.add(chg);
        for (int j = 0; j < responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().size(); j++) {
          ChecklistItem chi = new ChecklistItem();
          chi.setName(responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j).getName());
          chi.setId(responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j).getId());
          chi.setChecklist_group_id(responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j).getChecklist_group_id());
          listarrayitem.add(chi);
        }
      }
      Log.e("getDataChecklist", "DetailOnProgresvisitPmOther" + listarray);
      adapterChecklist.UpdateTikets(listarrayitem);

    }, throwable -> {});
    rcvchecklist.setAdapter(adapterChecklist);
  }

  private void onendclick() {
    bodyChecklist.setNotes(textInputEditText.getText().toString());
    Observable<ResponseChecklist> updatechecklistend = mApi
        .updatechecklist(accessToken, idtiket, bodyChecklist).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    updatechecklistend.subscribe(
        responseOnProgressEnd -> onBackPressed()
        , throwable -> {});
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

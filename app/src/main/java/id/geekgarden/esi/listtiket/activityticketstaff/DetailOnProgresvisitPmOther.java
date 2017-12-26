package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistPM;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistPM.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerPMInstrument;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist.BodyChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist.ChecklistItems;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.BodyChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.ChecklistItemVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistItem;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ResponseChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ResponseVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.ResponsePMInstrument;
import id.geekgarden.esi.helper.ImagePicker;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgresvisitPmOther extends AppCompatActivity implements
    OnItemSelectedListener {

  public static final String KEY_URI = "id";
  public static final String KEY_CUST = "customer_id";
  public static final String KEY_CAT = "category_other";
  public static final String KEY_TICK = "ticket_type";
  public static final String KEY_ACTI = "activity_id";
  public static final String KEY_SNAME = "staff_name";
  public static final String KEY_SPHN = "staff_phonenumber";
  public static final String KEY_INST = "instrument_type";
  public static final String KEY_INS = "instrument";
  public static final String KEY_PRIO = "priority";
  public static final String KEY_NUM = "number";
  public static final String KEY_CUSTN = "customername";
  public static final String KEY_CONT = "contract";
  public static final String KEY_DESC = "description";
  public static final String KEY_CIT = "it_category";
  public static final String KEY_IDI = "hardware_id";
  public static final String KEY_IDS = "software_id";
  public static final String KEY_HAR = "hardware";
  public static final String KEY_SOF = "software";
  private final static int FILECHOOSER_RESULTCODE = 1;
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  @BindView(R.id.tvticketcategory)
  TextView tvticketcategory;
  @BindView(R.id.rcvchecklist)
  RecyclerView rcvchecklist;
  @BindView(R.id.lytspnengineer)
  TextInputLayout lytspnengineer;
  @BindView(R.id.rcvchecklistvisit)
  RecyclerView rcvchecklistvisit;
  @BindView(R.id.textInputEditTextvisit)
  TextInputEditText textInputEditTextvisit;
  @BindView(R.id.lytpm)
  TextInputLayout lytpm;
  @BindView(R.id.lytvisit)
  TextInputLayout lytvisit;
  @BindView(R.id.btnStart)
  Button btnStart;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  @BindView(R.id.tvstatusalat)
  TextView tvstatusalat;
  @BindView(R.id.tvhours)
  EditText tvhours;
  @BindView(R.id.tvminute)
  EditText tvminute;
  private AdapterSpinnerPMInstrument adapterSpinnerPMInstrument;
  private AdapterChecklistPM adapterChecklistPM;
  private AdapterChecklistVisit adapterChecklistVisit;
  private String accessToken;
  private String idtiket;
  private Bitmap bitmap;
  private File file = null;
  boolean is_empty = false;
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
  private String ticket_type;
  private String id_customer;
  private String activity_id;
  private String staff_name;
  private String staff_phonenumber;
  private String instrument_type;
  private String instrument;
  private String priority;
  private String number;
  private String customer_name;
  private String contract;
  private String description;
  private String it_category;
  private String hardware_id;
  private String software_id;
  private String hardware;
  private String software;

  private BodyChecklist bodyChecklist;
  private BodyChecklistVisit bodyChecklistVisit;
  int itemnumberinstrument;
  private ArrayList<ChecklistGroup> listarray = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();
  private ArrayList<ChecklistItems> listbodychecklist = new ArrayList<ChecklistItems>();
  private ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistItem> listarrayvisit = new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistItem>();
  private ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistGroup> listarrayvisitgroup = new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistGroup>();
  private ArrayList<ChecklistItemVisit> listbodychecklistvisit = new ArrayList<ChecklistItemVisit>();
    /*@OnCheckedChanged(R.id.cbSparepart)
    void openAddSparepart(CheckBox checkBox, boolean checked) {
        Intent i = new Intent(getApplicationContext(), Sparepart.class);
        startActivity(i);
    }*/;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getService();
    setContentView(R.layout.activity_detail_on_progresvisit_pm_other);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initData();
    initViewData();
    if (category.equals("Visit")) {
      Utils.showProgress(this).show();
      initRecycleviewVisit();
      rcvchecklist.setVisibility(View.GONE);
      rcvchecklistvisit.setVisibility(View.VISIBLE);
      lytvisit.setVisibility(View.VISIBLE);
      lytpm.setVisibility(View.GONE);
      initDataVisit();
    }
    if (category.equals("PM")) {
      initRecycleview();
      rcvchecklistvisit.setVisibility(View.GONE);
      rcvchecklist.setVisibility(View.VISIBLE);
      lytvisit.setVisibility(View.GONE);
      lytpm.setVisibility(View.VISIBLE);
    }
    bodyChecklistVisit = new BodyChecklistVisit();
    bodyChecklist = new BodyChecklist();
    Log.e("onCreate", "DetailOnProgresvisitPmOther" + bodyChecklist.toString());
    initActionbar();
    initSpinnerInstrument();
  }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {}
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {}
    if (getIntent() != null) {
      ticket_type = getIntent().getStringExtra(KEY_TICK);
    } else {}
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {}
    if (getIntent() != null) {
      activity_id = getIntent().getStringExtra(KEY_ACTI);
    } else {}
    if (getIntent() != null) {
      staff_name= getIntent().getStringExtra(KEY_SNAME);
    } else {}
    if (getIntent() != null) {
      staff_phonenumber = getIntent().getStringExtra(KEY_SPHN);
    } else {}
    if (getIntent() != null) {
      instrument_type = getIntent().getStringExtra(KEY_INST);
    } else {}
    if (getIntent() != null) {
      instrument = getIntent().getStringExtra(KEY_INS);
    } else {}
    if (getIntent() != null) {
      priority = getIntent().getStringExtra(KEY_PRIO);
    } else {}
    if (getIntent() != null) {
      number = getIntent().getStringExtra(KEY_NUM);
    } else {}
    if (getIntent() != null) {
      customer_name = getIntent().getStringExtra(KEY_CUSTN);
    } else {}
    if (getIntent() != null) {
      contract = getIntent().getStringExtra(KEY_CONT);
    } else {}
    if (getIntent() != null) {
      description = getIntent().getStringExtra(KEY_DESC);
    } else {}
    if (getIntent() != null) {
      it_category = getIntent().getStringExtra(KEY_CIT);
    } else {}
    if (getIntent() != null) {
      hardware_id = getIntent().getStringExtra(KEY_IDI);
    } else {}
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_IDS);
    } else {}
    if (getIntent() != null) {
      hardware = getIntent().getStringExtra(KEY_HAR);
    } else {}
    if (getIntent() != null) {
      software = getIntent().getStringExtra(KEY_SOF);
    } else {}
  }

  private void initRecycleviewVisit() {
    rcvchecklistvisit.setHasFixedSize(true);
    rcvchecklistvisit.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvchecklistvisit.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvchecklistvisit.setNestedScrollingEnabled(false);
  }

  private void initRecycleview() {
    rcvchecklist.setHasFixedSize(true);
    rcvchecklist.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvchecklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvchecklist.setNestedScrollingEnabled(false);
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
          Utils.dismissProgress();
          adapterSpinnerPMInstrument.UpdateOption(responseSpinnerPMInstrument1.getData());
        }
        , throwable -> {
          Utils.dismissProgress();
        });
  }

  @OnClick(R.id.btncamera)
  void openCamera(View view) {
    getCameraClick();
  }

  @OnClick(R.id.btnStart)
  void ConfirmTiket() {
    Utils.showProgress(this).show();
    if (is_empty == true) {
      if (category.equals("PM")) {
        onEndClickPM();
        uploadimage();
      }
      if (category.equals("Visit")) {
        OnEndClickVisit();
        uploadimage();
      }
    } else {
      getCameraClick();
    }
  }


  private void initDataVisit() {
    adapterChecklistVisit = new AdapterChecklistVisit(
        new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistItem>(
            0), getApplicationContext(), new AdapterChecklistVisit.onCheckboxchecked() {
      @Override
      public void onCheckboxcheckedlistener(int id, int id_checklist_group, Boolean is_checked,
          String description) {
        Log.e("id", "DetailOnProgresvisitPmOther" + id);
        Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
        Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
        Log.e("onChecktext", "DetailOnProgresvisitPmOther" + description);
        ChecklistItemVisit cliv = new ChecklistItemVisit();
        cliv.setChecklistItemId(String.valueOf(id));
        cliv.setCheklistGroupId(String.valueOf(id_checklist_group));
        cliv.setValue(is_checked);
        cliv.setNote(description);
        listbodychecklistvisit.add(cliv);
        bodyChecklistVisit.setData(listbodychecklistvisit);
      }
    });
    Observable<ResponseVisit> getvisitchecklist = mApi
        .getvisitchecklist(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getvisitchecklist.subscribe(responseVisit -> {
          for (int i = 0; i < responseVisit.getData().getChecklistGroup().size(); i++) {
            id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistGroup chg = new id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistGroup();
            chg.setName(responseVisit.getData().getChecklistGroup().get(i).getName());
            chg.setId(responseVisit.getData().getChecklistGroup().get(i).getId());
            listarrayvisitgroup.add(chg);
            for (int j = 0;
                j < responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().size();
                j++) {
              id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistItem chi = new id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ChecklistItem();
              chi.setName(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              chi.setId(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getId());
              chi.setChecklistGroupId(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklistGroupId());
              listarrayvisit.add(chi);
            }
            Log.e("initDataVisit", "DetailOnProgresvisitPmOther" + listarrayvisit);
          }
          Utils.dismissProgress();
          adapterChecklistVisit.UpdateTikets(listarrayvisit);
          bodyChecklistVisit.setChecklistId(responseVisit.getData().getId());
        }
        , throwable -> {
          Utils.dismissProgress();
        });
    rcvchecklistvisit.setAdapter(adapterChecklistVisit);
  }

  private void OnEndClickVisit() {
    bodyChecklistVisit.setNotes(textInputEditTextvisit.getText().toString());
    bodyChecklistVisit.setInstrumentId(itemnumberinstrument);
    Observable<ResponseChecklist> updatechecklistend = mApi
        .updatechecklistvisit(accessToken, idtiket, bodyChecklistVisit)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    updatechecklistend.subscribe(
        responseOnProgressEnd -> {
          onBackPressed();
          Utils.dismissProgress();
        }
        , throwable -> {
          Utils.dismissProgress();
        });
  }

  private void getDataChecklist() {
    adapterChecklistPM = new AdapterChecklistPM
        (new ArrayList<ChecklistItem>(0), getApplicationContext(), new onCheckboxchecked() {
          @Override
          public void onCheckboxcheckedlistener(int id, int id_checklist_group,
              Boolean is_checked) {
            Log.e("id", "DetailOnProgresvisitPmOther" + id);
            Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
            Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
            ChecklistItems checklistItems = new ChecklistItems();
            checklistItems.setChecklistItemId(String.valueOf(id));
            checklistItems.setCheklistGroupId(String.valueOf(id_checklist_group));
            checklistItems.setValue(is_checked);
            listbodychecklist.add(checklistItems);
            bodyChecklist.setData(listbodychecklist);
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
        for (int j = 0;
            j < responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().size();
            j++) {
          ChecklistItem chi = new ChecklistItem();
          chi.setName(
              responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getName());
          chi.setId(responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
              .getId());
          chi.setChecklistGroupId(
              responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getChecklistGroupId());
          listarrayitem.add(chi);
        }
      }
      Utils.dismissProgress();
      Log.e("getDataChecklist", "DetailOnProgresvisitPmOther" + listarray);
      adapterChecklistPM.UpdateTikets(listarrayitem);
    }, throwable -> {
      Utils.dismissProgress();
    });
    rcvchecklist.setAdapter(adapterChecklistPM);
  }

  private void onEndClickPM() {
    bodyChecklist.setNotes(textInputEditText.getText().toString());
    Observable<ResponseChecklist> updatechecklistend = mApi
        .updatechecklist(accessToken, idtiket, bodyChecklist).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    updatechecklistend.subscribe(
        responseOnProgressEnd -> {
          onBackPressed();
          Utils.dismissProgress();
          }
        , throwable -> {
          Utils.dismissProgress();
        });
  }

  private void initViewData() {
    tvnamaanalis.setText(staff_name);
    tvnotelp.setText(staff_phonenumber);
    tvurgency.setText(priority);
    tvnumber.setText(number);
    tvDescTiket.setText(description);
    tvticketcategory.setText(category);
    tvstatusalat.setText(contract);
  }

  private void getCameraClick() {
    Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
    startActivityForResult(chooseImageIntent, FILECHOOSER_RESULTCODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case FILECHOOSER_RESULTCODE:
        onFileChooserResult(resultCode, data);
        break;
    }
  }

  private void onFileChooserResult(int resultCode, Intent data) {
    data = null;
    if (resultCode == RESULT_OK) {
      bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
      ImageView view = findViewById(R.id.imgcapture);
      view.setImageBitmap(bitmap);
      is_empty = true;
      btnStart.setBackgroundResource(R.color.colorPrimary);
    } else {
      is_empty = false;
      btnStart.setBackgroundResource(R.color.colorGray);
    }
  }

  private void uploadimage() {
    Part body = null;
    if (file != null) {
      RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
      body = Part.createFormData("image", file.getName(), requestBody);
    }
    Observable<RequestBody> requestBodyImage = mApi
        .updateimage(accessToken, idtiket, body)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    requestBodyImage.subscribe(requestBody -> {
      Utils.dismissProgress();
    }, throwable -> {
      Utils.dismissProgress();
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
    getSupportFragmentManager().findFragmentByTag("ended");
    finish();
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Datum selectediteminstrument = (Datum) adapterView.getItemAtPosition(i);
    itemnumberinstrument = selectediteminstrument.getInstrumentTypeId();
    if (category.equals("PM")) {
      Utils.showProgress(this).show();
      getDataChecklist();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

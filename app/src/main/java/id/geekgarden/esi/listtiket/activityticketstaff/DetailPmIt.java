package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistPmIt;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistPmIt.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.BodyChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.ChecklistItemVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ResponseChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpmit.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpmit.ChecklistItem;
import id.geekgarden.esi.helper.ImagePicker;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPmIt extends AppCompatActivity {

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
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvnotelp)
  TextView tvnotelp;
  @BindView(R.id.tvurgency)
  TextView tvurgency;
  @BindView(R.id.tvticketcategory)
  TextView tvticketcategory;
  @BindView(R.id.tvnumber)
  TextView tvnumber;
  @BindView(R.id.tvkategori)
  TextView tvkategori;
  @BindView(R.id.tvstatusalat)
  TextView tvstatusalat;
  @BindView(R.id.tvhours)
  EditText tvhours;
  @BindView(R.id.tvminute)
  EditText tvminute;
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  @BindView(R.id.rcvchecklist)
  RecyclerView rcvchecklist;
  @BindView(R.id.textInputEditTextvisit)
  TextInputEditText textInputEditTextvisit;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  private final static int FILECHOOSER_RESULTCODE = 1;
  @BindView(R.id.btnStart1)
  Button btnStart1;
  @BindView(R.id.imageView6)
  ImageView imageView6;
  @BindView(R.id.btnStart)
  LinearLayout btnStart;
  private Bitmap bitmap;
  private File file = null;
  boolean is_empty = false;
  private String accessToken;
  private String idtiket;
  private String supervisor;
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
  private String id_division;
  private ActionBar actionBar;
  private AdapterChecklistPmIt adapterChecklistPmIt;
  private Api mApi;
  private GlobalPreferences glpref;
  private BodyChecklistVisit bodyChecklistVisit = new BodyChecklistVisit();
  private ArrayList<ChecklistGroup> listarray = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();
  private ArrayList<ChecklistItemVisit> listbodychecklist = new ArrayList<ChecklistItemVisit>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_on_progrespm_it);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(this);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initData();
    initRecycleview();
    initActionbar();
    initViewData();
    getDataChecklist();
  }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {
    }
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {
    }
    if (getIntent() != null) {
      ticket_type = getIntent().getStringExtra(KEY_TICK);
    } else {
    }
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {
    }
    if (getIntent() != null) {
      activity_id = getIntent().getStringExtra(KEY_ACTI);
    } else {
    }
    if (getIntent() != null) {
      staff_name = getIntent().getStringExtra(KEY_SNAME);
    } else {
    }
    if (getIntent() != null) {
      staff_phonenumber = getIntent().getStringExtra(KEY_SPHN);
    } else {
    }
    if (getIntent() != null) {
      instrument_type = getIntent().getStringExtra(KEY_INST);
    } else {
    }
    if (getIntent() != null) {
      instrument = getIntent().getStringExtra(KEY_INS);
    } else {
    }
    if (getIntent() != null) {
      priority = getIntent().getStringExtra(KEY_PRIO);
    } else {
    }
    if (getIntent() != null) {
      number = getIntent().getStringExtra(KEY_NUM);
    } else {
    }
    if (getIntent() != null) {
      customer_name = getIntent().getStringExtra(KEY_CUSTN);
    } else {
    }
    if (getIntent() != null) {
      contract = getIntent().getStringExtra(KEY_CONT);
    } else {
    }
    if (getIntent() != null) {
      description = getIntent().getStringExtra(KEY_DESC);
    } else {
    }
    if (getIntent() != null) {
      it_category = getIntent().getStringExtra(KEY_CIT);
    } else {
    }
    if (getIntent() != null) {
      hardware_id = getIntent().getStringExtra(KEY_IDI);
    } else {
    }
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_IDS);
    } else {
    }
    if (getIntent() != null) {
      hardware = getIntent().getStringExtra(KEY_HAR);
    } else {
    }
    if (getIntent() != null) {
      software = getIntent().getStringExtra(KEY_SOF);
    } else {
    }
    Utils.showProgress(this).show();
  }

  private void initViewData() {
    tvDescTiket.setText(description);
    tvnamaanalis.setText(staff_name);
    tvnotelp.setText(staff_phonenumber);
    tvnumber.setText(number);
    tvstatusalat.setText(software + hardware);
    tvticketcategory.setText(category);
    tvurgency.setText(priority);
  }

  private void initRecycleview() {
    rcvchecklist.setHasFixedSize(true);
    rcvchecklist.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvchecklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvchecklist.setNestedScrollingEnabled(false);
  }

  @OnClick(R.id.btncamera)
  void openCamera(View view) {
    getCameraClick();
  }

  @OnClick(R.id.btnStart)
  void ConfirmTiket() {
    if (is_empty == true) {
      Utils.showProgress(this).show();
      uploadimage();
      onEndPmIt();
    } else {
      getCameraClick();
    }
  }

  private void onEndPmIt() {
    bodyChecklistVisit
        .setTravel_time(tvhours.getText().toString() + ":" + tvminute.getText().toString());
    bodyChecklistVisit.setNotes(textInputEditTextvisit.getText().toString());
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
          Utils.showToast(this, "Check Your Connection");
        });
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
      file = ImagePicker.getTempFile(this);
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


  private void getDataChecklist() {
    adapterChecklistPmIt = new AdapterChecklistPmIt(
        new ArrayList<ChecklistItem>(
            0), getApplicationContext(), new onCheckboxchecked() {
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
        listbodychecklist.add(cliv);
        bodyChecklistVisit.setData(listbodychecklist);
        bodyChecklistVisit.setNotes(textInputEditTextvisit.getText().toString());
      }
    });
    mApi.getPmIt(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklistPmIt -> {
          for (int i = 0; i < responseChecklistPmIt.getData().getChecklistGroup().size(); i++) {
            ChecklistGroup chg = new ChecklistGroup();
            chg.setName(responseChecklistPmIt.getData().getChecklistGroup().get(i).getName());
            chg.setId(responseChecklistPmIt.getData().getChecklistGroup().get(i).getId());
            listarray.add(chg);
            for (int j = 0;
                j < responseChecklistPmIt.getData().getChecklistGroup().get(i).getChecklistItem()
                    .size();
                j++) {
              ChecklistItem chi = new ChecklistItem();
              chi.setName(
                  responseChecklistPmIt.getData().getChecklistGroup().get(i).getChecklistItem()
                      .get(j)
                      .getName());
              chi.setId(
                  responseChecklistPmIt.getData().getChecklistGroup().get(i).getChecklistItem()
                      .get(j)
                      .getId());
              chi.setChecklistGroupId(
                  responseChecklistPmIt.getData().getChecklistGroup().get(i).getChecklistItem()
                      .get(j)
                      .getChecklistGroupId());
              listarrayitem.add(chi);
            }
          }
          Utils.dismissProgress();
          adapterChecklistPmIt.UpdateTikets(listarrayitem);
          bodyChecklistVisit.setChecklistId(responseChecklistPmIt.getData().getId());
        }, throwable -> {
          Utils.dismissProgress();
        });
    rcvchecklist.setAdapter(adapterChecklistPmIt);
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail PM IT");
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
    getSupportFragmentManager().findFragmentByTag("progres new");
    finish();
  }
}

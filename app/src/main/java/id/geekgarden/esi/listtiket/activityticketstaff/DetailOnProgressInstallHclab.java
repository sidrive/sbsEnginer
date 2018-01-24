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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistHclab;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistHclab.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.BodyChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.ChecklistItemVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab.ChecklistItem;
import id.geekgarden.esi.helper.ImagePicker;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgressInstallHclab extends AppCompatActivity {

  public static final String KEY_URI = "id";
  public static final String KEY_CAT = "category";
  public static final String KEY_TICK = "ticket_type";
  public static final String KEY_CUST = "id_customer";
  public static final String KEY_ACTI = "activity_id";
  public static final String KEY_SNAME = "staff_name";
  public static final String KEY_SPHN = "staff_phonenumber";
  public static final String KEY_INST = "instrument_type";
  public static final String KEY_INS = "instrument";
  public static final String KEY_PRIO = "priority";
  public static final String KEY_NUM = "number";
  public static final String KEY_CUSTN = "customer_name";
  public static final String KEY_CONT = "contract";
  public static final String KEY_DESC = "description";
  public static final String KEY_CIT = "it_category";
  public static final String KEY_IDI = "hardware_id";
  public static final String KEY_IDS = "software_id";
  public static final String KEY_HAR = "hardware";
  public static final String KEY_SOF = "software";
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvhours)
  EditText tvhours;
  @BindView(R.id.tvminute)
  EditText tvminute;
  @BindView(R.id.rcvcheckpmhclab)
  RecyclerView rcvcheckpmhclab;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  @BindView(R.id.btnStart)
  Button btnStart;
  @BindView(R.id.bntHold)
  Button bntHold;
  @BindView(R.id.textInputEditTextvisit)
  TextInputEditText textInputEditTextvisit;
  @BindView(R.id.tvModule)
  TextView tvModule;
  boolean is_empty = false;
  private Bitmap bitmap;
  private File file = null;
  private final static int FILECHOOSER_RESULTCODE = 1;
  private ActionBar actionBar;
  private Api mApi;
  private GlobalPreferences glpref;
  private String Accesstoken;
  private String idtiket;
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
  private AdapterChecklistHclab adapterChecklistHclab;
  private ArrayList<ChecklistGroup> listarrayhclab = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listitemhclab = new ArrayList<ChecklistItem>();
  private BodyChecklistVisit bodyChecklistItHclab = new BodyChecklistVisit();
  ArrayList<ChecklistItemVisit> bodycheckhclab = new ArrayList<ChecklistItemVisit>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_installation_hclab);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    Accesstoken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initData();
    initDataView();
    initRecycleView();
    getChecklistHCLAB();
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

  private void initRecycleView() {
    rcvcheckpmhclab.setHasFixedSize(true);
    rcvcheckpmhclab.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvcheckpmhclab.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvcheckpmhclab.setNestedScrollingEnabled(false);
  }

  @OnClick(R.id.btncamera)
  void openCamera(View view) {
    getCameraClick();
  }

  @OnClick(R.id.bntHold)
  void holdTiket(View view) {
    Utils.showProgress(this).show();
    onholdclick();
  }

  @OnClick(R.id.btnStart)
  void endTiket(View view) {
    if (is_empty == true) {
      uploadimage();
      onendclick();
      Utils.showProgress(this).show();
    } else {
      getCameraClick();
    }
  }

  private void onholdclick() {
    bodyChecklistItHclab.setTravel_time(tvhours.getText().toString()+":"+tvminute.getText().toString());
    bodyChecklistItHclab.setNotes(textInputEditTextvisit.getText().toString());
    mApi.holdchecklistvisit(Accesstoken, idtiket, bodyChecklistItHclab)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            responseOnProgressEnd -> {
              onBackPressed();
              Utils.dismissProgress();
            }
            , throwable -> {
              Utils.dismissProgress();
              Utils.showToast(this,"Check Your Connection");
            });
    Log.e("DetailOnInstallHclab", "onholdclick: " + bodyChecklistItHclab);
  }

  private void onendclick() {
    bodyChecklistItHclab.setTravel_time(tvhours.getText().toString()+":"+tvminute.getText().toString());
    bodyChecklistItHclab.setNotes(textInputEditTextvisit.getText().toString());
    mApi.updatechecklistvisit(Accesstoken, idtiket, bodyChecklistItHclab)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            responseOnProgressEnd -> {
              onBackPressed();
              Utils.dismissProgress();
            }
            , throwable -> {
              Utils.dismissProgress();
              Utils.showToast(this,"Check Your Connection");
            });
  }

  private void getChecklistHCLAB() {
    adapterChecklistHclab = new AdapterChecklistHclab(
        new ArrayList<ChecklistItem>(
            0), getApplicationContext(), new onCheckboxchecked() {
      @Override
      public void onCheckboxcheckedlistener(int id, int id_checklist_group, Boolean is_checked,
          String description, int position, String name) {
        Log.e("id", "DetailOnProgresvisitPmOther" + id);
        Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
        Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
        Log.e("onChecktext", "DetailOnProgresvisitPmOther" + description);
        ChecklistItemVisit bodyhclab = new ChecklistItemVisit();
        bodyhclab.setName(name);
        bodyhclab.setChecklistItemId(String.valueOf(id));
        bodyhclab.setCheklistGroupId(String.valueOf(id_checklist_group));
        bodyhclab.setNote(description);
        bodyhclab.setValue(is_checked);
        bodycheckhclab.remove(position);
        bodycheckhclab.add(position,bodyhclab);
        bodyChecklistItHclab.setData(bodycheckhclab);
        Log.e("onCheckboxchecked", "DetailOnProgressInstallHclab" + bodycheckhclab);
      }
    });
    mApi.getithclab(Accesstoken, software_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseVisit -> {
          bodyChecklistItHclab.setChecklistId(responseVisit.getData().getId());
          for (int i = 0; i < responseVisit.getData().getChecklistGroup().size(); i++) {
            ChecklistGroup chg = new ChecklistGroup();
            chg.setName(responseVisit.getData().getChecklistGroup().get(i).getName());
            chg.setId(responseVisit.getData().getChecklistGroup().get(i).getId());
            listarrayhclab.add(chg);
            for (int j = 0;
                j < responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().size();
                j++) {
              ChecklistItem chi = new ChecklistItem();
              ChecklistItemVisit CIV = new ChecklistItemVisit();
              chi.setName(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              chi.setId(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getId());
              chi.setChecklistGroupId(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklistGroupId());
              CIV.setName(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getName());
              CIV.setNote("");
              CIV.setChecklistItemId(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getId().toString());
              CIV.setCheklistGroupId(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getChecklistGroupId().toString());
              CIV.setValue(false);
              bodycheckhclab.add(CIV);
              listitemhclab.add(chi);
            }
            Log.e("initDataVisit", "DetailOnProgresvisitPmOther" + listitemhclab);
          }
          adapterChecklistHclab.UpdateTikets(listitemhclab);
          Utils.dismissProgress();
        }, throwable -> {
          Utils.dismissProgress();
        });
    rcvcheckpmhclab.setAdapter(adapterChecklistHclab);
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
      file = ImagePicker.getTempFile(this);
      view.setImageBitmap(bitmap);
      is_empty = true;
      btnStart.setBackgroundResource(R.color.colorPrimary);
    } else {
      is_empty = false;
      btnStart.setBackgroundResource(R.color.colorGray);
    }
  }

  private void uploadimage() {
    MultipartBody.Part imageBody = null;
    if (file != null) {
      RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
      imageBody = MultipartBody.Part.createFormData("image", file.getName(), image);
    }
    Observable<RequestBody> requestBodyImage = mApi
        .updateimage(Accesstoken, idtiket, imageBody)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    requestBodyImage.subscribe(requestBody -> {
      Utils.dismissProgress();
    }, throwable -> {
      Utils.dismissProgress();
    });
  }

  private void initDataView() {
    tvnamaanalis.setText(customer_name);
    tvModule.setText(hardware + software);
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Install HCLAB");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}
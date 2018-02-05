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
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistAnalyzer;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistAnalyzer.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.BodyChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.ChecklistItemVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer.ChecklistItem;
import id.geekgarden.esi.data.model.tikets.staffticket.model.loadchecklist.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.loadchecklist.ResponseGetChecklist;
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

public class DetailOnProgressHoldInstallAnalyzer extends AppCompatActivity {

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
  @BindView(R.id.txtGenkey)
  EditText txtGenkey;
  @BindView(R.id.rcvcheckpmanalyzer)
  RecyclerView rcvcheckpmanalyzer;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  @BindView(R.id.btnStart)
  LinearLayout btnStart;
  @BindView(R.id.textInputEditTextvisit)
  TextInputEditText textInputEditTextvisit;
  @BindView(R.id.tvInterface)
  TextView tvInterface;
  @BindView(R.id.bntHold)
  LinearLayout bntHold;
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
  private AdapterChecklistAnalyzer adapterChecklistAnalyzer;
  private ArrayList<ChecklistGroup> listarrayanalyzer =
      new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listitemanalyzer =
      new ArrayList<ChecklistItem>();
  private ArrayList<ChecklistItemVisit> bodycheckanalyzer = new ArrayList<ChecklistItemVisit>();
  private BodyChecklistVisit bodyChecklistItAnalyzer = new BodyChecklistVisit();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_installation_analyzer);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    Accesstoken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initData();
    initDataView();
    initRecycleView();
    getChecklistAnalyzer();
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

  private void initRecycleView() {
    rcvcheckpmanalyzer.setHasFixedSize(true);
    rcvcheckpmanalyzer.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvcheckpmanalyzer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvcheckpmanalyzer.setNestedScrollingEnabled(false);
  }

  private void onholdclick() {
    bodyChecklistItAnalyzer.setNotes(textInputEditTextvisit.getText().toString());
    mApi.holdchecklistvisit(Accesstoken, idtiket, bodyChecklistItAnalyzer)
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

  private void onendclick() {
    bodyChecklistItAnalyzer.setNotes(textInputEditTextvisit.getText().toString());
    mApi.updatechecklistvisit(Accesstoken, idtiket, bodyChecklistItAnalyzer)
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

  private void getChecklistAnalyzer() {
    adapterChecklistAnalyzer = new AdapterChecklistAnalyzer(
        new ArrayList<ChecklistItem>(
            0), getApplicationContext(), new onCheckboxchecked() {
      @Override
      public void onCheckboxcheckedlistener(int id, int id_checklist_group, String name,
          int position, Boolean is_checked,
          String description) {
        Log.e("onChec", "DetailOnProgressHoldInstallAnalyzer" + name);
        Log.e("id", "DetailOnProgresvisitPmOther" + id);
        Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
        Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
        Log.e("onChecktext", "DetailOnProgresvisitPmOther" + description);
        ChecklistItemVisit bodyanalyzer = new ChecklistItemVisit();
        bodyanalyzer.setName(name);
        bodyanalyzer.setChecklistItemId(String.valueOf(id));
        bodyanalyzer.setCheklistGroupId(String.valueOf(id_checklist_group));
        bodyanalyzer.setNote(description);
        bodyanalyzer.setValue(is_checked);
        bodycheckanalyzer.remove(position);
        bodycheckanalyzer.add(position,bodyanalyzer);
        bodyChecklistItAnalyzer.setData(bodycheckanalyzer);
      }
    });
    mApi.getticketchecklist(Accesstoken, idtiket)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseAnalyzer -> {
          bodyChecklistItAnalyzer.setChecklistId(responseAnalyzer.getData().getChecklistId());
          for (int i = 0; i < responseAnalyzer.getData().getData().size(); i++) {
            ChecklistItemVisit dt = new ChecklistItemVisit();
            ChecklistItem ci = new ChecklistItem();
              dt.setChecklistItemId(responseAnalyzer.getData().getData().get(i).getChecklistItemId());
              dt.setCheklistGroupId(responseAnalyzer.getData().getData().get(i).getCheklistGroupId());
              dt.setName(responseAnalyzer.getData().getData().get(i).getName());
              dt.setNote(responseAnalyzer.getData().getData().get(i).getNote());
              dt.setValue(responseAnalyzer.getData().getData().get(i).getValue());
            ci.setId(Integer.parseInt(responseAnalyzer.getData().getData().get(i).getChecklistItemId()));
            ci.setChecklistGroupId(Integer.parseInt(responseAnalyzer.getData().getData().get(i).getCheklistGroupId()));
            ci.setName(responseAnalyzer.getData().getData().get(i).getName());
            ci.setNote(responseAnalyzer.getData().getData().get(i).getNote());
            ci.setIschecked(responseAnalyzer.getData().getData().get(i).getValue());
              bodycheckanalyzer.add(dt);
              listitemanalyzer.add(ci);
            }
          Utils.dismissProgress();
          adapterChecklistAnalyzer.UpdateTikets(listitemanalyzer);
        }, throwable -> {
          Utils.dismissProgress();
        });
    rcvcheckpmanalyzer.setAdapter(adapterChecklistAnalyzer);
  }

  private void initDataView() {
    tvnamaanalis.setText(customer_name);
    tvInterface.setText(hardware + software);
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Installation Analyzer");
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

package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseSparepart;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.Part;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.helper.ImagePicker;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgressHold extends AppCompatActivity implements OnItemSelectedListener {

  private final static int FILECHOOSER_RESULTCODE = 1;
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
  boolean is_empty = false;
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  @BindView(R.id.tvhours)
  EditText tvhours;
  @BindView(R.id.tvminute)
  EditText tvminute;
  @BindView(R.id.spinnerdata)
  Spinner spinnerdata;
  private Bitmap bitmap;
  private File file = null;
  private DatabaseSparepart db;
  @BindView(R.id.rcvreoccurence)
  RecyclerView rcvreoccurence;
  private List<Part> listarray = new ArrayList<Part>();
  @BindView(R.id.tvproblem)
  TextInputEditText tvproblem;
  @BindView(R.id.tvfault)
  TextInputEditText tvfault;
  @BindView(R.id.tvsolution)
  TextInputEditText tvsolution;
  String accessToken;
  String division_id;
  String itemnumber;
  String itemactivity;
  String TicketRelationID;
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
  private AdapterSpinnerOnProgress adapterSpinnerOnProgress;
  @BindView(R.id.ckbsparepart)
  CheckBox ckbsparepart;
  private Api mApi;
  private GlobalPreferences glpref;
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvnohp)
  TextView tvnohp;
  @BindView(R.id.tvLabelalat)
  TextView tvlabelalat;
  @BindView(R.id.tvtipealat)
  TextView tvtipealat;
  @BindView(R.id.tvurgency)
  TextView tvurgency;
  @BindView(R.id.tvnumber)
  TextView tvnumber;
  @BindView(R.id.tvnamacustomer)
  TextView tvnamacustomer;
  @BindView(R.id.tvcategory)
  TextView tvcategory;
  @BindView(R.id.tvstatusalat)
  TextView tvstatusalat;
  private ActionBar actionBar;
  @BindView(R.id.bntHold)
  Button bntHold;
  @BindView(R.id.btnEnd)
  Button btnEnd;
  @BindView(R.id.btncamera)
  Button btncamera;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getService();
    setContentView(R.layout.activity_onprogress_service_hold);
    ButterKnife.bind(this);
    db = new DatabaseSparepart(getApplicationContext());
    rcvreoccurence.setVisibility(View.GONE);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    division_id = glpref.read(PrefKey.division_id, String.class);
    Log.e("onCreate", "DetailOnProgressHold" + division_id);
    idtiket = getIntent().getStringExtra(KEY_URI);
    init();
    initActionBar();
    initData();
    initviewdata();
  }

  private void init() {
    if (division_id.equals("3") || division_id.equals("4")) {
      spinnerdata.setVisibility(View.VISIBLE);
      getdataspinner();
      initSpinner();
    } else {
      spinnerdata.setVisibility(View.GONE);
    }
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

  @OnClick(R.id.btnEnd)
  void endTiket(View view) {
    Utils.showProgress(this).show();
    if (is_empty == true) {
      uploadimage();
      onendclick();
    } else {
      getCameraClick();
    }
  }

  private void initviewdata() {
    tvnamaanalis.setText(staff_name);
    tvnohp.setText(staff_phonenumber);
    tvurgency.setText(priority);
    tvnumber.setText(number);
    tvnamacustomer.setText(customer_name);
    tvstatusalat.setText(contract);
    tvDescTiket.setText(description);
    Utils.dismissProgress();
  }

  private void initTypeDeviceDueDivision(){
    if(division_id.equals("3")){
      tvlabelalat.setText("Device");
      tvtipealat.setText(software+hardware);
    }
    tvtipealat.setText(instrument_type);
    Log.e("division","id_divison"+division_id);
    Log.e("software","software"+software);
    Log.e("hardware","hardware"+hardware);
    Utils.dismissProgress();
  }

  private void onholdclick() {
    for (int i = 0; i < db.getAllSparepart().size(); i++) {
      Part sp = new Part();
      sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
      sp.setDescription(db.getAllSparepart().get(i).getDescription());
      sp.setQuantity(db.getAllSparepart().get(i).getQty());
      sp.setStatus(db.getAllSparepart().get(i).getStatus());
      sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
      listarray.add(sp);
    }
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    BodyOnProgress bodyOnProgress = new BodyOnProgress();
    bodyOnProgress.setProblem(tvproblem.getText().toString());
    bodyOnProgress.setFaultDescription(tvfault.getText().toString());
    bodyOnProgress.setSolution(tvsolution.getText().toString());
    bodyOnProgress.setTicketActivityId(activity_id);
    bodyOnProgress.setParts(listarray);
    if (TextUtils.isEmpty(tvproblem.getText().toString())) {
      tvproblem.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    if (TextUtils.isEmpty(tvfault.getText().toString())) {
      tvfault.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    if (TextUtils.isEmpty(tvsolution.getText().toString())) {
      tvsolution.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    Observable<ResponseOnProgress> respononprogress = mApi
        .updateonholdtiket(accessToken, idtiket, bodyOnProgress)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    respononprogress.subscribe(responseOnProgress -> {
      Utils.dismissProgress();
      DatabaseSparepart db = new DatabaseSparepart(getApplicationContext());
      db.deleteAllsparepart(new SQLiteSparepart());
      onBackPressed();
    }, throwable -> {
      Utils.dismissProgress();
    });
  }

  private void onendclick() {
    for (int i = 0; i < db.getAllSparepart().size(); i++) {
      Part sp = new Part();
      sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
      sp.setDescription(db.getAllSparepart().get(i).getDescription());
      sp.setQuantity(db.getAllSparepart().get(i).getQty());
      sp.setStatus(db.getAllSparepart().get(i).getStatus());
      sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
      listarray.add(sp);
    }
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {
    }
    BodyOnProgress bodyOnProgress = new BodyOnProgress();
    bodyOnProgress.setProblem(tvproblem.getText().toString());
    bodyOnProgress.setFaultDescription(tvfault.getText().toString());
    bodyOnProgress.setSolution(tvsolution.getText().toString());
    bodyOnProgress.setTicketActivityId(activity_id);
    if (division_id.equals("3") || division_id.equals("4")) {
      bodyOnProgress.setTicketActivityId(itemactivity);
    } else {
      bodyOnProgress.setTicketActivityId(activity_id);
    }
    bodyOnProgress.setParts(listarray);
    Log.e("", "onendclick: " + listarray);
    if (TextUtils.isEmpty(tvproblem.getText().toString())) {
      tvproblem.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    if (TextUtils.isEmpty(tvfault.getText().toString())) {
      tvfault.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    if (TextUtils.isEmpty(tvsolution.getText().toString())) {
      tvsolution.setError("This");
      Utils.showToast(getApplicationContext(), "Please Fill Empty Data");
    }
    Observable<ResponseOnProgressEnd> respononprogressend = mApi
        .updateonendtiket(accessToken, idtiket, bodyOnProgress)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    respononprogressend.subscribe(responseOnProgressEnd -> {
      Utils.dismissProgress();
      db.deleteAllsparepart(new SQLiteSparepart());
      onBackPressed();
    }, throwable -> {
      Utils.dismissProgress();
    });
  }

  private void initSpinner() {
    Spinner spinner = findViewById(R.id.spinnerdata);
    spinner.setOnItemSelectedListener(this);
    adapterSpinnerOnProgress = new AdapterSpinnerOnProgress(this,
        android.R.layout.simple_spinner_item, new ArrayList<Datum>());
    adapterSpinnerOnProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapterSpinnerOnProgress);
  }

  private void getdataspinner() {
    Observable<Responsespinneronprogress> responspinneronprogress = mApi
        .getSpinneronprogress(accessToken, division_id)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responspinneronprogress.subscribe(responsespinneronprogress ->
            adapterSpinnerOnProgress.UpdateOption(responsespinneronprogress.getData())
        , throwable -> {
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
      btnEnd.setBackgroundResource(R.color.colorPrimary);
    } else {
      is_empty = false;
      btnEnd.setBackgroundResource(R.color.colorGray);
    }
  }

  private void uploadimage() {
    MultipartBody.Part body = null;
    if (file != null) {
      RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
      body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
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

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("On Progress Hold");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick({R.id.bntHold, R.id.btnEnd})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.bntHold:
        break;
      case R.id.btnEnd:
        break;
    }
  }

  @OnClick(R.id.ckbsparepart)
  public void onViewClicked() {
    boolean checked = ckbsparepart.isChecked();
    switch (ckbsparepart.getId()) {
      case R.id.ckbsparepart:
        if (checked) {
          Intent i = new Intent(getApplicationContext(), Sparepart.class);
          startActivity(i);
        } else {

        }
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    getSupportFragmentManager().findFragmentByTag("progres hold");
    finish();
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Datum selecteditem = (Datum) adapterView.getItemAtPosition(i);
    Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
    itemnumber = selecteditem.getId().toString();
    itemactivity = selecteditem.getName();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

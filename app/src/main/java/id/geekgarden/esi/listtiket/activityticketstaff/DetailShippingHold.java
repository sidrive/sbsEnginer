package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping.BodyShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.ChecklistItemVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistItem;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistTiket;
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

public class DetailShippingHold extends AppCompatActivity {

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
  @BindView(R.id.rcvshipping)
  RecyclerView rcvshipping;
  @BindView(R.id.tvnoteshipping)
  TextInputEditText tvnoteshipping;
  @BindView(R.id.lytnoteshipping)
  TextInputLayout lytnoteshipping;
  @BindView(R.id.tvhours)
  EditText tvhours;
  @BindView(R.id.tvminute)
  EditText tvminute;
  @BindView(R.id.btnHold)
  Button btnHold;
  private Bitmap bitmap;
  private File file = null;
  boolean is_empty = false;
  private AdapterChecklistShipping adapterChecklistShipping;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  @BindView(R.id.btnStart)
  Button btnStart;
  private ActionBar actionBar;
  private String accessToken;
  private Api mApi;
  private GlobalPreferences glpref;
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
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();
  private ArrayList<Datum> listarraybody = new ArrayList<Datum>();
  private BodyShipping bodyShipping = new BodyShipping();
  Datum datum = new Datum();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_shipping_checklist);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initActionbar();
    initData();
    initRecycleview();
    getDataShippingChecklist();
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

  @OnClick(R.id.btnStart)
  void ConfirmTiket() {
    if (is_empty == true) {
      updateDataShipping();
      uploadimage();
      Utils.showProgress(this).show();
    } else {
      getCameraClick();
    }
  }

  @OnClick(R.id.btnHold)
  void HoldTiket() {
    Utils.showProgress(this).show();
    holdDataShipping();
  }

  private void holdDataShipping() {
    bodyShipping.setNotes(tvnoteshipping.getText().toString());
    bodyShipping.setTravel_time(tvhours.getText().toString()+":"+tvminute.getText().toString());
    mApi.holdshippingchecklist(accessToken, idtiket, bodyShipping)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklist -> {
          Utils.dismissProgress();
          onBackPressed();
          finish();
        }, throwable -> {
          Utils.dismissProgress();
          Utils.showToast(this,"Check Your Connection");
        });
  }

  private void updateDataShipping() {
    bodyShipping.setNotes(tvnoteshipping.getText().toString());
    bodyShipping.setTravel_time(tvhours.getText().toString()+":"+tvminute.getText().toString());
    mApi.updateshippingchecklist(accessToken, idtiket, bodyShipping)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklist -> {
          Utils.dismissProgress();
          onBackPressed();
          finish();
        }, throwable -> {
          Utils.dismissProgress();
          Utils.showToast(this,"Check Your Connection");
        });
  }

  private void initRecycleview() {
    rcvshipping.setHasFixedSize(true);
    rcvshipping.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
    rcvshipping.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvshipping.setNestedScrollingEnabled(false);
  }

  private void getDataShippingChecklist() {
    adapterChecklistShipping = new AdapterChecklistShipping(new ArrayList<ChecklistItem>(0),
        getApplicationContext(),
        (id, id_checklist_group, name, is_checked, partno, qty, position) -> {
          Log.e("getDataShipping", "DetailShipping" + id);
          Log.e("getDataShipping", "DetailShipping" + id_checklist_group);
          Log.e("getDataShipping", "DetailShipping" + is_checked);
          Log.e("getDataShipping", "DetailShipping" + partno);
          Log.e("getDataShipping", "DetailShipping" + qty);
          Log.e("getDataShipping", "DetailShipping" + position);
          Datum datumshipping = new Datum();
          datumshipping.setChecklistItemId(String.valueOf(id));
          datumshipping.setName(name);
          datumshipping.setCheklistGroupId(String.valueOf(id_checklist_group));
          datumshipping.setPartNo(partno);
          datumshipping.setQuantity(qty);
          datumshipping.setValue(is_checked);
          listarraybody.remove(position);
          listarraybody.add(position, datumshipping);
          bodyShipping.setData(listarraybody);
          Log.e("DetailShipping", "getDataShippingChecklist: " + bodyShipping.toString());
        });
    Utils.dismissProgress();
    mApi.getticketchecklistinstall(accessToken, idtiket)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklist -> {
          bodyShipping.setChecklistId(responseChecklist.getData().getChecklistId());
          tvnoteshipping.setText(responseChecklist.getData().getNotes());
          for (int i = 0; i < responseChecklist.getData().getData().size(); i++) {
            Datum dt = new Datum();
            ChecklistItem ci = new ChecklistItem();
            dt.setChecklistItemId(responseChecklist.getData().getData().get(i).getChecklistItemId());
            dt.setCheklistGroupId(responseChecklist.getData().getData().get(i).getCheklistGroupId());
            dt.setName(responseChecklist.getData().getData().get(i).getName());
            dt.setPartNo(responseChecklist.getData().getData().get(i).getPartNo());
            dt.setQuantity(responseChecklist.getData().getData().get(i).getQuantity());
            dt.setValue(responseChecklist.getData().getData().get(i).getValue());
            ci.setId(Integer.parseInt(responseChecklist.getData().getData().get(i).getChecklistItemId()));
            ci.setChecklistGroupId(Integer.parseInt(responseChecklist.getData().getData().get(i).getCheklistGroupId()));
            ci.setName(responseChecklist.getData().getData().get(i).getName());
            ci.setPartNo(responseChecklist.getData().getData().get(i).getPartNo());
            ci.setQty(responseChecklist.getData().getData().get(i).getQuantity());
            ci.setValue(responseChecklist.getData().getData().get(i).getValue());
              listarrayitem.add(ci);
              listarraybody.add(dt);
            }
          Utils.dismissProgress();
          adapterChecklistShipping.UpdateTikets(listarrayitem);
        }, throwable -> {
          Utils.dismissProgress();
        });
    rcvshipping.setAdapter(adapterChecklistShipping);

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

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Shipping Checklist");
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    getSupportFragmentManager().findFragmentByTag("ended");
    finish();
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

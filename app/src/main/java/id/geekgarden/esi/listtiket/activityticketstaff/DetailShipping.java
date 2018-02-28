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
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping.BodyShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping.Datum;
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

public class DetailShipping extends AppCompatActivity {

  private final static int FILECHOOSER_RESULTCODE = 1;
  public static final String KEY_URI = "id_ticket";
  public static final String KEY_INSTR = "id_instrument";
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
  @BindView(R.id.bntHold)
  LinearLayout btnHold;
  private Bitmap bitmap;
  private File file = null;
  boolean is_empty = false;
  private AdapterChecklistShipping adapterChecklistShipping;
  @BindView(R.id.btncamera)
  Button btncamera;
  @BindView(R.id.imgcapture)
  ImageView imgcapture;
  @BindView(R.id.btnStart)
  LinearLayout btnStart;
  private ActionBar actionBar;
  private String accessToken;
  private Api mApi;
  private GlobalPreferences glpref;
  private String idtiket;
  private String id_instrument;
  private ArrayList<ChecklistGroup> listarray = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();
  private ArrayList<Datum> listarraybody = new ArrayList<Datum>();
  private ArrayList<ChecklistTiket> listarrayitemtiket = new ArrayList<ChecklistTiket>();
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
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {
    }
    if (getIntent() != null) {
      id_instrument = getIntent().getStringExtra(KEY_INSTR);
    } else {
    }
    initRecycleview();
    getDataShippingChecklist();
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

  @OnClick(R.id.bntHold)
  void HoldTiket() {
    Utils.showProgress(this).show();
    holdDataShipping();
  }

  private void holdDataShipping() {
    bodyShipping.setNotes(tvnoteshipping.getText().toString());
    bodyShipping.setTravel_time(tvhours.getText().toString()+":"+tvminute.getText().toString());
    bodyShipping.setData(listarraybody);
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
    bodyShipping.setData(listarraybody);
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
    Utils.showProgress(this).show();
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
          Log.e("DetailShipping", "getDataShippingChecklist: " + bodyShipping.toString());
        });
    Utils.dismissProgress();

    mApi.getshippingchecklist(accessToken, Integer.parseInt(id_instrument), "SC")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklist -> {
          bodyShipping.setChecklistId(responseChecklist.getData().getId());
          for (int i = 0; i < responseChecklist.getData().getChecklistGroup().size(); i++) {
            ChecklistGroup chg = new ChecklistGroup();
            chg.setName(responseChecklist.getData().getChecklistGroup().get(i).getName());
            chg.setId(responseChecklist.getData().getChecklistGroup().get(i).getId());
            listarray.add(chg);
            for (int j = 0;
                j < responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem()
                    .size();
                j++) {
              ChecklistItem chi = new ChecklistItem();
              Datum dt = new Datum();
              chi.setName(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              chi.setId(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getId());
              chi.setChecklistGroupId(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklistGroupId());
              chi.setPartNo(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getPartNo());
              chi.setQty(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getQty());
              dt.setChecklistItemId(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getId().toString());
              dt.setCheklistGroupId(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklistGroupId().toString());
              dt.setPartNo(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getPartNo());
              dt.setQuantity(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getQty());
              dt.setName(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              dt.setValue(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getValue());
              listarrayitem.add(chi);
              listarraybody.add(dt);
            }
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

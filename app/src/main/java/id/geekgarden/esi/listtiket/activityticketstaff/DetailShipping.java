package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistItem;
import id.geekgarden.esi.helper.ImagePicker;
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
  private Bitmap bitmap;
  private File file = null;
  boolean is_empty = false;
  private AdapterChecklistShipping adapterChecklistShipping;
  @BindView(R.id.tvtraveltime)
  EditText tvtraveltime;
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
  private String id_instrument;
  private ArrayList<ChecklistGroup> listarray = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listarrayitem = new ArrayList<ChecklistItem>();

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

  private void initRecycleview() {
    rcvshipping.setHasFixedSize(true);
    rcvshipping.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvshipping.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
  }

  private void getDataShippingChecklist() {
    adapterChecklistShipping = new AdapterChecklistShipping(new ArrayList<ChecklistItem>(0),
        getApplicationContext(),
        (id, id_checklist_group, is_checked, description, qty) -> {
          Log.e("getDataShipping", "DetailShipping" + id);
          Log.e("getDataShipping", "DetailShipping" + id_checklist_group);
          Log.e("getDataShipping", "DetailShipping" + is_checked);
          Log.e("getDataShipping", "DetailShipping" + description);
          Log.e("getDataShipping", "DetailShipping" + qty);
        });
    mApi.getshippingchecklist(accessToken, Integer.parseInt(id_instrument), "SC")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseChecklist -> {
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
              chi.setName(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              chi.setId(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getId());
              chi.setChecklist_group_id(
                  responseChecklist.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklist_group_id());
              listarrayitem.add(chi);
            }
          }
          adapterChecklistShipping.UpdateTikets(listarrayitem);
        }, throwable -> {
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
    }, throwable -> {
    });
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Shipping Checklist");
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

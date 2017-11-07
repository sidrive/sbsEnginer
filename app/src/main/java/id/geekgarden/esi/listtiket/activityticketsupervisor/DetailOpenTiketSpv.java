package id.geekgarden.esi.listtiket.activityticketsupervisor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
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
import id.geekgarden.esi.data.model.tikets.supervisorticket.adapter.AdapterSpinnerEngineerSpv;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer.Datum;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer.ResponseDivertedID;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.BodyDiverted;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.ResponseDiverted;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOpenTiketSpv extends AppCompatActivity implements OnItemSelectedListener {

  public static String KEY_ID = "id";
  String accessToken;
  String idtiket;
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  @BindView(R.id.spnAssignInto)
  Spinner spnAssignInto;
  private ActionBar actionBar;
  private Api mApi;
  private GlobalPreferences glpref;
  private AdapterSpinnerEngineerSpv adapterSpinnerEngineerSpv;
  private int itemnumberengineer;
  @BindView(R.id.tvNoHp)
  TextView tvNoHp;
  @BindView(R.id.tvTipeAlat)
  TextView tvTipeAlat;
  @BindView(R.id.tvUrgency)
  TextView tvUrgency;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getervice();
    setContentView(R.layout.activity_detail_open_tiket_spv);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_ID);
      Log.e("", "onclickdataupdate: " + idtiket);
    } else {
      Log.e("", "null: ");
    }
    initActionBar();
    initViewData();
    initSpinnerEngineer();
  }

  @OnClick(R.id.btnDiverted)
  void Diverted(View view) {
    OnAssignChange();
  }

  private void initSpinnerEngineer() {
    spnAssignInto.setOnItemSelectedListener(this);
    adapterSpinnerEngineerSpv = new AdapterSpinnerEngineerSpv(this,
        android.R.layout.simple_spinner_item,
        new ArrayList<Datum>());
    adapterSpinnerEngineerSpv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnAssignInto.setAdapter(adapterSpinnerEngineerSpv);
     Observable<ResponseDivertedID> getengineerid = mApi
         .getassignid(accessToken,idtiket)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread());
     getengineerid.subscribe(
         responseDivertedID -> adapterSpinnerEngineerSpv.UpdateOption(responseDivertedID.getData().getAvailableStaff().getData())
     ,throwable -> {});
  }

  private void OnAssignChange() {
    BodyDiverted bodyDiverted = new BodyDiverted();
    bodyDiverted.setStaffId(itemnumberengineer);
    Observable<ResponseDiverted> updatediverted = mApi
        .updateassign(accessToken, idtiket, bodyDiverted)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    updatediverted.subscribe(responseDiverted -> {
      UiUtils.showToast(getApplicationContext(),"Success Divert");
      onBackPressed();}
      , throwable -> {});
  }

  @Override
  public View onCreateView(String name, Context context, AttributeSet attrs) {
    return super.onCreateView(name, context, attrs);
  }

  private void initViewData() {
    Observable<ResponseDetailTiket> responsedetailtiket = mApi
        .detailtiket(accessToken, idtiket)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responsedetailtiket.subscribe(responseDetailTiket -> {
      tvNoHp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
      tvTipeAlat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
      tvUrgency.setText(responseDetailTiket.getData().getPriority());
      tvDescTiket.setText(responseDetailTiket.getData().getDescription());
    }, throwable -> {});
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Tiket Open");
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
    Datum selecteditenengineer = (Datum) adapterView.getItemAtPosition(i);
    itemnumberengineer = selecteditenengineer.getId();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}
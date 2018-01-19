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
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer.Datum;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer.ResponseDivertedID;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.BodyDiverted;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.ResponseDiverted;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOpenTiketSpv extends AppCompatActivity implements OnItemSelectedListener {

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
  String accessToken;
  @BindView(R.id.tvLabelalat)
  TextView tvLabelalat;
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
  private String id_division;
  private String it_category;
  private String hardware_id;
  private String software_id;
  private String hardware;
  private String software;
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
    mApi = ApiService.getService();
    setContentView(R.layout.activity_detail_open_tiket_spv);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    id_division = glpref.read(PrefKey.division_id, String.class);
    initActionBar();
    initData();
    initViewData();
    initSpinnerEngineer();
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
      hardware_id = getIntent().getStringExtra(KEY_HAR);
    } else {
    }
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_SOF);
    } else {
    }
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
    adapterSpinnerEngineerSpv
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnAssignInto.setAdapter(adapterSpinnerEngineerSpv);
    Observable<ResponseDivertedID> getengineerid = mApi
        .getassignid(accessToken, idtiket)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getengineerid.subscribe(
        responseDivertedID -> adapterSpinnerEngineerSpv
            .UpdateOption(responseDivertedID.getData().getAvailableStaff().getData())
        , throwable -> {
        });
  }

  private void OnAssignChange() {
    BodyDiverted bodyDiverted = new BodyDiverted();
    bodyDiverted.setStaffId(itemnumberengineer);
    Observable<ResponseDiverted> updatediverted = mApi
        .updateassign(accessToken, idtiket, bodyDiverted)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    updatediverted.subscribe(responseDiverted -> {
          Utils.showToast(getApplicationContext(), "Success Divert");
          onBackPressed();
        }
        , throwable -> {
        });
  }

  @Override
  public View onCreateView(String name, Context context, AttributeSet attrs) {
    return super.onCreateView(name, context, attrs);
  }

  private void initViewData() {
    tvNoHp.setText(staff_phonenumber);
    tvUrgency.setText(priority);
    tvDescTiket.setText(description);
    initTypeDeviceDueDivision();
  }

  private void initTypeDeviceDueDivision() {
    if (id_division.equals("3")) {
      tvLabelalat.setText("Device");
      tvTipeAlat.setText(software + hardware);
    } else {
      tvTipeAlat.setText(instrument_type);
    }
    Log.e("division", "id_divison" + id_division);
    Log.e("software", "software" + software);
    Log.e("hardware", "hardware" + hardware);
    Utils.dismissProgress();
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
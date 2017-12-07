package id.geekgarden.esi.listtiket.activityticketstaff;

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistAnalyzer;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistAnalyzer.onCheckboxchecked;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistHclab;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistitHclab.BodyChecklistItHclab;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistitanalyzer.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab.ChecklistItem;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
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
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvDate)
  TextView tvDate;
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
  private AdapterChecklistHclab adapterChecklistHclab;
  private ArrayList<ChecklistGroup> listarrayhclab = new ArrayList<ChecklistGroup>();
  private ArrayList<ChecklistItem> listitemhclab = new ArrayList<ChecklistItem>();
  private BodyChecklistItHclab bodyChecklistItHclab = new BodyChecklistItHclab();

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
  }

  private void initRecycleView() {
    rcvcheckpmhclab.setHasFixedSize(true);
    rcvcheckpmhclab.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvcheckpmhclab.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rcvcheckpmhclab.setNestedScrollingEnabled(false);
  }

  private void getChecklistHCLAB() {
    adapterChecklistHclab = new AdapterChecklistHclab(
        new ArrayList<ChecklistItem>(
            0), getApplicationContext(), new AdapterChecklistHclab.onCheckboxchecked() {
      @Override
      public void onCheckboxcheckedlistener(int id, int id_checklist_group, Boolean is_checked,
          String description) {
        Log.e("id", "DetailOnProgresvisitPmOther" + id);
        Log.e("id_check_group", "DetailOnProgresvisitPmOther" + id_checklist_group);
        Log.e("check", "DetailOnProgresvisitPmOther" + is_checked);
        Log.e("onChecktext", "DetailOnProgresvisitPmOther" + description);

      }
    });
    mApi.getithclab(Accesstoken, id_customer, "installanz")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseVisit -> {
          for (int i = 0; i < responseVisit.getData().getChecklistGroup().size(); i++) {
            ChecklistGroup chg = new ChecklistGroup();
            chg.setName(responseVisit.getData().getChecklistGroup().get(i).getName());
            chg.setId(responseVisit.getData().getChecklistGroup().get(i).getId());
            listarrayhclab.add(chg);
            for (int j = 0;
                j < responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().size();
                j++) {
              ChecklistItem chi = new ChecklistItem();
              chi.setName(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getName());
              chi.setId(responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                  .getId());
              chi.setChecklistGroupId(
                  responseVisit.getData().getChecklistGroup().get(i).getChecklistItem().get(j)
                      .getChecklistGroupId());
              listitemhclab.add(chi);
            }
            Log.e("initDataVisit", "DetailOnProgresvisitPmOther" + listitemhclab);
          }
          adapterChecklistHclab.UpdateTikets(listitemhclab);
        }, throwable -> {
        });
    rcvcheckpmhclab.setAdapter(adapterChecklistHclab);
  }

  private void initDataView() {
    tvnamaanalis.setText(customer_name);
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Dialihkan");
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

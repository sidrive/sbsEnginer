package id.geekgarden.esi.listtiket.activityticketstaff;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updatestartedtiket.ResponseStartedTiket;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailConfirmedTiket extends AppCompatActivity {
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
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  private ActionBar actionBar;
  private Api mApi;
  private GlobalPreferences glpref;
  private String accessToken;
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
  private String id_division;
  private String it_category;
  private String hardware_id;
  private String software_id;
  private String hardware;
  private String software;

    int customer_id;
    String idtiket;
    @BindView(R.id.tvNoHp)
    TextView tvNoHp;
    @BindView(R.id.tvTipeAlat)
    TextView tvTipeAlat;
    @BindView(R.id.tvUrgency)
    TextView tvUrgency;
    @BindView(R.id.btnStart)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getService();
        setContentView(R.layout.activity_detail_confirmed_tiket);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        id_division = glpref.read(PrefKey.division_id,String.class);
        initData();
        initViewData();
        initActionBar();
    }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {}
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {}
    if (getIntent() != null) {
      ticket_type = getIntent().getStringExtra(KEY_TICK);
    } else {}
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {}
    if (getIntent() != null) {
      activity_id = getIntent().getStringExtra(KEY_ACTI);
    } else {}
    if (getIntent() != null) {
      staff_name= getIntent().getStringExtra(KEY_SNAME);
    } else {}
    if (getIntent() != null) {
      staff_phonenumber = getIntent().getStringExtra(KEY_SPHN);
    } else {}
    if (getIntent() != null) {
      instrument_type = getIntent().getStringExtra(KEY_INST);
    } else {}
    if (getIntent() != null) {
      instrument = getIntent().getStringExtra(KEY_INS);
    } else {}
    if (getIntent() != null) {
      priority = getIntent().getStringExtra(KEY_PRIO);
    } else {}
    if (getIntent() != null) {
      number = getIntent().getStringExtra(KEY_NUM);
    } else {}
    if (getIntent() != null) {
      customer_name = getIntent().getStringExtra(KEY_CUSTN);
    } else {}
    if (getIntent() != null) {
      contract = getIntent().getStringExtra(KEY_CONT);
    } else {}
    if (getIntent() != null) {
      description = getIntent().getStringExtra(KEY_DESC);
    } else {}
    if (getIntent() != null) {
      it_category = getIntent().getStringExtra(KEY_CIT);
    } else {}
    if (getIntent() != null) {
      hardware_id = getIntent().getStringExtra(KEY_IDI);
    } else {}
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_IDS);
    } else {}
    if (getIntent() != null) {
      hardware = getIntent().getStringExtra(KEY_HAR);
    } else {}
    if (getIntent() != null) {
      software = getIntent().getStringExtra(KEY_SOF);
    } else {}
  }

  @OnClick(R.id.btnStart)
    void Start(View view) {
        onclickstartdataupdate();
    }

    private void initViewData() {
      tvNoHp.setText(staff_phonenumber);
      tvTipeAlat.setText(instrument_type);
      tvUrgency.setText(priority);
      tvDescTiket.setText(description);
    }

    private void onclickstartdataupdate() {
        Observable<ResponseStartedTiket> responseStartedTiket = mApi
                .updateonstarttiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseStartedTiket.subscribe(responseStartedTiket1 -> {
          if (id_division.equals("3") && category.equals("Installation")) {
            if (it_category.equals("Hardware")) {
              Intent i = new Intent(getApplicationContext(), DetailOnProgressInstallAnalyzer.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              id_customer = String.valueOf(customer_id);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CAT, category);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_ACTI, activity_id);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INS, instrument);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_PRIO, priority);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_NUM, number);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CONT, contract);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_DESC, description);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CIT, it_category);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDI, hardware_id);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDS, software_id);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_HAR, hardware);
              i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SOF, software);
              startActivity(i);
            } else if (it_category.equals("Software")){
              Intent i = new Intent(getApplicationContext(), DetailOnProgressInstallHclab.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              id_customer = String.valueOf(customer_id);
              i.putExtra(DetailOnProgressInstallHclab.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressInstallHclab.KEY_CAT, category);
              i.putExtra(DetailOnProgressInstallHclab.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgressInstallHclab.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgressInstallHclab.KEY_ACTI, activity_id);
              i.putExtra(DetailOnProgressInstallHclab.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgressInstallHclab.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailOnProgressInstallHclab.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgressInstallHclab.KEY_INS, instrument);
              i.putExtra(DetailOnProgressInstallHclab.KEY_PRIO, priority);
              i.putExtra(DetailOnProgressInstallHclab.KEY_NUM, number);
              i.putExtra(DetailOnProgressInstallHclab.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgressInstallHclab.KEY_CONT, contract);
              i.putExtra(DetailOnProgressInstallHclab.KEY_DESC, description);
              i.putExtra(DetailOnProgressInstallHclab.KEY_HAR, hardware);
              i.putExtra(DetailOnProgressInstallHclab.KEY_SOF, software);
              startActivity(i);
            }
          } else if (id_division.equals("3") && category.equals("PM")){
            Intent i = new Intent(getApplicationContext(), DetailPmIt.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            id_customer = String.valueOf(customer_id);
            i.putExtra(DetailPmIt.KEY_URI, idtiket);
            i.putExtra(DetailPmIt.KEY_CAT, category);
            i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
            i.putExtra(DetailPmIt.KEY_CUST, customer_id);
            i.putExtra(DetailPmIt.KEY_ACTI, activity_id);
            i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
            i.putExtra(DetailPmIt.KEY_SPHN, staff_phonenumber);
            i.putExtra(DetailPmIt.KEY_INST, instrument_type);
            i.putExtra(DetailPmIt.KEY_INS, instrument);
            i.putExtra(DetailPmIt.KEY_PRIO, priority);
            i.putExtra(DetailPmIt.KEY_NUM, number);
            i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
            i.putExtra(DetailPmIt.KEY_CONT, contract);
            i.putExtra(DetailPmIt.KEY_DESC, description);
            i.putExtra(DetailPmIt.KEY_HAR, hardware);
            i.putExtra(DetailPmIt.KEY_SOF, software);
            startActivity(i);
          } else if (category.equals("Visit")) {
              Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            id_customer = String.valueOf(customer_id);
              Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + id_customer);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, id_customer);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,activity_id);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, hardware);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, software);
              startActivity(i);
              finish();
            } else
            if (category.equals("PM")) {
                Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              id_customer = String.valueOf(customer_id);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, id_customer);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,activity_id);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, hardware);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, software);
                startActivity(i);
                finish();
            } else
            if (category.equals("Installation")) {
              Intent i = new Intent(getApplicationContext(), DetailInstrumentForm.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              id_customer = String.valueOf(customer_id);
              i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
              i.putExtra(DetailInstrumentForm.KEY_CAT, category);
              i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
              i.putExtra(DetailInstrumentForm.KEY_CUST, id_customer);
              i.putExtra(DetailInstrumentForm.KEY_ACTI, activity_id);
              i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
              i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
              i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
              i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
              i.putExtra(DetailInstrumentForm.KEY_NUM, number);
              i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
              i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
              i.putExtra(DetailInstrumentForm.KEY_DESC, description);
              i.putExtra(DetailInstrumentForm.KEY_HAR, hardware);
              i.putExtra(DetailInstrumentForm.KEY_SOF, software);
              startActivity(i);
              finish();
            } else
              if (category.equals("Return")) {
                Intent i = new Intent(getApplicationContext(), DetailInstrumentForm.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                id_customer = String.valueOf(customer_id);
                i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                i.putExtra(DetailInstrumentForm.KEY_CAT,category);
                i.putExtra(DetailInstrumentForm.KEY_TICK,ticket_type);
                i.putExtra(DetailInstrumentForm.KEY_CUST, id_customer);
                i.putExtra(DetailInstrumentForm.KEY_ACTI,activity_id);
                i.putExtra(DetailInstrumentForm.KEY_SNAME,staff_name);
                i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phonenumber);
                i.putExtra(DetailInstrumentForm.KEY_INST,instrument_type);
                i.putExtra(DetailInstrumentForm.KEY_INS,instrument);
                i.putExtra(DetailInstrumentForm.KEY_PRIO,priority);
                i.putExtra(DetailInstrumentForm.KEY_NUM,number);
                i.putExtra(DetailInstrumentForm.KEY_CUSTN,customer_name);
                i.putExtra(DetailInstrumentForm.KEY_CONT,contract);
                i.putExtra(DetailInstrumentForm.KEY_DESC,description);
                i.putExtra(DetailInstrumentForm.KEY_HAR, hardware);
                i.putExtra(DetailInstrumentForm.KEY_SOF, software);
                startActivity(i);
                finish();
              } else {
                Intent i = new Intent(getApplicationContext(), DetailOnProgressNew.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              id_customer = String.valueOf(customer_id);
              i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressNew.KEY_CAT,category);
              i.putExtra(DetailOnProgressNew.KEY_TICK,ticket_type);
              i.putExtra(DetailOnProgressNew.KEY_CUST, id_customer);
              i.putExtra(DetailOnProgressNew.KEY_ACTI,activity_id);
              i.putExtra(DetailOnProgressNew.KEY_SNAME,staff_name);
              i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phonenumber);
              i.putExtra(DetailOnProgressNew.KEY_INST,instrument_type);
              i.putExtra(DetailOnProgressNew.KEY_INS,instrument);
              i.putExtra(DetailOnProgressNew.KEY_PRIO,priority);
              i.putExtra(DetailOnProgressNew.KEY_NUM,number);
              i.putExtra(DetailOnProgressNew.KEY_CUSTN,customer_name);
              i.putExtra(DetailOnProgressNew.KEY_CONT,contract);
              i.putExtra(DetailOnProgressNew.KEY_DESC,description);
              i.putExtra(DetailOnProgressNew.KEY_HAR, hardware);
              i.putExtra(DetailOnProgressNew.KEY_SOF, software);
              startActivity(i);
              finish();
            }
        }, throwable -> {
            Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + throwable.getMessage());
            if (throwable.getMessage().equals("HTTP 422 Unprocessable Entity")){
                UiUtils.showToast(getApplicationContext(),"You should end your Saba Activity first before starting this ticket");
            }
        });
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Tiket Confirm");
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
        getSupportFragmentManager().findFragmentByTag("confirm");
        finish();
    }
}

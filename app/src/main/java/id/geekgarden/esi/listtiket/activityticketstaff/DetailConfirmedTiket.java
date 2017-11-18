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
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
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
        initData();
        initActionBar();
    }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
      initViewData();
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
          if (category.equals("Visit")) {
              Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + id_customer);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
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
              startActivity(i);
              finish();
            } else
            if (category.equals("PM")) {
                customer_id = responseStartedTiket1.getData().getCustomer().getData().getId();
                Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                id_customer = String.valueOf(customer_id);
                Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + id_customer);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, id_customer);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                startActivity(i);
                finish();
            } else
            if (category.equals("Installation")) {
                customer_id = responseStartedTiket1.getData().getCustomer().getData().getId();
                Intent i = new Intent(getApplicationContext(), DetailInstrumentForm.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                id_customer = String.valueOf(customer_id);
                Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + id_customer);
                i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                i.putExtra(DetailInstrumentForm.KEY_CUST, id_customer);
                i.putExtra(DetailInstrumentForm.KEY_CAT, category);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(getApplicationContext(), DetailOnProgressNew.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
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

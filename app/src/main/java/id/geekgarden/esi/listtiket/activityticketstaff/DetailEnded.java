package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailEnded extends AppCompatActivity {
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
    String accessToken;
    @BindView(R.id.tvreportlink)
    TextView tvreportlink;
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private Api mApi;
    private GlobalPreferences glpref;
    @BindView(R.id.tvnamaanalis)
    TextView tvnamaanalis;
    @BindView(R.id.tvnotelp)
    TextView tvnotelp;
    @BindView(R.id.tvtipealat)
    TextView tvtipealat;
    @BindView(R.id.tvurgency)
    TextView tvurgency;
    @BindView(R.id.tvnumber)
    TextView tvnumber;
    @BindView(R.id.tvsnalat)
    TextView tvsnalat;
    @BindView(R.id.tvkategori)
    TextView tvkategori;
    @BindView(R.id.tvstatusalat)
    TextView tvstatusalat;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getService();
        setContentView(R.layout.activity_detail_ended);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        initActionbar();
        initData();
        initViewData();
        initURLData();
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
    }

    private void initViewData() {
            tvnamaanalis.setText(staff_name);
            tvnotelp.setText(staff_phonenumber);
            tvtipealat.setText(instrument_type);
            tvurgency.setText(priority);
            tvnumber.setText(number);
            tvsnalat.setText(instrument);
            tvstatusalat.setText(contract);
            tvDescTiket.setText(description);
    }

    private void initURLData() {
        mApi.detailtiket(accessToken, idtiket)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(responseDetailTiket -> {
                String url_string = responseDetailTiket.getData().getInvoice();
                tvreportlink.setText(url_string);
            }, throwable -> {});
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Ended");
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
        getSupportFragmentManager().findFragmentByTag("ended");
        finish();
    }

}

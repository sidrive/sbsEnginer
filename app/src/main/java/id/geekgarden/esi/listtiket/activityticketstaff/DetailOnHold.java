package id.geekgarden.esi.listtiket.activityticketstaff;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterOnHoldServiceReport;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.servicereport.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnHold extends AppCompatActivity {
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
    public static final String TAG = DetailOnHold.class.getSimpleName();
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
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    @BindView(R.id.lyt_01)
    LinearLayout lyt01;
    @BindView(R.id.lyt_02)
    LinearLayout lyt02;
    @BindView(R.id.btnStart)
    Button btnStart;
    private List<Datum> listarray1 = new ArrayList<Datum>();
    private AdapterOnHoldServiceReport adapterOnHoldServiceReport;
    private String accessToken;
    private String idtiket;
    @BindView(R.id.rcvservicerpt)
    RecyclerView rcvservicerpt;
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

    Observable<ResponseDetailTiket> responsedetailtiket;
    Observable<ResponseServiceReport> responseServiceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_on_hold);
        ButterKnife.bind(this);
        initAcionbar();
        mApi = ApiService.getService();
        glpref = new GlobalPreferences(this);
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e(TAG, "onCreate: " + accessToken);
        showservicereport(idtiket, accessToken);
        initData();
        initDataView();
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

    @OnClick(R.id.btnStart)
    void ConfirmTiket() {
        resumeclick(idtiket, accessToken);
    }

    private void resumeclick(String idtiket, String accessToken) {
        Log.e("", "onclickdataupdate: " + idtiket);
        Observable<ResponseOnRestart> responseOnRestart = mApi
                .updateonrestarttiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseOnRestart.subscribe(responseOnRestart1 -> {
            Intent i = new Intent(getApplicationContext(), DetailOnProgressHold.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
            startActivity(i);
            finish();
        }, throwable -> {
        });
    }

    private void initDataView() {
            tvnamaanalis.setText(staff_name);
            tvnotelp.setText(staff_phonenumber);
            tvtipealat.setText(instrument_type);
            tvurgency.setText(priority);
            tvnumber.setText(number);
            tvsnalat.setText(instrument);
            tvstatusalat.setText(contract);
            tvDescTiket.setText(description);
    }

    private void showservicereport(String idtiket, String accessToken) {
        adapterOnHoldServiceReport = new AdapterOnHoldServiceReport(new ArrayList<Datum>(0), getApplicationContext(),
                id -> {
                    Log.e(TAG, "onPostClickListener: " + id);
                    String id_ticket_activity = String.valueOf(id);
                    DialogFragment dialogFragment = new TVPartFragment(glpref, mApi, idtiket, id_ticket_activity);
                    dialogFragment.show(getFragmentManager(), "TAG");
                });
        responseServiceReport = mApi
                .getservicereport(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseServiceReport.subscribe(responseServiceReport1 ->
                        adapterOnHoldServiceReport.UpdateTikets(responseServiceReport1.getData())
                , throwable -> {
                });
        rcvservicerpt.setAdapter(adapterOnHoldServiceReport);
        rcvservicerpt.setHasFixedSize(true);
        rcvservicerpt.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rcvservicerpt.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initAcionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail OnHold");
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
        getSupportFragmentManager().findFragmentByTag("hold");
        finish();
    }
}

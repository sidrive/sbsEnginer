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

import id.geekgarden.esi.helper.Utils;
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
    public static final String KEY_CIT = "it_category";
    public static final String KEY_IDI = "hardware_id";
    public static final String KEY_IDS = "software_id";
    public static final String KEY_HAR = "hardware";
    public static final String KEY_SOF = "software";
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
    private String it_category;
    private String hardware_id;
    private String software_id;
    private String hardware;
    private String software;
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
    @BindView(R.id.tvLabelalat)
    TextView tvlabelalat;
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
    String id_division;
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
        id_division = glpref.read(PrefKey.division_id, String.class);
        Log.e(TAG, "onCreate: " + accessToken);
        initData();
        initDataView();
        showservicereport();
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
        Utils.showProgress(this).show();
    }

    @OnClick(R.id.btnStart)
    void ConfirmTiket() {
        Utils.showProgress(this).show();
        resumeclick(idtiket, accessToken);
    }

    private void resumeclick(String idtiket, String accessToken) {
        Log.e("", "onclickdataupdate: " + idtiket);
        Observable<ResponseOnRestart> responseOnRestart = mApi
                .updateonrestarttiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseOnRestart.subscribe(responseOnRestart1 -> {
            Utils.dismissProgress();
            if (id_division.equals("3") && category.equals("Installation")) {
                if (it_category.equals("Hardware")) {
                    Utils.dismissProgress();
                    Intent i = new Intent(getApplicationContext(), DetailOnProgressHoldInstallAnalyzer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CAT, category);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CUST, id_customer);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_ACTI, activity_id);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SNAME, staff_name);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SPHN, staff_phonenumber);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_INST, instrument_type);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_INS, instrument);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_PRIO, priority);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_NUM, number);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CONT, contract);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_DESC, description);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CIT, it_category);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_IDI, hardware_id);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_IDS, software_id);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_HAR, hardware);
                    i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SOF, software);
                    startActivity(i);
                    finish();
                } else if (it_category.equals("Software")){
                    Utils.dismissProgress();
                    Intent i = new Intent(getApplicationContext(), DetailOnProgressInstallHclab.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CAT, category);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CUST, id_customer);
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
                    finish();
                }
            } else if (id_division.equals("3") && category.equals("PM")){
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailPmIt.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailPmIt.KEY_URI, idtiket);
                i.putExtra(DetailPmIt.KEY_CAT, category);
                i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                i.putExtra(DetailPmIt.KEY_CUST, id_customer);
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
                finish();
            } else if (id_division.equals("3") && category.equals("Visit")) {
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailOnProgressVisitIT.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressVisitIT.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressVisitIT.KEY_CAT,category);
                i.putExtra(DetailOnProgressVisitIT.KEY_TICK,ticket_type);
                i.putExtra(DetailOnProgressVisitIT.KEY_CUST, id_customer);
                i.putExtra(DetailOnProgressVisitIT.KEY_ACTI,activity_id);
                i.putExtra(DetailOnProgressVisitIT.KEY_SNAME,staff_name);
                i.putExtra(DetailOnProgressVisitIT.KEY_SPHN, staff_phonenumber);
                i.putExtra(DetailOnProgressVisitIT.KEY_INST,instrument_type);
                i.putExtra(DetailOnProgressVisitIT.KEY_INS,instrument);
                i.putExtra(DetailOnProgressVisitIT.KEY_PRIO,priority);
                i.putExtra(DetailOnProgressVisitIT.KEY_NUM,number);
                i.putExtra(DetailOnProgressVisitIT.KEY_CUSTN,customer_name);
                i.putExtra(DetailOnProgressVisitIT.KEY_CONT,contract);
                i.putExtra(DetailOnProgressVisitIT.KEY_DESC,description);
                i.putExtra(DetailOnProgressVisitIT.KEY_HAR, hardware);
                i.putExtra(DetailOnProgressVisitIT.KEY_SOF, software);
                startActivity(i);
                finish();
            }else if (category.equals("Visit")) {
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailInstrumentForm.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailInstrumentForm.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                Utils.dismissProgress();
                Intent i = new Intent(getApplicationContext(), DetailOnProgressNew.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            Utils.dismissProgress();
            Utils.showToast(this,"Check Your Connection");
        });
    }

    private void initDataView() {
            tvnamaanalis.setText(staff_name);
            tvnotelp.setText(staff_phonenumber);
            tvurgency.setText(priority);
            tvnumber.setText(number);
            tvsnalat.setText(instrument);
            tvstatusalat.setText(contract);
            tvDescTiket.setText(description);
            initTypeDeviceDueDivision();
            Utils.dismissProgress();
    }

    private void initTypeDeviceDueDivision(){
        if(id_division.equals("3")){
            tvlabelalat.setText("Device");
            tvtipealat.setText(software+hardware);
        } else {
            tvtipealat.setText(instrument_type);
        }
        Log.e("division","id_divison"+id_division);
        Log.e("software","software"+software);
        Log.e("hardware","hardware"+hardware);
        Utils.dismissProgress();
    }

    private void showservicereport() {
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
        responseServiceReport.subscribe(responseServiceReport1 -> {
                        Utils.dismissProgress();
                        adapterOnHoldServiceReport.UpdateTikets(responseServiceReport1.getData());}
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

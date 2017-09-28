package id.geekgarden.esi.listtiket.activitymyticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.ServiceReportListener;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterTiketDetailServiceReport;
import id.geekgarden.esi.data.model.tikets.AdapterTiketDetailPart;
import id.geekgarden.esi.data.model.tikets.detailticket.Part;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum_;
import id.geekgarden.esi.data.model.tikets.servicereport.Parts;
import id.geekgarden.esi.data.model.tikets.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnHold extends AppCompatActivity implements ServiceReportListener {
    public static final String KEY_URI = "id_tiket";
    public static final String TAG = DetailOnHold.class.getSimpleName();
    private List<Datum_> listarray = new ArrayList<Datum_>();
    private List<Datum> listarray1 = new ArrayList<Datum>();
    private AdapterTiketDetailPart adapterTiketDetailPart;
    private AdapterTiketDetailServiceReport adapterTiketDetailServiceReport;
    String accessToken;
    String idtiket;
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
    @BindView(R.id.tvdescription)
    TextView tvdescription;
    private ActionBar actionBar;

    @OnClick(R.id.btnStart)
    void ConfirmTiket() {
        resumeclick();
    }

    private void resumeclick() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        } else {
            Log.e("", "null: ");
        }
        Log.e("", "onclickdataupdate: " + idtiket);
        Observable<ResponseOnRestart> responseOnRestart = mApi.updateonrestarttiket(accessToken, idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseOnRestart.subscribe(new Observer<ResponseOnRestart>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseOnRestart responseOnRestart) {
                Log.e("", "onNext: " + responseOnRestart.getData().getStatus().toString());
                Intent i = new Intent(getApplicationContext(), ListTiket.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_on_hold);
        ButterKnife.bind(this);
        /*adapterTiketDetailPart = new AdapterTiketDetailPart(listarray, getApplicationContext());*/
        initAcionbar();
        AdapterTiketDetailServiceReport serviceReport = new AdapterTiketDetailServiceReport(this, listarray, listarray1);
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(this);
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e(TAG, "onCreate: " + accessToken);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e(TAG, "onCreate: " + idtiket);
        }
        showservicereport();
        Observable<ResponseDetailTiket> responsedetailtiket = mApi.detailtiket(accessToken, idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(new Observer<ResponseDetailTiket>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseDetailTiket responseDetailTiket) {
                tvnamaanalis.setText(responseDetailTiket.getData().getStaffName());
                tvnotelp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
                tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
                tvurgency.setText(responseDetailTiket.getData().getPriority());
                tvnumber.setText(responseDetailTiket.getData().getNumber());
                tvsnalat.setText(responseDetailTiket.getData().getInstrument().getData().getSerialNumber());
                tvdescription.setText(responseDetailTiket.getData().getDescription());
                tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
            }
        });
        rcvservicerpt.setAdapter(adapterTiketDetailServiceReport);
        rcvservicerpt.setHasFixedSize(true);
        rcvservicerpt.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rcvservicerpt.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void showservicereport() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(this);
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e(TAG, "onCreate: " + accessToken);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e(TAG, "onCreate: " + idtiket);
        }
        Observable<ResponseServiceReport> responseServiceReport = mApi.getservicereport(accessToken,idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseServiceReport.subscribe(new Observer<ResponseServiceReport>() {
           @Override
           public void onCompleted() {

           }

           @Override
           public void onError(Throwable throwable) {

           }

           @Override
           public void onNext(ResponseServiceReport responseServiceReport) {
               for (int i = 0;i< responseServiceReport.getData().size(); i++) {
                   Datum sr = new Datum();
                   sr.setName(responseServiceReport.getData().get(i).getName());
                   sr.setProblem(responseServiceReport.getData().get(i).getProblem());
                   sr.setFaultDescription(responseServiceReport.getData().get(i).getFaultDescription());
                   sr.setSolution(responseServiceReport.getData().get(i).getSolution());
                   listarray1.add(sr);
                   for (int i1 = 0; i1 < responseServiceReport.getData().get(i).getParts().getData().size(); i1++) {
                       Datum_ sp = new Datum_();
                       sp.setPartNumber(responseServiceReport.getData().get(i).getParts().getData().get(i1).getPartNumber());
                       sp.setDescription(responseServiceReport.getData().get(i).getParts().getData().get(i1).getDescription());
                       sp.setQuantity(responseServiceReport.getData().get(i).getParts().getData().get(i1).getQuantity());
                       sp.setStatus(responseServiceReport.getData().get(i).getParts().getData().get(i1).getStatus());
                       sp.setRemarks(responseServiceReport.getData().get(i).getParts().getData().get(i1).getRemarks());
                       listarray.add(sp);
                   }
               }
               adapterTiketDetailServiceReport.UpdateTikets(listarray1,listarray);
           }
       });
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
        Intent i = new Intent(getApplicationContext(), ListTiket.class);
        startActivity(i);
        finish();
    }

    @Override
    public void loadpart(int idServiceReport) {

    }
}

package id.geekgarden.esi.listtiket.activitymyticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Datum;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.Part;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgressHold extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Part> listarray = new ArrayList<Part>();
    @BindView(R.id.tvproblem)
    TextInputEditText tvproblem;
    @BindView(R.id.tvfault)
    TextInputEditText tvfault;
    @BindView(R.id.tvsolution)
    TextInputEditText tvsolution;
    private AdapterSpinnerOnProgress adapterSpinnerOnProgress;
    private Datum datum = new Datum();
    String itemnumber;
    String accessToken;
    String idtiket;
    @BindView(R.id.ckbsparepart)
    CheckBox ckbsparepart;
    @BindView(R.id.tvtraveltime)
    TextView tvtraveltime;
    private Api mApi;
    private GlobalPreferences glpref;
    public static String KEY_URI = "id";
    @BindView(R.id.tvnamaanalis)
    TextView tvnamaanalis;
    @BindView(R.id.tvnohp)
    TextView tvnohp;
    @BindView(R.id.tvtipealat)
    TextView tvtipealat;
    @BindView(R.id.tvurgency)
    TextView tvurgency;
    @BindView(R.id.tvnumber)
    TextView tvnumber;
    @BindView(R.id.tvnamacustomer)
    TextView tvnamacustomer;
    @BindView(R.id.tvcategory)
    TextView tvcategory;
    @BindView(R.id.tvstatusalat)
    TextView tvstatusalat;
    private ActionBar actionBar;
    @BindView(R.id.bntHold)
    Button bntHold;
    @BindView(R.id.btnEnd)
    Button btnEnd;

    @OnClick(R.id.bntHold)
    void holdTiket(View view) {
        onholdclick();
    }

    @OnClick(R.id.btnEnd)
    void endTiket(View view) {
        onendclick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_onprogress_service_report);
        ButterKnife.bind(this);
        initActionBar();
        initSpinner();
        getdataspinner();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        idtiket = getIntent().getStringExtra(KEY_URI);
        final Observable<ResponseDetailTiket> responsedetailtiket = mApi.detailtiket(accessToken, idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
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
                tvnohp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
                tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
                tvurgency.setText(responseDetailTiket.getData().getPriority());
                tvtraveltime.setText(responseDetailTiket.getData().getTravelTime());
                tvnumber.setText(responseDetailTiket.getData().getNumber());
                tvnamacustomer.setText(responseDetailTiket.getData().getCustomerName());
                tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
            }
        });
    }


    private void getdataspinner() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Observable<Responsespinneronprogress> responspinneronprogress = mApi.getSpinneronprogress(accessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responspinneronprogress.subscribe(new Observer<Responsespinneronprogress>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Responsespinneronprogress responsespinneronprogress) {
                adapterSpinnerOnProgress.UpdateOption(responsespinneronprogress.getData());

            }
        });

    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinnerdata);
        spinner.setOnItemSelectedListener(this);
        adapterSpinnerOnProgress = new AdapterSpinnerOnProgress(this, android.R.layout.simple_spinner_item, new ArrayList<Datum>());
        adapterSpinnerOnProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerOnProgress);
    }

    private void onholdclick() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        DatabaseHandler db = new DatabaseHandler(this);
        for (int i = 0;i< db.getAllSparepart().size(); i++) {
            Part sp = new Part();
            sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
            sp.setDescription(db.getAllSparepart().get(i).getDescription());
            sp.setQuantity(db.getAllSparepart().get(i).getQty());
            sp.setStatus(db.getAllSparepart().get(i).getStatus());
            sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
            listarray.add(sp);
        }
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        } else {
            Log.e("", "null: ");
        }
        BodyOnProgress bodyOnProgress = new BodyOnProgress();
        bodyOnProgress.setTicketActivityId(itemnumber);
        bodyOnProgress.setProblem(tvproblem.getText().toString());
        bodyOnProgress.setFaultDescription(tvfault.getText().toString());
        bodyOnProgress.setSolution(tvsolution.getText().toString());
        bodyOnProgress.setParts(listarray);
        Log.e("", "onholdclick: "+listarray );
        Observable<ResponseOnProgress> respononprogress = mApi.updateonholdtiket(accessToken, idtiket, bodyOnProgress).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respononprogress.subscribe(new Observer<ResponseOnProgress>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseOnProgress responseOnProgress) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteAllsparepart(new SQLiteSparepart());
                getSupportFragmentManager().findFragmentByTag("hold");
                finish();
            }
        });
    }

    private void onendclick() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        DatabaseHandler db = new DatabaseHandler(this);
        for (int i = 0;i< db.getAllSparepart().size(); i++) {
            Part sp = new Part();
            sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
            sp.setDescription(db.getAllSparepart().get(i).getDescription());
            sp.setQuantity(db.getAllSparepart().get(i).getQty());
            sp.setStatus(db.getAllSparepart().get(i).getStatus());
            sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
            listarray.add(sp);
        }
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        } else {
            Log.e("", "null: ");
        }
        BodyOnProgress bodyOnProgress = new BodyOnProgress();
        bodyOnProgress.setTicketActivityId(itemnumber);
        bodyOnProgress.setProblem(tvproblem.getText().toString());
        bodyOnProgress.setFaultDescription(tvfault.getText().toString());
        bodyOnProgress.setSolution(tvsolution.getText().toString());
        bodyOnProgress.setParts(listarray);
        Log.e("", "onendclick: "+listarray );
        Observable<ResponseOnProgressEnd> respononprogressend = mApi.updateonendtiket(accessToken, idtiket, bodyOnProgress).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respononprogressend.subscribe(new Observer<ResponseOnProgressEnd>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseOnProgressEnd respononprogressend) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteAllsparepart(new SQLiteSparepart());
                getSupportFragmentManager().findFragmentByTag("ended");
                finish();
            }
        });
    }


    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("On Progress Hold");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.bntHold, R.id.btnEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bntHold:
                break;
            case R.id.btnEnd:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*Long onprogresslist = adapterView.getItemIdAtPosition(i);*/
        Datum selecteditem = (Datum) adapterView.getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
        itemnumber = selecteditem.getId().toString();
        /*datum.setName(adapterView.getItemAtPosition(i).toString());*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.ckbsparepart)
    public void onViewClicked() {
        boolean checked = ckbsparepart.isChecked();

        switch (ckbsparepart.getId()) {
            case R.id.ckbsparepart:
                if (checked) {
                    Intent i = new Intent(getApplicationContext(), Sparepart.class);
                    startActivity(i);
                } else {

                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().findFragmentByTag("progres hold");
        finish();
    }
}

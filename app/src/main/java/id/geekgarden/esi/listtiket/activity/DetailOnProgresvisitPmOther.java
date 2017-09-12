package id.geekgarden.esi.listtiket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgresvisitPmOther extends AppCompatActivity {
    public static final String KEY_URI = "id";
    String accessToken;
    String idtiket;
    @BindView(R.id.tvdescription)
    TextView tvdescription;
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
    @BindView(R.id.textInputEditText)
    TextInputEditText textInputEditText;
    @BindView(R.id.cbSparepart)
    CheckBox cbSparepart;
    private ActionBar actionBar;
    private String key;

    @OnClick(R.id.btnStart)
    void ConfirmTiket() {
        onendclick();
    }

    private void onendclick() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken,String.class);
        if (getIntent()!=null){
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        }
        else{
            Log.e("", "null: " );
        }
        Observable<ResponseOnProgressEnd> respononprogressend = mApi.updateonendtiket(accessToken,idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respononprogressend.subscribe(new Observer<ResponseOnProgressEnd>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseOnProgressEnd respononprogressend) {
                Intent i = new Intent(getApplicationContext(), ListTiket.class);
                startActivity(i);
            }
        });
    }

    /*@OnCheckedChanged(R.id.cbSparepart)
    void openAddSparepart(CheckBox checkBox, boolean checked) {
        Intent i = new Intent(getApplicationContext(), Sparepart.class);
        startActivity(i);
    }*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_on_progresvisit_pm_other);
        ButterKnife.bind(this);
        initActionbar();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e("", "onCreate: " + accessToken);
        /*glpref.read(PrefKey.idtiket, String.class);*/
        if (getIntent()!=null){
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        }
        else{
            Log.e("", "null: " );
        }
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
                tvnamaanalis.setText(responseDetailTiket.getData().getCustomerName());
                tvnotelp.setText(responseDetailTiket.getData().getCustomer().getData().getPhoneNumber());
                tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
                tvurgency.setText(responseDetailTiket.getData().getPriority());
                tvnumber.setText(responseDetailTiket.getData().getNumber());
                tvsnalat.setText(responseDetailTiket.getData().getInstrument().getData().getSerialNumber());
                tvdescription.setText(responseDetailTiket.getData().getDescription());
                tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
            }
        });
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Tiket");
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

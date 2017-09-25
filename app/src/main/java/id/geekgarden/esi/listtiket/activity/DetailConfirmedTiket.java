package id.geekgarden.esi.listtiket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updatestartedtiket.ResponseStartedTiket;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailConfirmedTiket extends AppCompatActivity {
    public static final String KEY_URI = "id";
    private Api mApi;
    String idtiket;
    @BindView(R.id.tvNoHp)
    TextView tvNoHp;
    @BindView(R.id.tvTipeAlat)
    TextView tvTipeAlat;
    @BindView(R.id.tvUrgency)
    TextView tvUrgency;
    @BindView(R.id.txtDescription)
    EditText txtDescription;
    @BindView(R.id.btnStart)
    Button btnStart;

    @OnClick(R.id.btnStart)
    void Start(View view) {
        onclickstartdataupdate();

    }

    private GlobalPreferences glpref;
    private String accessToken;
    private ActionBar actionBar;

    public DetailConfirmedTiket() {
    }

    @OnClick(R.id.btnStart)
    void ConfirmTiket(View view) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_confirmed_tiket);
        ButterKnife.bind(this);
        initActionBar();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e("", "onCreate: "+accessToken );
        /*glpref.read(PrefKey.idtiket, String.class);*/
        if (getIntent()!=null){
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickstartdataupdate: " + idtiket);
        }
        else{
            Log.e("", "null: " );
        }
        Observable<ResponseDetailTiket> responsedetailtiket = mApi.detailtiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(new Observer<ResponseDetailTiket>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseDetailTiket responseDetailTiket) {
                tvNoHp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
                tvTipeAlat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
                tvUrgency.setText(responseDetailTiket.getData().getPriority());
            }
        });
    }

    private void onclickstartdataupdate() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken,String.class);
        if (getIntent()!=null){
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickstartdataupdate: " + idtiket);
        }
        else{
            Log.e("", "null: " );
        }
        Log.e("", "onclickstartdataupdate: "+idtiket);
        final Observable<ResponseStartedTiket> responseStartedTiket = mApi.updateonstarttiket(accessToken,idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseStartedTiket.subscribe(new Observer<ResponseStartedTiket>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseStartedTiket responseStartedTiket) {
                Log.e("", "onNext: "+responseStartedTiket.getData().getStatus().toString());
                Intent i = new Intent(getApplicationContext(),ListTiket.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initActionBar() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

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
import id.geekgarden.esi.data.model.tikets.detailopentiket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailOpenTiket extends AppCompatActivity {
    String accessToken;
    String idtiket;
    private ActionBar actionBar;
    private Api mApi;
    private GlobalPreferences glpref;
    public static String KEY_URI = "id";
    @BindView(R.id.tvNoHp)
    TextView tvNoHp;
    @BindView(R.id.tvTipeAlat)
    TextView tvTipeAlat;
    @BindView(R.id.tvUrgency)
    TextView tvUrgency;
    @BindView(R.id.txtDescription)
    EditText txtDescription;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @OnClick(R.id.btnConfirm)
    void Confirm(View view) {
        onclickdataupdate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_open_tiket);
        ButterKnife.bind(this);
        initActionBar();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        Log.e("", "onCreate: "+accessToken );
        /*glpref.read(PrefKey.idtiket, String.class);*/
        idtiket = getIntent().getStringExtra(KEY_URI);
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
                tvNoHp.setText(responseDetailTiket.getData().getNumber());
                tvTipeAlat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
                tvUrgency.setText(responseDetailTiket.getData().getPriority());
            }
        });
    }

    private void onclickdataupdate() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken,String.class);
        idtiket = getIntent().getStringExtra(KEY_URI);
        Log.e("", "onclickdataupdate: "+idtiket);
        BodyConfirmTicket bodyConfirmTicket = new BodyConfirmTicket();
        bodyConfirmTicket.setComment(txtDescription.getText().toString());
        Observable<ResponseConfirmTicket> respontiketconfirm = mApi.updateconfirmtiket(accessToken,idtiket,bodyConfirmTicket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respontiketconfirm.subscribe(new Observer<ResponseConfirmTicket>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseConfirmTicket responseConfirmTicket) {
                Log.e("", "onNext: "+responseConfirmTicket.getData().getStatus().toString());
                Intent i = new Intent(getApplicationContext(),ListTiket.class);
                startActivity(i);
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


}

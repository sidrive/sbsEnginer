package id.geekgarden.esi.listtiket.activitymyticket;


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
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updatestartedtiket.ResponseStartedTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailConfirmedTiket extends AppCompatActivity {
    public static final String KEY_URI = "id";
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private Api mApi;
    private GlobalPreferences glpref;
    private String accessToken;
    private ActionBar actionBar;

    int customer_id;
    String id_customer;
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
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_confirmed_tiket);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            initViewData();
        } else {

        }
        initActionBar();

    }

    @OnClick(R.id.btnStart)
    void Start(View view) {
        onclickstartdataupdate();
    }

    private void initViewData() {
        Observable<ResponseDetailTiket> responsedetailtiket = mApi
                .detailtiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(responseDetailTiket -> {
            tvNoHp.setText(responseDetailTiket.getData().getCustomer().getData().getPhoneNumber());
            tvTipeAlat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
            tvUrgency.setText(responseDetailTiket.getData().getPriority());
            tvDescTiket.setText(responseDetailTiket.getData().getDescription());
        }, throwable -> {
        });
    }

    private void onclickstartdataupdate() {
        Observable<ResponseStartedTiket> responseStartedTiket = mApi
                .updateonstarttiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseStartedTiket.subscribe(responseStartedTiket1 -> {
            if (responseStartedTiket1.getData().getTicketType().getData().getId() == 2) {
                customer_id = responseStartedTiket1.getData().getCustomer().getData().getId();
                Intent i = new Intent(getApplicationContext(), DetailOnProgresvisitPmOther.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                id_customer = String.valueOf(customer_id);
                Log.e("onclickstartdataupdate", "DetailConfirmedTiket" + id_customer);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, id_customer);
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
}

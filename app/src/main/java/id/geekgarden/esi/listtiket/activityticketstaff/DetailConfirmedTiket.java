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
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private Api mApi;
    private GlobalPreferences glpref;
    private String accessToken;
    private String supervisor;
    private String category;
    private ActionBar actionBar;
    private String ticket_type;

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
        } else {}
        if (getIntent() != null) {
            category = getIntent().getStringExtra(KEY_CAT);
        } else {}
        if (getIntent() != null) {
            ticket_type = getIntent().getStringExtra(KEY_TICK);
        } else {}
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
            if (category.equals("Visit")) {
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
            if (ticket_type.equals("Installation")) {
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

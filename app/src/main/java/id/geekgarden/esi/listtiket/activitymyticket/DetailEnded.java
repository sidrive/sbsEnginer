package id.geekgarden.esi.listtiket.activitymyticket;

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
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailEnded extends AppCompatActivity {
    String accessToken;
    String idtiket;
    public static final String KEY_URI = "id";
    @BindView(R.id.tvreportlink)
    TextView tvreportlink;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_ended);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        idtiket = getIntent().getStringExtra(KEY_URI);
        initActionbar();
        initViewData();
    }

    private void initViewData() {
        Observable<ResponseDetailTiket> responsedetailtiket = mApi
            .detailtiket(accessToken, idtiket)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(responseDetailTiket -> {
          tvnamaanalis.setText(responseDetailTiket.getData().getStaffName());
          tvnotelp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
          tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
          tvurgency.setText(responseDetailTiket.getData().getPriority());
          tvnumber.setText(responseDetailTiket.getData().getNumber());
          tvsnalat.setText(responseDetailTiket.getData().getInstrument().getData().getSerialNumber());
          tvdescription.setText(responseDetailTiket.getData().getDescription());
          tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
          String url_string = responseDetailTiket.getData().getInvoice();
          tvreportlink.setText(url_string);
        },throwable -> {});
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

package id.geekgarden.esi.listtiket.activitymyticketspv;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updatediverted.BodyDiverted;
import id.geekgarden.esi.data.model.tikets.updatediverted.ResponseDiverted;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOpenTiketSpv extends AppCompatActivity {

    public static final String KEY_ID = "id_engineer";
    String accessToken;
    String idtiket;
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private ActionBar actionBar;
    private Api mApi;
    private GlobalPreferences glpref;
    private String id_engineer;
    @BindView(R.id.tvNoHp)
    TextView tvNoHp;
    @BindView(R.id.tvTipeAlat)
    TextView tvTipeAlat;
    @BindView(R.id.tvUrgency)
    TextView tvUrgency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_detail_open_tiket_spv);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            id_engineer = getIntent().getStringExtra(KEY_ID);
            Log.e("", "onclickdataupdate: " + id_engineer);
        } else {
            Log.e("", "null: ");
        }
        initActionBar();
        initViewData();
    }
    @OnClick(R.id.btnDiverted)
    void Diverted(View view) {
        OnAssignChange();
    }

    private void OnAssignChange() {
        BodyDiverted bodyDiverted = new BodyDiverted();
        Observable<ResponseDiverted> updatediverted = mApi
            .updateassign(accessToken,id_engineer,bodyDiverted)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
        updatediverted.subscribe(responseDiverted -> {},throwable -> {});
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initViewData() {
        Observable<ResponseDetailTiket> responsedetailtiket = mApi
                .detailtiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(responseDetailTiket -> {
            tvNoHp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
            tvTipeAlat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
            tvUrgency.setText(responseDetailTiket.getData().getPriority());
            tvDescTiket.setText(responseDetailTiket.getData().getDescription());
        }, throwable -> {
        });
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Tiket Open");
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
        getSupportFragmentManager().findFragmentByTag("open");
        finish();
    }
}
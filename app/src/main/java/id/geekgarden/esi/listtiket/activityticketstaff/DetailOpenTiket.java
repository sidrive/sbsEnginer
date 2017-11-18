package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailOpenTiket extends AppCompatActivity {
    String accessToken;
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private ActionBar actionBar;
    private Api mApi;
    private GlobalPreferences glpref;
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
    private String idtiket;
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
        mApi = ApiService.getService();
        setContentView(R.layout.activity_detail_open_tiket);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        initData();
        initActionBar();
        initViewData();
    }

    private void initData() {
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
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initViewData() {
            tvNoHp.setText(staff_phonenumber);
            tvTipeAlat.setText(instrument_type);
            tvUrgency.setText(priority);
            tvDescTiket.setText(description);
    }

    private void onclickdataupdate() {
        if (TextUtils.isEmpty(txtDescription.getText().toString())) {
            txtDescription.setError("This");
            UiUtils.showToast(getApplicationContext(), "Comment Can't Empty");
        } else {
            BodyConfirmTicket bodyConfirmTicket = new BodyConfirmTicket();
            bodyConfirmTicket.setComment(txtDescription.getText().toString());
            Observable<ResponseConfirmTicket> respontiketconfirm = mApi
                    .updateconfirmtiket(accessToken, idtiket, bodyConfirmTicket)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            respontiketconfirm.subscribe(
                    responseConfirmTicket -> onBackPressed()
                    , throwable -> {
                        UiUtils.showToast(getApplicationContext(), throwable.getLocalizedMessage());
                        if (throwable.getMessage().equals("HTTP 422 Unprocessable Entity")) {
                            UiUtils.showToast(getApplicationContext(), "Input Comment First");
                        }
                    });
        }
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
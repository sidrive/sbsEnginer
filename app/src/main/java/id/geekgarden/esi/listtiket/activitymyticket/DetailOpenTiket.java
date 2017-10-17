package id.geekgarden.esi.listtiket.activitymyticket;

import android.app.AlertDialog;
import android.app.DialogFragment;
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

import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import retrofit2.HttpException;
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
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent()!=null){
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        }
        else{
            Log.e("", "null: " );
        }
        initActionBar();
        initViewData();
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
    },throwable -> {});
  }

  private void onclickdataupdate() {
    if (TextUtils.isEmpty(txtDescription.getText().toString())){
          txtDescription.setError("This");
          UiUtils.showToast(getApplicationContext(),"Comment Can't Empty");
        }else{
      BodyConfirmTicket bodyConfirmTicket = new BodyConfirmTicket();
      bodyConfirmTicket.setComment(txtDescription.getText().toString());
      Observable<ResponseConfirmTicket> respontiketconfirm = mApi
          .updateconfirmtiket(accessToken,idtiket,bodyConfirmTicket)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
      respontiketconfirm.subscribe(responseConfirmTicket -> {}
      ,throwable -> {
        UiUtils.showToast(getApplicationContext(),throwable.getLocalizedMessage());
        if (throwable.getMessage().equals("HTTP 422 Unprocessable Entity")){
          UiUtils.showToast(getApplicationContext(),"Input Comment First");
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
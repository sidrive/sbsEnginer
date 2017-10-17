package id.geekgarden.esi.listtiket.activitymyticket;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterOnHoldServiceReport;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum;
import id.geekgarden.esi.data.model.tikets.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnHold extends AppCompatActivity{
    public static final String KEY_URI = "id_tiket";
    public static final String TAG = DetailOnHold.class.getSimpleName();
    private List<Datum> listarray1 = new ArrayList<Datum>();
    private AdapterOnHoldServiceReport adapterOnHoldServiceReport;
    String accessToken;
    String idtiket;
    @BindView(R.id.rcvservicerpt)
    RecyclerView rcvservicerpt;
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

  Observable<ResponseDetailTiket> responsedetailtiket;
  Observable<ResponseServiceReport> responseServiceReport;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_on_hold);
    ButterKnife.bind(this);
    initAcionbar();
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(this);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    Log.e(TAG, "onCreate: " + accessToken);
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
      Log.e(TAG, "onCreate: " + idtiket);
      showservicereport(idtiket, accessToken);
      detailTicketHold(idtiket, accessToken);
    }
  }

  @OnClick(R.id.btnStart)
  void ConfirmTiket() {
    resumeclick(idtiket, accessToken);
  }

  private void resumeclick(String idtiket, String accessToken) {
    Log.e("", "onclickdataupdate: " + idtiket);
    Observable<ResponseOnRestart> responseOnRestart = mApi
        .updateonrestarttiket(accessToken, idtiket)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    responseOnRestart.subscribe(responseOnRestart1 -> {
      Intent i = new Intent(getApplicationContext(), DetailOnProgressHold.class);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
      startActivity(i);
      finish();
    },throwable -> {});
  }

    private void detailTicketHold(String idtiket, String accessToken) {
      responsedetailtiket = mApi.detailtiket(accessToken, idtiket)
          .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
      responsedetailtiket.subscribe(responseDetailTiket -> {
        tvnamaanalis.setText(responseDetailTiket.getData().getStaffName());
        tvnotelp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
        tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
        tvurgency.setText(responseDetailTiket.getData().getPriority());
        tvnumber.setText(responseDetailTiket.getData().getNumber());
        tvsnalat.setText(responseDetailTiket.getData().getInstrument().getData().getSerialNumber());
        tvdescription.setText(responseDetailTiket.getData().getDescription());
        tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
      }, throwable -> {});
    }

  private void showservicereport(final String idtiket, String accessToken) {
    adapterOnHoldServiceReport = new AdapterOnHoldServiceReport(new ArrayList<Datum>(0),getApplicationContext(),
        id -> {
          Log.e(TAG, "onPostClickListener: "+id );
          String id_ticket_activity = String.valueOf(id);
          DialogFragment dialogFragment = new TVPartFragment(glpref,mApi,idtiket,id_ticket_activity);
          dialogFragment.show(getFragmentManager(),"TAG");});
        responseServiceReport = mApi.getservicereport(accessToken, idtiket ).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseServiceReport.subscribe(
            responseServiceReport1 -> adapterOnHoldServiceReport.UpdateTikets(responseServiceReport1.getData())
        ,throwable -> {});
    rcvservicerpt.setAdapter(adapterOnHoldServiceReport);
    }

    private void initAcionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail OnHold");
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
        getSupportFragmentManager().findFragmentByTag("hold");
        finish();
    }
}

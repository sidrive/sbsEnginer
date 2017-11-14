package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.saba.detailsaba.ResponseDetailSaba;
import id.geekgarden.esi.data.model.saba.updateendsaba.ResponseEndSaba;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailSabaActivity extends AppCompatActivity {
    public static final String KEY_URI = "id";
    String id;
    @BindView(R.id.tvdesc)
    TextView tvdesc;
    @BindView(R.id.btnEnd)
    Button btnEnd;
    private ActionBar actionBar;
    private Api mApi;
    private GlobalPreferences glpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_saba);
        mApi = ApiService.getService();
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        initActionBar();
        String AccessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            id = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + id);
        } else {
            Log.e("", "null: ");
        }
        Observable<ResponseDetailSaba> responsedetailSaba = mApi.getdetailsaba(AccessToken,id).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responsedetailSaba.subscribe(new Observer<ResponseDetailSaba>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseDetailSaba responseDetailSabaSaba) {
            tvdesc.setText(responseDetailSabaSaba.getData().getDescription());
            }
        });
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Activity");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnEnd)
    public void updateendactivity() {
        mApi = ApiService.getService();
        glpref = new GlobalPreferences(getApplicationContext());
        String AccessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            id = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + id);
        } else {
            Log.e("", "null: ");
        }
        Observable<ResponseEndSaba> responseendsaba = mApi.updateendsaba(AccessToken,id).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseendsaba.subscribe(new Observer<ResponseEndSaba>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseEndSaba responseEndSaba) {
            Intent i = new Intent(getApplicationContext(),SabaActivity.class);
            startActivity(i);
            finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),SabaActivity.class);
        startActivity(i);
    }

}

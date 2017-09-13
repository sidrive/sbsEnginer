package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.saba.updatesaba.BodySaba;
import id.geekgarden.esi.data.model.saba.updatesaba.ResponseUpdateSaba;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TambahSabaActivity extends AppCompatActivity {
    @BindView(R.id.etSbActivity)
    EditText etSbActivity;
    @BindView(R.id.btnStart)
    Button btnStart;
    private ActionBar actionBar;
    private Api mApi;
    private GlobalPreferences glpref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_saba);
        ButterKnife.bind(this);
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        initActionBar();

    }

    @OnClick(R.id.btnStart)
    public void AddDataSaba() {
        String AccessToken = glpref.read(PrefKey.accessToken,String.class);
        BodySaba bodySaba = new BodySaba();
        bodySaba.setDescription(etSbActivity.getText().toString());
        Observable<ResponseUpdateSaba> updatesaba = mApi.updateonsaba(AccessToken,bodySaba).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        updatesaba.subscribe(new Observer<ResponseUpdateSaba>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseUpdateSaba responseUpdateSaba) {
                Intent i = new Intent(getApplicationContext(),SabaActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Tambah Activity");
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

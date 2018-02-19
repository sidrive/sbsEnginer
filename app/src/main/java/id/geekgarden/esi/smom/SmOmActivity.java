package id.geekgarden.esi.smom;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.smom.AdapterTiketSmOm;
import id.geekgarden.esi.data.model.smom.instrumentsmom.Datum;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailEnded;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmOmActivity extends AppCompatActivity {
  private ActionBar actionBar;
  private AdapterTiketSmOm adapter;
  private Api mApi;
  private GlobalPreferences glpref;
  String accessToken;
  @BindView(R.id.rcvSmOm)RecyclerView rcvSmOm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sm_om);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(this);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initActionBar();
    initRecycleview();
    initData();
  }

  private void initData() {
    mApi.getsmomInstrument(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapter.UpdateTikets(object.getData());
        }, throwable -> {
          Utils.showToast(this,"Check Your Connection");
        });
  }

  private void initRecycleview() {
    adapter =  new AdapterTiketSmOm(new ArrayList<Datum>(0),this,
        new AdapterTiketSmOm.OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id) {
            Intent i = new Intent(getApplicationContext(), DetailSmOm.class);
            String idtiket = String.valueOf(id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailSmOm.KEY_URI, idtiket);
            startActivity(i);
            finish();
          }
        });
    rcvSmOm.setLayoutManager(new LinearLayoutManager(this));
    rcvSmOm.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    rcvSmOm.setAdapter(adapter);

  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Document Information");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

}

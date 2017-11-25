package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;

import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.saba.AdapterSaba;
import id.geekgarden.esi.data.model.saba.getsaba.Datum;
import id.geekgarden.esi.data.model.saba.getsaba.ResponseSaba;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SabaActivity extends AppCompatActivity {
  private ActionBar actionBar;
  private Api mApi;
  private AdapterSaba adapterSaba;
  private GlobalPreferences glpref;

  @BindView(R.id.rcvActSaba)
  RecyclerView rcvActSaba;
  @OnClick(R.id.fab)void AddActivitySaba(View view){
    Intent i  = new Intent(this,TambahSabaActivity.class);
    startActivity(i);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_saba);
     ButterKnife.bind(this);
     mApi = ApiService.getService();
     initActionBar();
     getdatasaba();
  }

    private void getdatasaba() {
      CharSequence text = "End your activity first";
      int duration = Toast.LENGTH_SHORT;
      glpref = new GlobalPreferences(getApplicationContext());
      String AccessToken = glpref.read(PrefKey.accessToken, String.class);
      Log.e("accessToken:", "getdatasaba: "+AccessToken);
      final Observable<ResponseSaba> responseSaba = mApi.getsaba(AccessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
      responseSaba.subscribe(new Observer<ResponseSaba>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
                Log.e("OnError", "onError: "+e.getMessage());
                Log.e("OnError", "onError: "+e.getLocalizedMessage());
          }

          @Override
          public void onNext(ResponseSaba responseSaba) {
                adapterSaba.UpdateTikets(responseSaba.getData());
                Log.e("responsesaba :", "onNext: "+responseSaba.getData().size());
          }
        });
        adapterSaba = new AdapterSaba(new ArrayList<Datum>(0), getApplicationContext(), id -> {
            Log.e("id: ", "onPostClickListener: "+id );
            Intent i = new Intent(getApplicationContext(),DetailSabaActivity.class);
            String idsaba = String.valueOf(id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailSabaActivity.KEY_URI, idsaba);
            startActivity(i);
            finish();
        });
        rcvActSaba.setAdapter(adapterSaba);
        rcvActSaba.setHasFixedSize(true);
        rcvActSaba.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rcvActSaba.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Activity SABA");
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

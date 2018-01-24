package id.geekgarden.esi.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.saba.AdapterSaba;
import id.geekgarden.esi.data.model.saba.getsaba.Datum;
import id.geekgarden.esi.data.model.saba.getsaba.ResponseSaba;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterNotifications;
import id.geekgarden.esi.data.model.tikets.staffticket.model.getnotifications.Datumnotif;
import id.geekgarden.esi.data.model.tikets.staffticket.model.getnotifications.NotificationsItem;
import id.geekgarden.esi.data.model.tikets.staffticket.model.getnotifications.ResponseNotif;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import id.geekgarden.esi.sabaactivity.DetailSabaActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rakasn on 20/01/18.
 */

public class NotificationActivity extends AppCompatActivity {
  private ActionBar actionBar;
  private Api mApi;
  private AdapterNotifications adapterNotifications;
  private GlobalPreferences glpref;

  @BindView(R.id.rcvActNotif)
  RecyclerView rcvActNotif;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notifications);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    initActionBar();
    getNotifications();
  }


  private void getNotifications() {
    CharSequence text = "End your activity first";
    int duration = Toast.LENGTH_SHORT;
    glpref = new GlobalPreferences(getApplicationContext());
    String AccessToken = glpref.read(PrefKey.accessToken, String.class);
    Log.e("accessToken:", "getdatasaba: "+AccessToken);
    final Observable<ResponseNotif> notifItem = mApi.getNotif(AccessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    notifItem.subscribe(new Observer<ResponseNotif>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("OnError", "onError: "+e.getMessage());
        Log.e("OnError", "onError: "+e.getLocalizedMessage());
      }

      @Override
      public void onNext(ResponseNotif responseNotif) {
        adapterNotifications.UpdateTikets(responseNotif.getData());
        Log.e("responsesaba :", "onNext: "+responseNotif.getData().size());
      }
    });
    adapterNotifications = new AdapterNotifications(new ArrayList<Datumnotif>(0), getApplicationContext(), id -> {

    });
    rcvActNotif.setAdapter(adapterNotifications);
    rcvActNotif.setHasFixedSize(true);
    rcvActNotif.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    rcvActNotif.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

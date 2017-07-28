package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.provider.ContactsContract.Data;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.DataAdapter;
import id.geekgarden.esi.data.model.DataAdapter.PostItemListener;
import id.geekgarden.esi.data.model.DataItem;
import id.geekgarden.esi.data.model.ResponseUsers;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SabaActivity extends AppCompatActivity {
  private ActionBar actionBar;
  private DataAdapter adapter;
  private Api mApi;
  @BindView(R.id.rcvActSaba)RecyclerView rcvActSaba;
  @OnClick(R.id.fab)void AddActivitySaba(View view){
    Intent i  = new Intent(this,TambahSabaActivity.class);
    startActivity(i);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_saba);
    ButterKnife.bind(this);
    mApi = ApiService.getervice();
    initActionBar();
    showDummyData();
    initRecycleView();
  }

  private void initRecycleView() {
    adapter = new DataAdapter(this, new ArrayList<DataItem>(0), new PostItemListener() {
      @Override
      public void onPostClickLsitener(long id) {
       Intent i =new Intent(getApplicationContext(),DetailSabaActivity.class);
       startActivity(i);
      }
    });
    rcvActSaba.setHasFixedSize(true);
    rcvActSaba.setLayoutManager(new LinearLayoutManager(this));
    rcvActSaba.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    rcvActSaba.setAdapter(adapter);
  }

  private void showDummyData() {
    Observable<ResponseUsers> responseUsersObservable = mApi.getdataUsers().subscribeOn(Schedulers.newThread()).observeOn(
        AndroidSchedulers.mainThread());
    responseUsersObservable.subscribe(new Observer<ResponseUsers>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("onError", "SabaActivity" + e.getLocalizedMessage());
      }

      @Override
      public void onNext(ResponseUsers responseUsers) {
        adapter.UpdateData(responseUsers.getData());
      }
    });
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

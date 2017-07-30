package id.geekgarden.esi.smom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmOmActivity extends AppCompatActivity {
  private ActionBar actionBar;

  private Api mApi;
  @BindView(R.id.rcvSmOm)RecyclerView rcvSmOm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sm_om);
    ButterKnife.bind(this);
    mApi = ApiService.getervice();
    initActionBar();
    initRecycleview();
    showDummyData();
  }

  private void showDummyData() {

  }

  private void initRecycleview() {

    rcvSmOm.setLayoutManager(new LinearLayoutManager(this));
    rcvSmOm.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("SM $ OM ");
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

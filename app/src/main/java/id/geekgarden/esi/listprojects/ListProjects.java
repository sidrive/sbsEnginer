package id.geekgarden.esi.listprojects;

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
import id.geekgarden.esi.data.model.DataAdapter;
import id.geekgarden.esi.data.model.DataAdapter.PostItemListener;
import id.geekgarden.esi.data.model.DataItem;
import id.geekgarden.esi.data.model.ResponseUsers;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListProjects extends AppCompatActivity {
  private ActionBar actionBar;
  private Api mApi;
  private DataAdapter adapter;
  @BindView(R.id.rcvListProject)
  RecyclerView rcvListProject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_projects);
    ButterKnife.bind(this);
    mApi = ApiService.getervice();
    initActionBar();
    showDummyData();
    initRecycleView();
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

  private void initRecycleView() {
    adapter = new DataAdapter(this, new ArrayList<DataItem>(0), new PostItemListener() {
      @Override
      public void onPostClickLsitener(long id) {

      }
    });
    rcvListProject.setHasFixedSize(true);
    rcvListProject.setLayoutManager(new LinearLayoutManager(this));
    rcvListProject.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    rcvListProject.setAdapter(adapter);
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("List Project");
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

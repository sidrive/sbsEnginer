package id.geekgarden.esi.listprojects;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterListProjects;
import id.geekgarden.esi.preference.GlobalPreferences;

public class ListProjects extends AppCompatActivity {
  private ActionBar actionBar;
  private Api mApi;
  private AdapterListProjects adapter;
  private GlobalPreferences glpref;
  @BindView(R.id.rcvListProject)
  RecyclerView rcvListProject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_projects);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    initActionBar();
   /*showDummyData();*/
    initRecycleView();
  }

 /* private void showDummyData() {
    final String accesstoken = glpref.read(PrefKey.accessToken, String.class);
    final Observable<ResponseTikets> respon = mApi.getTikets(accesstoken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(new Observer<ResponseTikets>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(ResponseTikets responseTikets ) {

      }
    });
  }*/

  private void initRecycleView() {
    adapter = new AdapterListProjects(getApplicationContext(), new ArrayList<Datum>(0),
        (id, status) -> {

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

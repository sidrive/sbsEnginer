package id.geekgarden.esi.listprojects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import id.geekgarden.esi.data.model.project.listproject.Datum;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.project.listproject.adapter.AdapterListProjects;
import id.geekgarden.esi.preference.GlobalPreferences;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    initActionBar();
    initRecycleView();
    initData(accessToken);
  }

  private void initData(String accessToken) {
    mApi.getproject(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseProject -> {
          adapter.UpdateTikets(responseProject.getData());
        }, throwable -> {
          Utils.showToast(this,"Check Your Connection and Tryagain");
        });
  }


  private void initRecycleView() {
    adapter = new AdapterListProjects(getApplicationContext(), new ArrayList<Datum>(0),
        (id) -> {
          Intent i = new Intent(getApplicationContext(), DetailProject.class);
          String idtiket = String.valueOf(id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailProject.KEY_URI, idtiket);
          startActivity(i);
          finish();
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

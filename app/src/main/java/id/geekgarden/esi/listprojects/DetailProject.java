package id.geekgarden.esi.listprojects;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.project.listproject.adapter.AdapterChecklistProject;
import id.geekgarden.esi.data.model.project.listproject.bodyproject.BodyProject;
import id.geekgarden.esi.data.model.project.listproject.bodyproject.Datum;
import id.geekgarden.esi.data.model.project.listproject.detailproject.Datum_;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rakasn on 24/01/18.
 */

public class DetailProject extends AppCompatActivity {

  public static final String KEY_URI = "id";
  @BindView(R.id.textView4)
  TextView textView4;
  @BindView(R.id.tvProject)
  TextView tvProject;
  @BindView(R.id.lyt_02)
  LinearLayout lyt02;
  @BindView(R.id.btnSave)
  Button btnSave;
  private ActionBar actionBar;
  private Api mApi;
  private String id;
  private String accessToken;
  private AdapterChecklistProject adapter;
  private GlobalPreferences glpref;
  @BindView(R.id.rcvListProject)
  RecyclerView rcvListProject;
  private ArrayList<Datum_> listarray = new ArrayList<Datum_>();
  private ArrayList<Datum> listarraybody = new ArrayList<Datum>();
  private BodyProject bodyProject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_projects);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    bodyProject = new BodyProject();
    initActionBar();
    initRecycleView();
    initData(accessToken);
  }

  private void initData(String accessToken) {
    if (getIntent() != null) {
      id = getIntent().getStringExtra(KEY_URI);
    } else {
    }
    Utils.showProgress(this).show();
    mApi.getdetailproject(accessToken, id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseProject -> {
          tvProject.setText(responseProject.getData().getName());
          for (int i = 0; i < responseProject.getData().getProjectGroup().getData().size(); i++) {
            for (int j = 0;
                j < responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                    .getData().size(); j++) {
              Datum_ chkitem = new Datum_();
              chkitem.setId(
                  responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                      .getData().get(j).getId());
              chkitem.setName(
                  responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                      .getData().get(j).getName());
              chkitem.setValue(
                  responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                      .getData().get(j).getValue());
              chkitem.setStartDate(
                  responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                      .getData().get(j).getStartDate());
              chkitem.setEndDate(responseProject.getData().getProjectGroup().getData().get(i).getProjectItem()
                  .getData().get(j).getEndDate());
              listarray.add(chkitem);
            }
          }
          Utils.dismissProgress();
          adapter.UpdateTikets(listarray);
        }, throwable -> {
          Utils.dismissProgress();
          Utils.showToast(this, "Check Your Connection and Tryagain");
        });
  }

  @OnClick(R.id.btnSave)
  void ConfirmProject() {
    Utils.showProgress(this).show();
    saveData();
  }

  private void saveData() {
    mApi.getupdateproject(accessToken, id, bodyProject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseDetailProject -> {
          Utils.dismissProgress();
          finish();
        }, throwable -> {
          Utils.dismissProgress();
          Utils.showToast(this, "Check Your Connection");
        });
  }

  private void initRecycleView() {
    adapter = new AdapterChecklistProject(new ArrayList<Datum_>(0), this,
        (id, is_checked) -> {
          Datum bodychecklist = new Datum();
          bodychecklist.setId(id);
          bodychecklist.setValue(is_checked);
          listarraybody.add(bodychecklist);
          bodyProject.setData(listarraybody);
          Log.e("initData", "DetailProject" + bodyProject.getData().toString());
          Log.e("initRecycleView", "DetailProject" + bodychecklist.toString());
        });
    rcvListProject.setHasFixedSize(true);
    rcvListProject.setLayoutManager(new LinearLayoutManager(this));
    rcvListProject
        .addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}
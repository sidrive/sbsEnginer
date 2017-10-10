package id.geekgarden.esi.listtiket;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.preference.GlobalPreferences;

public class OpenTiketOtherActivity extends AppCompatActivity {

  private final static String TAG = OpenTiketOtherActivity.class.getSimpleName();
  public final static String KEY = "key";
  @BindView(R.id.spndivision)
  Spinner spndivision;
  @BindView(R.id.lytSpnDivision)
  TextInputLayout lytSpnDivision;
  @BindView(R.id.spncustomer)
  Spinner spncustomer;
  @BindView(R.id.lytSpnCustomer)
  TextInputLayout lytSpnCustomer;
  @BindView(R.id.spninstrument)
  Spinner spninstrument;
  @BindView(R.id.lytSpnInstrument)
  TextInputLayout lytSpnInstrument;
  @BindView(R.id.spnengineer)
  Spinner spnengineer;
  @BindView(R.id.lytSpnEngineer)
  TextInputLayout lytSpnEngineer;
  @BindView(R.id.tvdescription)
  TextInputEditText tvdescription;
  @BindView(R.id.lytTvDesc)
  TextInputLayout lytTvDesc;
  @BindView(R.id.tvanalis)
  TextInputEditText tvanalis;
  @BindView(R.id.lytTvAnalis)
  TextInputLayout lytTvAnalis;
  @BindView(R.id.tvhpanalis)
  TextInputEditText tvhpanalis;
  @BindView(R.id.lytTvNoHpAnalis)
  TextInputLayout lytTvNoHpAnalis;
  @BindView(R.id.spnPriority)
  Spinner spnPriority;
  @BindView(R.id.lytSpnPriority)
  TextInputLayout lytSpnPriority;
  @BindView(R.id.btnOpenTiket)
  Button btnOpenTiket;
  private ActionBar actionBar;
  private Api mApi;
  private String key;
  private GlobalPreferences glpref;

  @OnClick(R.id.btnOpenTiket)
  void OpenTiket() {
    finish();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_tiket_other);
    ButterKnife.bind(this);
    key = getIntent().getStringExtra(KEY);
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(getApplicationContext());
    initActionbar();
      if (spnengineer.equals("IT")) {
                /*lytSpnSnAlat.setVisibility(View.GONE);
                lytSpnShi.setVisibility(View.VISIBLE);*/
                /*initSpinnerShi();*/
      } else {
                /*lytSpnSnAlat.setVisibility(View.VISIBLE);
                lytSpnShi.setVisibility(View.GONE);*/
                /*initSpinnerSn();*/
      }
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Open Tiket " + key);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    getSupportFragmentManager().findFragmentByTag("open");
    finish();
  }

}

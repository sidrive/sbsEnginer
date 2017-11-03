package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

public class DetailInstrumentForm extends AppCompatActivity {

  public static final String KEY_URI = "id";
  public static final String KEY_CUST = "id_customer";
  @BindView(R.id.tvaccount)
  TextInputEditText tvaccount;
  @BindView(R.id.tvaddress)
  TextInputEditText tvaddress;
  @BindView(R.id.tvcontact)
  TextInputEditText tvcontact;
  @BindView(R.id.tvphone)
  TextInputEditText tvphone;
  @BindView(R.id.tvfaxno)
  TextInputEditText tvfaxno;
  @BindView(R.id.chkgrndok)
  CheckBox chkgrndok;
  @BindView(R.id.chkgrndno)
  CheckBox chkgrndno;
  @BindView(R.id.tveasykey)
  TextInputEditText tveasykey;
  @BindView(R.id.tveasyno)
  TextInputEditText tveasyno;
  @BindView(R.id.chkwin7)
  CheckBox chkwin7;
  @BindView(R.id.chkwin8)
  CheckBox chkwin8;
  @BindView(R.id.tvoskey)
  TextInputEditText tvoskey;
  @BindView(R.id.tvinstrumentmodel)
  TextInputEditText tvinstrumentmodel;
  @BindView(R.id.tvinstrumentsn)
  TextInputEditText tvinstrumentsn;
  @BindView(R.id.tvpneumatic)
  TextInputEditText tvpneumatic;
  @BindView(R.id.tvsample)
  TextInputEditText tvsample;
  @BindView(R.id.tvmcp)
  TextInputEditText tvmcp;
  @BindView(R.id.tvbarcode)
  TextInputEditText tvbarcode;
  @BindView(R.id.tvxt)
  TextInputEditText tvxt;
  @BindView(R.id.tvcpuserial)
  TextInputEditText tvcpuserial;
  @BindView(R.id.tvmonitor)
  TextInputEditText tvmonitor;
  @BindView(R.id.tvkeyboard)
  TextInputEditText tvkeyboard;
  @BindView(R.id.tvmouse)
  TextInputEditText tvmouse;
  @BindView(R.id.tvprinter)
  TextInputEditText tvprinter;
  @BindView(R.id.tvprintersn)
  TextInputEditText tvprintersn;
  @BindView(R.id.tvups)
  TextInputEditText tvups;
  @BindView(R.id.tvupssn)
  TextInputEditText tvupssn;
  @BindView(R.id.tvinstalby)
  TextInputEditText tvinstalby;
  @BindView(R.id.tvinstaldate)
  TextInputEditText tvinstaldate;
  @BindView(R.id.chksold)
  CheckBox chksold;
  @BindView(R.id.chkrr)
  CheckBox chkrr;
  @BindView(R.id.chkdemo)
  CheckBox chkdemo;
  @BindView(R.id.chkeva)
  CheckBox chkeva;
  @BindView(R.id.chkcppr)
  CheckBox chkcppr;
  @BindView(R.id.chkmysap)
  CheckBox chkmysap;
  @BindView(R.id.chkcrm)
  CheckBox chkcrm;
  @BindView(R.id.btnStart)
  Button btnStart;
  @BindView(R.id.marginbotom)
  ImageView marginbotom;
  private ActionBar actionBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_instrument);
    ButterKnife.bind(this);
    initActionbar();
  }

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Instrument Installation");
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

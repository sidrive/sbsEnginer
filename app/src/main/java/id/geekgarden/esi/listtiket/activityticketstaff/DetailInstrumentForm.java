package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation.ResponseInstalled;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailInstrumentForm extends AppCompatActivity {

  public static final String KEY_URI = "id";
  public static final String KEY_CUST = "id_customer";
  public static final String KEY_CAT = "category";
  @BindView(R.id.chkgrndok)
  RadioButton chkgrndok;
  @BindView(R.id.chkgrndno)
  RadioButton chkgrndno;
  @BindView(R.id.chkwin7)
  RadioButton chkwin7;
  @BindView(R.id.chkwin8)
  RadioButton chkwin8;
  @BindView(R.id.chksold)
  RadioButton chksold;
  @BindView(R.id.chkrr)
  RadioButton chkrr;
  @BindView(R.id.chkdemo)
  RadioButton chkdemo;
  @BindView(R.id.chkeva)
  RadioButton chkeva;
  @BindView(R.id.chkcppr)
  RadioButton chkcppr;
  @BindView(R.id.chkmysap)
  RadioButton chkmysap;
  @BindView(R.id.chkcrm)
  RadioButton chkcrm;
  private Api mApi;
  private GlobalPreferences glpref;
  private String accessToken;
  private String supervisor;
  private String category;
  String id_customer;
  String idtiket;
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
  @BindView(R.id.tveasykey)
  TextInputEditText tveasykey;
  @BindView(R.id.tveasyno)
  TextInputEditText tveasyno;
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
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {
    }
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {
    }
    initData(accessToken, idtiket);
  }

  private void initData(String accessToken, String idtiket) {
    Observable<ResponseInstalled> getinstallation = mApi
        .getinstallation(accessToken, idtiket)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getinstallation.subscribe(responseInstalled -> {
      tvaccount.setText(responseInstalled.getData().getCustomerName());
      tvaddress.setText(responseInstalled.getData().getAddress());
      tvcontact.setText(responseInstalled.getData().getContactPerson());
      tvphone.setText(responseInstalled.getData().getPhoneNumber());
      tvfaxno.setText(responseInstalled.getData().getFaxNumber());
      if (responseInstalled.getData().getGrounding().equals("Ok")) {
        chkgrndok.setChecked(true);
      }
      if (responseInstalled.getData().getGrounding().equals("No")){
        chkgrndno.setChecked(true);
      }
      tveasykey.setText(responseInstalled.getData().getEasyaccessKey());
      tveasyno.setText(responseInstalled.getData().getEasyaccessSerialNumber());
      if (responseInstalled.getData().getOperatingSystem().equals("Win 7 Pro")){
        chkwin7.setChecked(true);
      }
      if (responseInstalled.getData().getOperatingSystem().equals("Win 8")){
       chkwin8.setChecked(true);
      }
      tvoskey.setText(responseInstalled.getData().getOperatingSystem());
      tvinstrumentmodel.setText(responseInstalled.getData().getInstrumentTypeId());
      tvinstrumentsn.setText(responseInstalled.getData().getInstrumentSerialNumber());
      tvpneumatic.setText(responseInstalled.getData().getPneumaticUnitSerialNumber());
      tvsample.setText(responseInstalled.getData().getSampleLoaderSerialNumber());
      tvmcp.setText(responseInstalled.getData().getMcpSerialNumber());
      tvbarcode.setText(responseInstalled.getData().getBarcodeReaderSerialNumber());
      tvxt.setText(responseInstalled.getData().getXt1800iXt2000iPim());
      tvcpuserial.setText(responseInstalled.getData().getCpuSerialNumber());
      tvmonitor.setText(responseInstalled.getData().getMonitorSerialNumber());
      tvkeyboard.setText(responseInstalled.getData().getKeyboardSerialNumber());
      tvmouse.setText(responseInstalled.getData().getMouseSerialNumber());
      tvprinter.setText(responseInstalled.getData().getPrinterModel());
      tvprintersn.setText(responseInstalled.getData().getPrinterSerialNumber());
      tvups.setText(responseInstalled.getData().getUpsModel());
      tvupssn.setText(responseInstalled.getData().getUpsSerialNumber());
      if (responseInstalled.getData().getDataUpdatedOn().equals("MySap")){
        chkmysap.setChecked(true);
      }
      if (responseInstalled.getData().getDataUpdatedOn().equals("CRM")){
        chkcrm.setChecked(true);
      }

    }, throwable -> {
    });
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

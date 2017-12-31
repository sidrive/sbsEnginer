package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation.BodyInstallation;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailInstrumentForm extends AppCompatActivity {

  public static final String KEY_URI = "id";
  public static final String KEY_CAT = "category";
  public static final String KEY_TICK = "ticket_type";
  public static final String KEY_CUST = "id_customer";
  public static final String KEY_ACTI = "activity_id";
  public static final String KEY_SNAME = "staff_name";
  public static final String KEY_SPHN = "staff_phonenumber";
  public static final String KEY_INST = "instrument_type";
  public static final String KEY_INS = "instrument";
  public static final String KEY_PRIO = "priority";
  public static final String KEY_NUM = "number";
  public static final String KEY_CUSTN = "customer_name";
  public static final String KEY_CONT = "contract";
  public static final String KEY_DESC = "description";
  public static final String KEY_CIT = "it_category";
  public static final String KEY_IDI = "hardware_id";
  public static final String KEY_IDS = "software_id";
  public static final String KEY_HAR = "hardware";
  public static final String KEY_SOF = "software";
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
  @BindView(R.id.chkbckup)
  RadioButton chkbckup;
  @BindView(R.id.checkstatus)
  RadioGroup checkstatus;
  @BindView(R.id.checkgrnd)
  RadioGroup checkgrnd;
  @BindView(R.id.checkwindows)
  RadioGroup checkwindows;
  @BindView(R.id.checkduo)
  RadioGroup checkduo;
  private Api mApi;
  private GlobalPreferences glpref;
  String accessToken;
  private String supervisor;
  BodyInstallation bodyInstallation;
  private String idtiket;
  private String category;
  private String ticket_type;
  private String id_customer;
  private String activity_id;
  private String staff_name;
  private String staff_phonenumber;
  private String instrument_type;
  private String instrument;
  private String priority;
  private String number;
  private String customer_name;
  private String contract;
  private String description;
  private String it_category;
  private String hardware_id;
  private String software_id;
  private String hardware;
  private String software;
  boolean grounding;
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
  @BindView(R.id.marginbotom)
  ImageView marginbotom;
  private ActionBar actionBar;
  int id_instrument;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_instrument);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initData();
    initDataView(accessToken, idtiket);
    initActionbar();
  }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {}
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {}
    if (getIntent() != null) {
      ticket_type = getIntent().getStringExtra(KEY_TICK);
    } else {}
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {}
    if (getIntent() != null) {
      activity_id = getIntent().getStringExtra(KEY_ACTI);
    } else {}
    if (getIntent() != null) {
      staff_name= getIntent().getStringExtra(KEY_SNAME);
    } else {}
    if (getIntent() != null) {
      staff_phonenumber = getIntent().getStringExtra(KEY_SPHN);
    } else {}
    if (getIntent() != null) {
      instrument_type = getIntent().getStringExtra(KEY_INST);
    } else {}
    if (getIntent() != null) {
      instrument = getIntent().getStringExtra(KEY_INS);
    } else {}
    if (getIntent() != null) {
      priority = getIntent().getStringExtra(KEY_PRIO);
    } else {}
    if (getIntent() != null) {
      number = getIntent().getStringExtra(KEY_NUM);
    } else {}
    if (getIntent() != null) {
      customer_name = getIntent().getStringExtra(KEY_CUSTN);
    } else {}
    if (getIntent() != null) {
      contract = getIntent().getStringExtra(KEY_CONT);
    } else {}
    if (getIntent() != null) {
      description = getIntent().getStringExtra(KEY_DESC);
    } else {}
    if (getIntent() != null) {
      it_category = getIntent().getStringExtra(KEY_CIT);
    } else {}
    if (getIntent() != null) {
      hardware_id = getIntent().getStringExtra(KEY_IDI);
    } else {}
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_IDS);
    } else {}
    if (getIntent() != null) {
      hardware = getIntent().getStringExtra(KEY_HAR);
    } else {}
    if (getIntent() != null) {
      software = getIntent().getStringExtra(KEY_SOF);
    } else {}
    Utils.showProgress(this);
  }

  private void updateInstallation(String accessToken, String idtiket) {
    bodyInstallation = new BodyInstallation();
    bodyInstallation.setAddress(tvaddress.getText().toString());
    bodyInstallation.setContactPerson(tvcontact.getText().toString());
    bodyInstallation.setPhoneNumber(tvphone.getText().toString());
    bodyInstallation.setFaxNumber(tvfaxno.getText().toString());
    bodyInstallation.setEasyaccessKey(tveasykey.getText().toString());
    bodyInstallation.setEasyaccessSerialNumber(tveasyno.getText().toString());
    bodyInstallation.setOsProductKey(tvoskey.getText().toString());
    bodyInstallation.setInstrumentSerialNumber(tvinstrumentsn.getText().toString());
    bodyInstallation.setPneumaticUnitSerialNumber(tvpneumatic.getText().toString());
    bodyInstallation.setSampleLoaderSerialNumber(tvsample.getText().toString());
    bodyInstallation.setMcpSerialNumber(tvmcp.getText().toString());
    bodyInstallation.setXt1800iXt2000iPim(tvxt.getText().toString());
    bodyInstallation.setCpuSerialNumber(tvcpuserial.getText().toString());
    bodyInstallation.setBarcodeReaderSerialNumber(tvbarcode.getText().toString());
    bodyInstallation.setMonitorSerialNumber(tvmonitor.getText().toString());
    bodyInstallation.setKeyboardSerialNumber(tvkeyboard.getText().toString());
    bodyInstallation.setMouseSerialNumber(tvmouse.getText().toString());
    bodyInstallation.setPrinterModel(tvprinter.getText().toString());
    bodyInstallation.setPrinterSerialNumber(tvprintersn.getText().toString());
    bodyInstallation.setUpsModel(tvups.getText().toString());
    bodyInstallation.setUpsSerialNumber(tvupssn.getText().toString());
    int checkstatusID = checkstatus.getCheckedRadioButtonId();
    View statusitem = checkstatus.findViewById(checkstatusID);
    int ids = checkstatus.indexOfChild(statusitem);
    int idstatus = ids + 1;
    bodyInstallation.setContractTypeId(idstatus);
    if (checkduo.getCheckedRadioButtonId() == -1){
    } else {
      int checkDUOID = checkduo.getCheckedRadioButtonId();
      View statusduo = checkduo.findViewById(checkDUOID);
      int idd = checkduo.indexOfChild(statusduo);
      RadioButton duo = (RadioButton)  checkduo.getChildAt(idd);
      String selectedduo = duo.getText().toString();
      Log.e("updateInstallation", "DetailInstrumentForm" + selectedduo);
      bodyInstallation.setDataUpdatedOn(selectedduo);
    }
    if (checkwindows.getCheckedRadioButtonId() == -1)
    {
      bodyInstallation.setOperatingSystem("");
    } else {
      int checkwinID = checkwindows.getCheckedRadioButtonId();
      View statuswin = checkwindows.findViewById(checkwinID);
      int idw = checkwindows.indexOfChild(statuswin);
      RadioButton win = (RadioButton)  checkwindows.getChildAt(idw);
      String selectedwin = win.getText().toString();
      Log.e("updateInstallation", "DetailInstrumentForm" + selectedwin);
      bodyInstallation.setOperatingSystem(selectedwin);
    }
    int checkgrndID = checkgrnd.getCheckedRadioButtonId();
    View statusgrnd = checkgrnd.findViewById(checkgrndID);
    int idg = checkgrnd.indexOfChild(statusgrnd);
    if (idg == 0){
      grounding = false;
    }else{
      grounding = true;
    }
    bodyInstallation.setGrounding(grounding);
    Log.e("updateInstallation", "DetailInstrumentForm" + idg);
    Log.e("updateInstallation", "DetailInstrumentForm" + idstatus);
    Log.e("updateInstallation", "DetailInstrumentForm" + grounding);
    mApi.updateinstallation(accessToken, idtiket, bodyInstallation)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseInstalled -> {
              Utils.dismissProgress();
              Intent i = new Intent(getApplicationContext(), DetailShipping.class);
              String instrumentid = String.valueOf(id_instrument);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailShipping.KEY_URI, idtiket);
              i.putExtra(DetailShipping.KEY_INSTR, instrumentid);
              Utils.showToast(getApplicationContext(),"Success Update Installation Form");
              startActivity(i);
              finish();
            }
            , throwable -> {
              Utils.dismissProgress();
            });
  }

  private void initDataView(String accessToken, String idtiket) {
    Utils.showProgress(this).show();
    mApi.getinstallation(accessToken, idtiket)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseInstalled -> {
          id_instrument = responseInstalled.getData().getInstrumentTypeId();
          tvaccount.setText(responseInstalled.getData().getCustomerName());
          tvaddress.setText(responseInstalled.getData().getAddress());
          tvcontact.setText(responseInstalled.getData().getContactPerson());
          tvphone.setText(responseInstalled.getData().getPhoneNumber());
          tvfaxno.setText(responseInstalled.getData().getFaxNumber());
          if (responseInstalled.getData().getGrounding().equals(0)) {
            chkgrndno.setChecked(true);
          } else {
            chkgrndok.setChecked(true);
          }
          tveasykey.setText(responseInstalled.getData().getEasyaccessKey());
          tveasyno.setText(responseInstalled.getData().getEasyaccessSerialNumber());
          tvoskey.setText(responseInstalled.getData().getOperatingSystem());
          tvinstrumentmodel.setText(responseInstalled.getData().getInstrumentType());
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
          if (responseInstalled.getData().getDataUpdatedOn().equals("MySap")) {
            chkmysap.setChecked(true);
          }
          if (responseInstalled.getData().getDataUpdatedOn().equals("CRM")) {
            chkcrm.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(1)) {
            chksold.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(2)) {
            chkrr.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(3)) {
            chkdemo.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(4)) {
            chkeva.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(5)) {
            chkbckup.setChecked(true);
          }
          if (responseInstalled.getData().getContractTypeId().equals(6)) {
            chkcppr.setChecked(true);
          }
          if (responseInstalled.getData().getOperatingSystem().equals("Win 7 Pro")) {
            chkwin7.setChecked(true);
            bodyInstallation.setOperatingSystem("Win 7 Pro");
          }
          if (responseInstalled.getData().getOperatingSystem().equals("Win 8")) {
            chkwin8.setChecked(true);
            bodyInstallation.setOperatingSystem("Win 8");
          }
          Utils.dismissProgress();
        }, throwable -> {
          Utils.dismissProgress();
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

  @OnClick(R.id.btnStart)
  public void onClick() {
    Utils.showProgress(this).show();
    updateInstallation(accessToken, idtiket);
  }
}

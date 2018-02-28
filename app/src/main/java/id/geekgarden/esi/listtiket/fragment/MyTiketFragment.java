package id.geekgarden.esi.listtiket.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketAll;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketAll.OnTiketPostItemListener;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketSwitch;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailInstrumentForm;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressHoldInstallAnalyzer;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressHoldInstallHclab;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressInstallAnalyzer;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressInstallHclab;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressVisitIT;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailPmIt;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailShipping;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailShippingHold;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailHoldTiketSpv;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailOpenTiketSpv;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSearchTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket.ResponseSearchTiket;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailEnded;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnHold;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressHold;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressNew;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOpenTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MyTiketFragment extends Fragment {
    private static final String KEY = "key";
    @BindView(R.id.etSearch)
    EditText etSearch;
    private Api mApi;
    private Unbinder unbinder;
    private AdapterTiketAll adapterTiketAll;
    private AdapterSearchTiket adapterSearchTiket;
    private AdapterTiketSwitch adapterTiketSwitch;
    private GlobalPreferences glpref;
    private String accessToken;
    private String key;
    private String id_division;
    private static ProgressDialog pDialog;

    public MyTiketFragment() {
    }
    @BindView(R.id.rcvTiket)
    RecyclerView rcvTiket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getString(KEY);
        }

      Log.e("MyTiketFragment", "onCreateTiket: " + key);
    }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mytiket, container, false);
        unbinder = ButterKnife.bind(this, v);
        pDialog = new ProgressDialog(getContext());
        pDialog.setTitle("Loading....");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        glpref = new GlobalPreferences(getContext());
        mApi = ApiService.getService();
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        id_division = glpref.read(PrefKey.division_id,String.class);
        return v;
    }

  @Override
  public void onResume() {
    super.onResume();
    onRefresh();
  }

  private void loadDataTicketScouting() {
    pDialog.show();
    mApi.getTiketswitch(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseTikets1 -> {
      pDialog.dismiss();
      if (responseTikets1.getData() != null) {
        adapterTiketSwitch.UpdateTikets(responseTikets1.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {pDialog.dismiss();
      Utils.dismissProgress();
      Utils.showToast(getContext(),"Check your connection and Try Again");
    });
    adapterTiketSwitch = new AdapterTiketSwitch(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {});
    rcvTiket.setAdapter(adapterTiketSwitch);
  }

  @OnTextChanged(value = R.id.etSearch,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void setEtSearch(CharSequence q){
        if (q.length()>=2){
          String name = q.toString();
            queryearchTiket(name);
        }if (q.length()==0){
            loadDataTiketall();
        }
    }

    private void queryearchTiket(String name) {
      pDialog.show();
      adapterSearchTiket = new AdapterSearchTiket(new ArrayList<Datum>(), getContext(),
          (int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) -> {
            if (status != null) {
              if (status.equals("new")) {
                Intent i = new Intent(getContext(), DetailOpenTiket.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
                i.putExtra(DetailOpenTiket.KEY_CAT, category);
                i.putExtra(DetailOpenTiket.KEY_TICK, ticket_type);
                i.putExtra(DetailOpenTiket.KEY_CUST, customer_id);
                i.putExtra(DetailOpenTiket.KEY_ACTI, id_activity);
                i.putExtra(DetailOpenTiket.KEY_SNAME, staff_name);
                i.putExtra(DetailOpenTiket.KEY_SPHN, staff_phone);
                i.putExtra(DetailOpenTiket.KEY_INST, instrument_type);
                i.putExtra(DetailOpenTiket.KEY_INS, instrument);
                i.putExtra(DetailOpenTiket.KEY_PRIO, priority);
                i.putExtra(DetailOpenTiket.KEY_NUM, number);
                i.putExtra(DetailOpenTiket.KEY_CUSTN, customer_name);
                i.putExtra(DetailOpenTiket.KEY_CONT, contract);
                i.putExtra(DetailOpenTiket.KEY_DESC, description);
                i.putExtra(DetailOpenTiket.KEY_CIT, it_category);
                i.putExtra(DetailOpenTiket.KEY_IDI, id_hardware);
                i.putExtra(DetailOpenTiket.KEY_IDS, id_software);
                i.putExtra(DetailOpenTiket.KEY_HAR, code);
                i.putExtra(DetailOpenTiket.KEY_SOF, version);
                startActivity(i);
              } else if (status.equals("confirmed")) {
                Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
                i.putExtra(DetailConfirmedTiket.KEY_CAT, category);
                i.putExtra(DetailConfirmedTiket.KEY_TICK, ticket_type);
                i.putExtra(DetailConfirmedTiket.KEY_CUST, customer_id);
                i.putExtra(DetailConfirmedTiket.KEY_ACTI, id_activity);
                i.putExtra(DetailConfirmedTiket.KEY_SNAME, staff_name);
                i.putExtra(DetailConfirmedTiket.KEY_SPHN, staff_phone);
                i.putExtra(DetailConfirmedTiket.KEY_INST, instrument_type);
                i.putExtra(DetailConfirmedTiket.KEY_INS, instrument);
                i.putExtra(DetailConfirmedTiket.KEY_PRIO, priority);
                i.putExtra(DetailConfirmedTiket.KEY_NUM, number);
                i.putExtra(DetailConfirmedTiket.KEY_CUSTN, customer_name);
                i.putExtra(DetailConfirmedTiket.KEY_CONT, contract);
                i.putExtra(DetailConfirmedTiket.KEY_DESC, description);
                i.putExtra(DetailConfirmedTiket.KEY_CIT, it_category);
                i.putExtra(DetailConfirmedTiket.KEY_IDI, id_hardware);
                i.putExtra(DetailConfirmedTiket.KEY_IDS, id_software);
                i.putExtra(DetailConfirmedTiket.KEY_HAR, code);
                i.putExtra(DetailConfirmedTiket.KEY_SOF, version);
                startActivity(i);
              } else if (status.equals("started")) {
                if (id_division.equals("3") && category.equals("Installation")) {
                  if (it_category.equals("Hardware")) {
                    Intent i = new Intent(getContext(), DetailOnProgressInstallAnalyzer.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CAT, category);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_ACTI, id_activity);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SNAME, staff_name);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INST, instrument_type);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INS, instrument);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_PRIO, priority);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_NUM, number);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CONT, contract);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_DESC, description);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CIT, it_category);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDI, id_hardware);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDS, id_software);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_HAR, code);
                    i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SOF, version);
                    startActivity(i);
                  } else if (it_category.equals("Software")) {
                    Intent i = new Intent(MyTiketFragment.this.getContext(),
                        DetailOnProgressInstallHclab.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CAT, category);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_ACTI, id_activity);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_SNAME, staff_name);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_INST, instrument_type);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_INS, instrument);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_PRIO, priority);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_NUM, number);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CONT, contract);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_DESC, description);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_CIT, it_category);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_IDI, id_hardware);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_IDS, id_software);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_HAR, code);
                    i.putExtra(DetailOnProgressInstallHclab.KEY_SOF, version);
                    startActivity(i);
                  }
                } else if (id_division.equals("3") && category.equals("PM")) {
                  Intent i = new Intent(MyTiketFragment.this.getContext(),
                      DetailPmIt.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailPmIt.KEY_URI, idtiket);
                  i.putExtra(DetailPmIt.KEY_CAT, category);
                  i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                  i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                  i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                  i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                  i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                  i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                  i.putExtra(DetailPmIt.KEY_INS, instrument);
                  i.putExtra(DetailPmIt.KEY_PRIO, priority);
                  i.putExtra(DetailPmIt.KEY_NUM, number);
                  i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                  i.putExtra(DetailPmIt.KEY_CONT, contract);
                  i.putExtra(DetailPmIt.KEY_DESC, description);
                  i.putExtra(DetailPmIt.KEY_CIT, it_category);
                  i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                  i.putExtra(DetailPmIt.KEY_IDS, id_software);
                  i.putExtra(DetailPmIt.KEY_HAR, code);
                  i.putExtra(DetailPmIt.KEY_SOF, version);
                  startActivity(i);
                } else if (id_division.equals("3") && category.equals("PU")) {
                  Intent i = new Intent(MyTiketFragment.this.getContext(),
                      DetailPmIt.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailPmIt.KEY_URI, idtiket);
                  i.putExtra(DetailPmIt.KEY_CAT, category);
                  i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                  i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                  i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                  i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                  i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                  i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                  i.putExtra(DetailPmIt.KEY_INS, instrument);
                  i.putExtra(DetailPmIt.KEY_PRIO, priority);
                  i.putExtra(DetailPmIt.KEY_NUM, number);
                  i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                  i.putExtra(DetailPmIt.KEY_CONT, contract);
                  i.putExtra(DetailPmIt.KEY_DESC, description);
                  i.putExtra(DetailPmIt.KEY_CIT, it_category);
                  i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                  i.putExtra(DetailPmIt.KEY_IDS, id_software);
                  i.putExtra(DetailPmIt.KEY_HAR, code);
                  i.putExtra(DetailPmIt.KEY_SOF, version);
                  startActivity(i);
                } else if (id_division.equals("3") && category.equals("Visit")) {
                  Intent i = new Intent(MyTiketFragment.this.getContext(),
                      DetailOnProgressVisitIT.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgressVisitIT.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgressVisitIT.KEY_CAT, category);
                  i.putExtra(DetailOnProgressVisitIT.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgressVisitIT.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgressVisitIT.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgressVisitIT.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgressVisitIT.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgressVisitIT.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgressVisitIT.KEY_INS, instrument);
                  i.putExtra(DetailOnProgressVisitIT.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgressVisitIT.KEY_NUM, number);
                  i.putExtra(DetailOnProgressVisitIT.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgressVisitIT.KEY_CONT, contract);
                  i.putExtra(DetailOnProgressVisitIT.KEY_DESC, description);
                  i.putExtra(DetailOnProgressVisitIT.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgressVisitIT.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgressVisitIT.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgressVisitIT.KEY_HAR, code);
                  i.putExtra(DetailOnProgressVisitIT.KEY_SOF, version);
                  startActivity(i);
                } else if (category.equals("Visit")) {
                  Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                  startActivity(i);
                }
                else if (category.equals("PM")) {
                  Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                  i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                  startActivity(i);
                } else if (category.equals("Installation")) {
                  Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                  i.putExtra(DetailInstrumentForm.KEY_CAT, category);
                  i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
                  i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                  i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
                  i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
                  i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
                  i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
                  i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
                  i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
                  i.putExtra(DetailInstrumentForm.KEY_NUM, number);
                  i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
                  i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
                  i.putExtra(DetailInstrumentForm.KEY_DESC, description);
                  i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
                  i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
                  i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
                  i.putExtra(DetailInstrumentForm.KEY_HAR, code);
                  i.putExtra(DetailInstrumentForm.KEY_SOF, version);
                  startActivity(i);
                } else if (category.equals("Return")) {
                  Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                  i.putExtra(DetailInstrumentForm.KEY_CAT, category);
                  i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
                  i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                  i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
                  i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
                  i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
                  i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
                  i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
                  i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
                  i.putExtra(DetailInstrumentForm.KEY_NUM, number);
                  i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
                  i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
                  i.putExtra(DetailInstrumentForm.KEY_DESC, description);
                  i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
                  i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
                  i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
                  i.putExtra(DetailInstrumentForm.KEY_HAR, code);
                  i.putExtra(DetailInstrumentForm.KEY_SOF, version);
                  startActivity(i);
                } else {
                  Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgressNew.KEY_CAT, category);
                  i.putExtra(DetailOnProgressNew.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgressNew.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgressNew.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgressNew.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgressNew.KEY_INS, instrument);
                  i.putExtra(DetailOnProgressNew.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgressNew.KEY_NUM, number);
                  i.putExtra(DetailOnProgressNew.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgressNew.KEY_CONT, contract);
                  i.putExtra(DetailOnProgressNew.KEY_DESC, description);
                  i.putExtra(DetailOnProgressNew.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgressNew.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgressNew.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgressNew.KEY_HAR, code);
                  i.putExtra(DetailOnProgressNew.KEY_SOF, version);
                  startActivity(i);
                }
              } else if (status.equals("held")) {
                Intent i = new Intent(getContext(), DetailOnHold.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnHold.KEY_URI, idtiket);
                i.putExtra(DetailOnHold.KEY_CAT, category);
                i.putExtra(DetailOnHold.KEY_TICK, ticket_type);
                i.putExtra(DetailOnHold.KEY_CUST, customer_id);
                i.putExtra(DetailOnHold.KEY_ACTI, id_activity);
                i.putExtra(DetailOnHold.KEY_SNAME, staff_name);
                i.putExtra(DetailOnHold.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnHold.KEY_INST, instrument_type);
                i.putExtra(DetailOnHold.KEY_INS, instrument);
                i.putExtra(DetailOnHold.KEY_PRIO, priority);
                i.putExtra(DetailOnHold.KEY_NUM, number);
                i.putExtra(DetailOnHold.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnHold.KEY_CONT, contract);
                i.putExtra(DetailOnHold.KEY_DESC, description);
                i.putExtra(DetailOnHold.KEY_CIT, it_category);
                i.putExtra(DetailOnHold.KEY_IDI, id_hardware);
                i.putExtra(DetailOnHold.KEY_IDS, id_software);
                i.putExtra(DetailOnHold.KEY_HAR, code);
                i.putExtra(DetailOnHold.KEY_SOF, version);
                startActivity(i);
              } else if (status.equals("restarted")) {
                Intent i = new Intent(getContext(), DetailOnProgressHold.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressHold.KEY_CAT, category);
                i.putExtra(DetailOnProgressHold.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressHold.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressHold.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressHold.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressHold.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressHold.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressHold.KEY_INS, instrument);
                i.putExtra(DetailOnProgressHold.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressHold.KEY_NUM, number);
                i.putExtra(DetailOnProgressHold.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressHold.KEY_CONT, contract);
                i.putExtra(DetailOnProgressHold.KEY_DESC, description);
                i.putExtra(DetailOnProgressHold.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressHold.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressHold.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressHold.KEY_HAR, code);
                i.putExtra(DetailOnProgressHold.KEY_SOF, version);
                startActivity(i);
              } else if (status.equals("done")) {
                Intent i = new Intent(getContext(), DetailEnded.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailEnded.KEY_URI, idtiket);
                i.putExtra(DetailEnded.KEY_CAT, category);
                i.putExtra(DetailEnded.KEY_TICK, ticket_type);
                i.putExtra(DetailEnded.KEY_CUST, customer_id);
                i.putExtra(DetailEnded.KEY_ACTI, id_activity);
                i.putExtra(DetailEnded.KEY_SNAME, staff_name);
                i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
                i.putExtra(DetailEnded.KEY_INST, instrument_type);
                i.putExtra(DetailEnded.KEY_INS, instrument);
                i.putExtra(DetailEnded.KEY_PRIO, priority);
                i.putExtra(DetailEnded.KEY_NUM, number);
                i.putExtra(DetailEnded.KEY_CUSTN, customer_name);
                i.putExtra(DetailEnded.KEY_CONT, contract);
                i.putExtra(DetailEnded.KEY_DESC, description);
                i.putExtra(DetailEnded.KEY_CIT, it_category);
                i.putExtra(DetailEnded.KEY_IDI, id_hardware);
                i.putExtra(DetailEnded.KEY_IDS, id_software);
                i.putExtra(DetailEnded.KEY_HAR, code);
                i.putExtra(DetailEnded.KEY_SOF, version);
                startActivity(i);
              } else {
                glpref.read(PrefKey.statustiket, String.class);
              }
            }
          });
      mApi.searchtiket(accessToken,name)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(responseSearchTiket1 -> {
            pDialog.dismiss();
            Log.e(TAG, "onNext: "+ responseSearchTiket1.getData().toString() );
            if (responseSearchTiket1.getData() != null) {
              adapterSearchTiket.UpdateTikets(responseSearchTiket1.getData());
            } else {
              Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
          },throwable -> pDialog.dismiss());
      rcvTiket.setAdapter(adapterSearchTiket);
    }

    private void loadDataTiketall() {
        pDialog.show();
      Observable<ResponseTikets> respontiket = mApi
            .getTiketall(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
        adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            new OnTiketPostItemListener() {
              @Override
              public void onPostClickListener(int id, String status, String ticket_type,
                  int id_customer,
                  String category, int activity_id, String staff_name, String staff_phone,
                  String instrument_type, String instrument, String priority, String number,
                  String customer_name, String contract, String description, String it_category,
                  int hardware_id, int software_id, String code, String version,int id_employee) {
                if (status != null) {
                  if (status.equals("new")) {
                    Intent i = new Intent(getContext(), DetailOpenTiket.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
                    i.putExtra(DetailOpenTiket.KEY_CAT, category);
                    i.putExtra(DetailOpenTiket.KEY_TICK, ticket_type);
                    i.putExtra(DetailOpenTiket.KEY_CUST, customer_id);
                    i.putExtra(DetailOpenTiket.KEY_ACTI, id_activity);
                    i.putExtra(DetailOpenTiket.KEY_SNAME, staff_name);
                    i.putExtra(DetailOpenTiket.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOpenTiket.KEY_INST, instrument_type);
                    i.putExtra(DetailOpenTiket.KEY_INS, instrument);
                    i.putExtra(DetailOpenTiket.KEY_PRIO, priority);
                    i.putExtra(DetailOpenTiket.KEY_NUM, number);
                    i.putExtra(DetailOpenTiket.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOpenTiket.KEY_CONT, contract);
                    i.putExtra(DetailOpenTiket.KEY_DESC, description);
                    i.putExtra(DetailOpenTiket.KEY_CIT, it_category);
                    i.putExtra(DetailOpenTiket.KEY_IDI, id_hardware);
                    i.putExtra(DetailOpenTiket.KEY_IDS, id_software);
                    i.putExtra(DetailOpenTiket.KEY_HAR, code);
                    i.putExtra(DetailOpenTiket.KEY_SOF, version);
                    startActivity(i);
                  } else if (status.equals("confirmed")) {
                    Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
                    i.putExtra(DetailConfirmedTiket.KEY_CAT, category);
                    i.putExtra(DetailConfirmedTiket.KEY_TICK, ticket_type);
                    i.putExtra(DetailConfirmedTiket.KEY_CUST, customer_id);
                    i.putExtra(DetailConfirmedTiket.KEY_ACTI, id_activity);
                    i.putExtra(DetailConfirmedTiket.KEY_SNAME, staff_name);
                    i.putExtra(DetailConfirmedTiket.KEY_SPHN, staff_phone);
                    i.putExtra(DetailConfirmedTiket.KEY_INST, instrument_type);
                    i.putExtra(DetailConfirmedTiket.KEY_INS, instrument);
                    i.putExtra(DetailConfirmedTiket.KEY_PRIO, priority);
                    i.putExtra(DetailConfirmedTiket.KEY_NUM, number);
                    i.putExtra(DetailConfirmedTiket.KEY_CUSTN, customer_name);
                    i.putExtra(DetailConfirmedTiket.KEY_CONT, contract);
                    i.putExtra(DetailConfirmedTiket.KEY_DESC, description);
                    i.putExtra(DetailConfirmedTiket.KEY_CIT, it_category);
                    i.putExtra(DetailConfirmedTiket.KEY_IDI, id_hardware);
                    i.putExtra(DetailConfirmedTiket.KEY_IDS, id_software);
                    i.putExtra(DetailConfirmedTiket.KEY_HAR, code);
                    i.putExtra(DetailConfirmedTiket.KEY_SOF, version);
                    startActivity(i);
                  } else if (status.equals("started")) {
                    if (id_division.equals("3") && category.equals("Installation")) {
                      if (it_category.equals("Hardware")) {
                        Intent i = new Intent(getContext(), DetailOnProgressInstallAnalyzer.class);
                        String idtiket = String.valueOf(id);
                        String customer_id = String.valueOf(id_customer);
                        String id_activity = String.valueOf(activity_id);
                        String id_hardware = String.valueOf(hardware_id);
                        String id_software = String.valueOf(software_id);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_URI, idtiket);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CAT, category);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_TICK, ticket_type);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUST, customer_id);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_ACTI, id_activity);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SNAME, staff_name);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SPHN, staff_phone);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INST, instrument_type);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INS, instrument);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_PRIO, priority);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_NUM, number);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUSTN, customer_name);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CONT, contract);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_DESC, description);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CIT, it_category);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDI, id_hardware);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDS, id_software);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_HAR, code);
                        i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SOF, version);
                        startActivity(i);
                      } else if (it_category.equals("Software")) {
                        Intent i = new Intent(MyTiketFragment.this.getContext(),
                            DetailOnProgressInstallHclab.class);
                        String idtiket = String.valueOf(id);
                        String customer_id = String.valueOf(id_customer);
                        String id_activity = String.valueOf(activity_id);
                        String id_hardware = String.valueOf(hardware_id);
                        String id_software = String.valueOf(software_id);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_URI, idtiket);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_CAT, category);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_TICK, ticket_type);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_CUST, customer_id);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_ACTI, id_activity);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_SNAME, staff_name);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_SPHN, staff_phone);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_INST, instrument_type);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_INS, instrument);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_PRIO, priority);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_NUM, number);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_CUSTN, customer_name);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_CONT, contract);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_DESC, description);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_CIT, it_category);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_IDI, id_hardware);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_IDS, id_software);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_HAR, code);
                        i.putExtra(DetailOnProgressInstallHclab.KEY_SOF, version);
                        startActivity(i);
                      }
                    } else if (id_division.equals("3") && category.equals("PM")) {
                      Intent i = new Intent(MyTiketFragment.this.getContext(),
                          DetailPmIt.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailPmIt.KEY_URI, idtiket);
                      i.putExtra(DetailPmIt.KEY_CAT, category);
                      i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                      i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                      i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                      i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                      i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                      i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                      i.putExtra(DetailPmIt.KEY_INS, instrument);
                      i.putExtra(DetailPmIt.KEY_PRIO, priority);
                      i.putExtra(DetailPmIt.KEY_NUM, number);
                      i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                      i.putExtra(DetailPmIt.KEY_CONT, contract);
                      i.putExtra(DetailPmIt.KEY_DESC, description);
                      i.putExtra(DetailPmIt.KEY_CIT, it_category);
                      i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                      i.putExtra(DetailPmIt.KEY_IDS, id_software);
                      i.putExtra(DetailPmIt.KEY_HAR, code);
                      i.putExtra(DetailPmIt.KEY_SOF, version);
                      startActivity(i);
                    } else if (id_division.equals("3") && category.equals("PU")) {
                      Intent i = new Intent(MyTiketFragment.this.getContext(),
                          DetailPmIt.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailPmIt.KEY_URI, idtiket);
                      i.putExtra(DetailPmIt.KEY_CAT, category);
                      i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                      i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                      i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                      i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                      i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                      i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                      i.putExtra(DetailPmIt.KEY_INS, instrument);
                      i.putExtra(DetailPmIt.KEY_PRIO, priority);
                      i.putExtra(DetailPmIt.KEY_NUM, number);
                      i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                      i.putExtra(DetailPmIt.KEY_CONT, contract);
                      i.putExtra(DetailPmIt.KEY_DESC, description);
                      i.putExtra(DetailPmIt.KEY_CIT, it_category);
                      i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                      i.putExtra(DetailPmIt.KEY_IDS, id_software);
                      i.putExtra(DetailPmIt.KEY_HAR, code);
                      i.putExtra(DetailPmIt.KEY_SOF, version);
                      startActivity(i);
                    } else if (id_division.equals("3") && category.equals("Visit")) {
                      Intent i = new Intent(MyTiketFragment.this.getContext(),
                          DetailOnProgressVisitIT.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailOnProgressVisitIT.KEY_URI, idtiket);
                      i.putExtra(DetailOnProgressVisitIT.KEY_CAT, category);
                      i.putExtra(DetailOnProgressVisitIT.KEY_TICK, ticket_type);
                      i.putExtra(DetailOnProgressVisitIT.KEY_CUST, customer_id);
                      i.putExtra(DetailOnProgressVisitIT.KEY_ACTI, id_activity);
                      i.putExtra(DetailOnProgressVisitIT.KEY_SNAME, staff_name);
                      i.putExtra(DetailOnProgressVisitIT.KEY_SPHN, staff_phone);
                      i.putExtra(DetailOnProgressVisitIT.KEY_INST, instrument_type);
                      i.putExtra(DetailOnProgressVisitIT.KEY_INS, instrument);
                      i.putExtra(DetailOnProgressVisitIT.KEY_PRIO, priority);
                      i.putExtra(DetailOnProgressVisitIT.KEY_NUM, number);
                      i.putExtra(DetailOnProgressVisitIT.KEY_CUSTN, customer_name);
                      i.putExtra(DetailOnProgressVisitIT.KEY_CONT, contract);
                      i.putExtra(DetailOnProgressVisitIT.KEY_DESC, description);
                      i.putExtra(DetailOnProgressVisitIT.KEY_CIT, it_category);
                      i.putExtra(DetailOnProgressVisitIT.KEY_IDI, id_hardware);
                      i.putExtra(DetailOnProgressVisitIT.KEY_IDS, id_software);
                      i.putExtra(DetailOnProgressVisitIT.KEY_HAR, code);
                      i.putExtra(DetailOnProgressVisitIT.KEY_SOF, version);
                      startActivity(i);
                    } else if (category.equals("Visit")) {
                      Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                      startActivity(i);
                    }
                    else if (category.equals("PM")) {
                      Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                      i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                      startActivity(i);
                    } else if (category.equals("Installation")) {
                      Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                      i.putExtra(DetailInstrumentForm.KEY_CAT, category);
                      i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
                      i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                      i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
                      i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
                      i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
                      i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
                      i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
                      i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
                      i.putExtra(DetailInstrumentForm.KEY_NUM, number);
                      i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
                      i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
                      i.putExtra(DetailInstrumentForm.KEY_DESC, description);
                      i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
                      i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
                      i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
                      i.putExtra(DetailInstrumentForm.KEY_HAR, code);
                      i.putExtra(DetailInstrumentForm.KEY_SOF, version);
                      startActivity(i);
                    } else if (category.equals("Return")) {
                      Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                      i.putExtra(DetailInstrumentForm.KEY_CAT, category);
                      i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
                      i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                      i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
                      i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
                      i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
                      i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
                      i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
                      i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
                      i.putExtra(DetailInstrumentForm.KEY_NUM, number);
                      i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
                      i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
                      i.putExtra(DetailInstrumentForm.KEY_DESC, description);
                      i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
                      i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
                      i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
                      i.putExtra(DetailInstrumentForm.KEY_HAR, code);
                      i.putExtra(DetailInstrumentForm.KEY_SOF, version);
                      startActivity(i);
                    } else {
                      Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                      i.putExtra(DetailOnProgressNew.KEY_CAT, category);
                      i.putExtra(DetailOnProgressNew.KEY_TICK, ticket_type);
                      i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
                      i.putExtra(DetailOnProgressNew.KEY_ACTI, id_activity);
                      i.putExtra(DetailOnProgressNew.KEY_SNAME, staff_name);
                      i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
                      i.putExtra(DetailOnProgressNew.KEY_INST, instrument_type);
                      i.putExtra(DetailOnProgressNew.KEY_INS, instrument);
                      i.putExtra(DetailOnProgressNew.KEY_PRIO, priority);
                      i.putExtra(DetailOnProgressNew.KEY_NUM, number);
                      i.putExtra(DetailOnProgressNew.KEY_CUSTN, customer_name);
                      i.putExtra(DetailOnProgressNew.KEY_CONT, contract);
                      i.putExtra(DetailOnProgressNew.KEY_DESC, description);
                      i.putExtra(DetailOnProgressNew.KEY_CIT, it_category);
                      i.putExtra(DetailOnProgressNew.KEY_IDI, id_hardware);
                      i.putExtra(DetailOnProgressNew.KEY_IDS, id_software);
                      i.putExtra(DetailOnProgressNew.KEY_HAR, code);
                      i.putExtra(DetailOnProgressNew.KEY_SOF, version);
                      startActivity(i);
                    }
                  } else if (status.equals("held")) {
                    Intent i = new Intent(getContext(), DetailOnHold.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnHold.KEY_URI, idtiket);
                    i.putExtra(DetailOnHold.KEY_CAT, category);
                    i.putExtra(DetailOnHold.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnHold.KEY_CUST, customer_id);
                    i.putExtra(DetailOnHold.KEY_ACTI, id_activity);
                    i.putExtra(DetailOnHold.KEY_SNAME, staff_name);
                    i.putExtra(DetailOnHold.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnHold.KEY_INST, instrument_type);
                    i.putExtra(DetailOnHold.KEY_INS, instrument);
                    i.putExtra(DetailOnHold.KEY_PRIO, priority);
                    i.putExtra(DetailOnHold.KEY_NUM, number);
                    i.putExtra(DetailOnHold.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOnHold.KEY_CONT, contract);
                    i.putExtra(DetailOnHold.KEY_DESC, description);
                    i.putExtra(DetailOnHold.KEY_CIT, it_category);
                    i.putExtra(DetailOnHold.KEY_IDI, id_hardware);
                    i.putExtra(DetailOnHold.KEY_IDS, id_software);
                    i.putExtra(DetailOnHold.KEY_HAR, code);
                    i.putExtra(DetailOnHold.KEY_SOF, version);
                    startActivity(i);
                  } else if (status.equals("restarted")) {
                    Intent i = new Intent(getContext(), DetailOnProgressHold.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressHold.KEY_CAT, category);
                    i.putExtra(DetailOnProgressHold.KEY_TICK, ticket_type);
                    i.putExtra(DetailOnProgressHold.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgressHold.KEY_ACTI, id_activity);
                    i.putExtra(DetailOnProgressHold.KEY_SNAME, staff_name);
                    i.putExtra(DetailOnProgressHold.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgressHold.KEY_INST, instrument_type);
                    i.putExtra(DetailOnProgressHold.KEY_INS, instrument);
                    i.putExtra(DetailOnProgressHold.KEY_PRIO, priority);
                    i.putExtra(DetailOnProgressHold.KEY_NUM, number);
                    i.putExtra(DetailOnProgressHold.KEY_CUSTN, customer_name);
                    i.putExtra(DetailOnProgressHold.KEY_CONT, contract);
                    i.putExtra(DetailOnProgressHold.KEY_DESC, description);
                    i.putExtra(DetailOnProgressHold.KEY_CIT, it_category);
                    i.putExtra(DetailOnProgressHold.KEY_IDI, id_hardware);
                    i.putExtra(DetailOnProgressHold.KEY_IDS, id_software);
                    i.putExtra(DetailOnProgressHold.KEY_HAR, code);
                    i.putExtra(DetailOnProgressHold.KEY_SOF, version);
                    startActivity(i);
                  } else if (status.equals("done")) {
                    Intent i = new Intent(getContext(), DetailEnded.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    String id_hardware = String.valueOf(hardware_id);
                    String id_software = String.valueOf(software_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailEnded.KEY_URI, idtiket);
                    i.putExtra(DetailEnded.KEY_CAT, category);
                    i.putExtra(DetailEnded.KEY_TICK, ticket_type);
                    i.putExtra(DetailEnded.KEY_CUST, customer_id);
                    i.putExtra(DetailEnded.KEY_ACTI, id_activity);
                    i.putExtra(DetailEnded.KEY_SNAME, staff_name);
                    i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
                    i.putExtra(DetailEnded.KEY_INST, instrument_type);
                    i.putExtra(DetailEnded.KEY_INS, instrument);
                    i.putExtra(DetailEnded.KEY_PRIO, priority);
                    i.putExtra(DetailEnded.KEY_NUM, number);
                    i.putExtra(DetailEnded.KEY_CUSTN, customer_name);
                    i.putExtra(DetailEnded.KEY_CONT, contract);
                    i.putExtra(DetailEnded.KEY_DESC, description);
                    i.putExtra(DetailEnded.KEY_CIT, it_category);
                    i.putExtra(DetailEnded.KEY_IDI, id_hardware);
                    i.putExtra(DetailEnded.KEY_IDS, id_software);
                    i.putExtra(DetailEnded.KEY_HAR, code);
                    i.putExtra(DetailEnded.KEY_SOF, version);
                    startActivity(i);
                  } else {
                    glpref.read(PrefKey.statustiket, String.class);
                  }
                }
              }
            });
        rcvTiket.setAdapter(adapterTiketAll);
      }

    private void loadDataTiketonprogresshold() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketrestarted(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");
        });
      adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0),
            getContext(),
          new OnTiketPostItemListener() {
            @Override
            public void onPostClickListener(int id, String status, String ticket_type,
                int id_customer,
                String category, int activity_id, String staff_name, String staff_phone,
                String instrument_type, String instrument, String priority, String number,
                String customer_name, String contract, String description, String it_category,
                int hardware_id, int software_id, String code, String version,int id_employee) {
              if (id_division.equals("3") && category.equals("Installation")) {
                if (it_category.equals("Interfaces")) {
                  Intent i = new Intent(MyTiketFragment.this.getContext(),
                      DetailOnProgressHoldInstallAnalyzer.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CAT, category);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_INS, instrument);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_NUM, number);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CONT, contract);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_DESC, description);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_HAR, code);
                  i.putExtra(DetailOnProgressHoldInstallAnalyzer.KEY_SOF, version);
                  startActivity(i);
                } else if (it_category.equals("Software")) {
                  Intent i = new Intent(MyTiketFragment.this.getContext(),
                      DetailOnProgressHoldInstallHclab.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  String id_hardware = String.valueOf(hardware_id);
                  String id_software = String.valueOf(software_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_CAT, category);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_TICK, ticket_type);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_ACTI, id_activity);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_SNAME, staff_name);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_INST, instrument_type);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_INS, instrument);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_PRIO, priority);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_NUM, number);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_CUSTN, customer_name);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_CONT, contract);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_DESC, description);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_CIT, it_category);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_IDI, id_hardware);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_IDS, id_software);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_HAR, code);
                  i.putExtra(DetailOnProgressHoldInstallHclab.KEY_SOF, version);
                  startActivity(i);
                }
              } else if (id_division.equals("3") && category.equals("PU")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailPmIt.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailPmIt.KEY_URI, idtiket);
                i.putExtra(DetailPmIt.KEY_CAT, category);
                i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                i.putExtra(DetailPmIt.KEY_INS, instrument);
                i.putExtra(DetailPmIt.KEY_PRIO, priority);
                i.putExtra(DetailPmIt.KEY_NUM, number);
                i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                i.putExtra(DetailPmIt.KEY_CONT, contract);
                i.putExtra(DetailPmIt.KEY_DESC, description);
                i.putExtra(DetailPmIt.KEY_CIT, it_category);
                i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                i.putExtra(DetailPmIt.KEY_IDS, id_software);
                i.putExtra(DetailPmIt.KEY_HAR, code);
                i.putExtra(DetailPmIt.KEY_SOF, version);
                startActivity(i);
              } else if (id_division.equals("3") && category.equals("PM")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailPmIt.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailPmIt.KEY_URI, idtiket);
                i.putExtra(DetailPmIt.KEY_CAT, category);
                i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                i.putExtra(DetailPmIt.KEY_INS, instrument);
                i.putExtra(DetailPmIt.KEY_PRIO, priority);
                i.putExtra(DetailPmIt.KEY_NUM, number);
                i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                i.putExtra(DetailPmIt.KEY_CONT, contract);
                i.putExtra(DetailPmIt.KEY_DESC, description);
                i.putExtra(DetailPmIt.KEY_CIT, it_category);
                i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                i.putExtra(DetailPmIt.KEY_IDS, id_software);
                i.putExtra(DetailPmIt.KEY_HAR, code);
                i.putExtra(DetailPmIt.KEY_SOF, version);
                startActivity(i);
              } else if (id_division.equals("3") && category.equals("Visit")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailOnProgressVisitIT.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressVisitIT.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressVisitIT.KEY_CAT, category);
                i.putExtra(DetailOnProgressVisitIT.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressVisitIT.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressVisitIT.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressVisitIT.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressVisitIT.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressVisitIT.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressVisitIT.KEY_INS, instrument);
                i.putExtra(DetailOnProgressVisitIT.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressVisitIT.KEY_NUM, number);
                i.putExtra(DetailOnProgressVisitIT.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressVisitIT.KEY_CONT, contract);
                i.putExtra(DetailOnProgressVisitIT.KEY_DESC, description);
                i.putExtra(DetailOnProgressVisitIT.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressVisitIT.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressVisitIT.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressVisitIT.KEY_HAR, code);
                i.putExtra(DetailOnProgressVisitIT.KEY_SOF, version);
                startActivity(i);
              } else if (category.equals("Visit")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                startActivity(i);
              } else if (category.equals("PM")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
                startActivity(i);
              } else if (category.equals("Installation")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailShippingHold.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailShippingHold.KEY_URI, idtiket);
                i.putExtra(DetailShippingHold.KEY_CAT, category);
                i.putExtra(DetailShippingHold.KEY_TICK, ticket_type);
                i.putExtra(DetailShippingHold.KEY_CUST, customer_id);
                i.putExtra(DetailShippingHold.KEY_ACTI, id_activity);
                i.putExtra(DetailShippingHold.KEY_SNAME, staff_name);
                i.putExtra(DetailShippingHold.KEY_SPHN, staff_phone);
                i.putExtra(DetailShippingHold.KEY_INST, instrument_type);
                i.putExtra(DetailShippingHold.KEY_INS, instrument);
                i.putExtra(DetailShippingHold.KEY_PRIO, priority);
                i.putExtra(DetailShippingHold.KEY_NUM, number);
                i.putExtra(DetailShippingHold.KEY_CUSTN, customer_name);
                i.putExtra(DetailShippingHold.KEY_CONT, contract);
                i.putExtra(DetailShippingHold.KEY_DESC, description);
                i.putExtra(DetailShippingHold.KEY_CIT, it_category);
                i.putExtra(DetailShippingHold.KEY_IDI, id_hardware);
                i.putExtra(DetailShippingHold.KEY_IDS, id_software);
                i.putExtra(DetailShippingHold.KEY_HAR, code);
                i.putExtra(DetailShippingHold.KEY_SOF, version);
                startActivity(i);
              } else if (category.equals("Return")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailShippingHold.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.putExtra(DetailShippingHold.KEY_URI, idtiket);
                i.putExtra(DetailShippingHold.KEY_CAT, category);
                i.putExtra(DetailShippingHold.KEY_TICK, ticket_type);
                i.putExtra(DetailShippingHold.KEY_CUST, customer_id);
                i.putExtra(DetailShippingHold.KEY_ACTI, id_activity);
                i.putExtra(DetailShippingHold.KEY_SNAME, staff_name);
                i.putExtra(DetailShippingHold.KEY_SPHN, staff_phone);
                i.putExtra(DetailShippingHold.KEY_INST, instrument_type);
                i.putExtra(DetailShippingHold.KEY_INS, instrument);
                i.putExtra(DetailShippingHold.KEY_PRIO, priority);
                i.putExtra(DetailShippingHold.KEY_NUM, number);
                i.putExtra(DetailShippingHold.KEY_CUSTN, customer_name);
                i.putExtra(DetailShippingHold.KEY_CONT, contract);
                i.putExtra(DetailShippingHold.KEY_DESC, description);
                i.putExtra(DetailShippingHold.KEY_CIT, it_category);
                i.putExtra(DetailShippingHold.KEY_IDI, id_hardware);
                i.putExtra(DetailShippingHold.KEY_IDS, id_software);
                i.putExtra(DetailShippingHold.KEY_HAR, code);
                i.putExtra(DetailShippingHold.KEY_SOF, version);
                startActivity(i);
              } else {
                Intent i = new Intent(MyTiketFragment.this.getContext(), DetailOnProgressHold.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressHold.KEY_CAT, category);
                i.putExtra(DetailOnProgressHold.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressHold.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressHold.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressHold.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressHold.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressHold.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressHold.KEY_INS, instrument);
                i.putExtra(DetailOnProgressHold.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressHold.KEY_NUM, number);
                i.putExtra(DetailOnProgressHold.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressHold.KEY_CONT, contract);
                i.putExtra(DetailOnProgressHold.KEY_DESC, description);
                i.putExtra(DetailOnProgressHold.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressHold.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressHold.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressHold.KEY_HAR, code);
                i.putExtra(DetailOnProgressHold.KEY_SOF, version);
                startActivity(i);
              }
            }
      });
        rcvTiket.setAdapter(adapterTiketAll);
      }

  private void loadDataTiketended() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketended(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(getContext(), DetailEnded.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailEnded.KEY_URI, idtiket);
            i.putExtra(DetailEnded.KEY_CAT, category);
            i.putExtra(DetailEnded.KEY_TICK, ticket_type);
            i.putExtra(DetailEnded.KEY_CUST, customer_id);
            i.putExtra(DetailEnded.KEY_ACTI, id_activity);
            i.putExtra(DetailEnded.KEY_SNAME, staff_name);
            i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
            i.putExtra(DetailEnded.KEY_INST, instrument_type);
            i.putExtra(DetailEnded.KEY_INS, instrument);
            i.putExtra(DetailEnded.KEY_PRIO, priority);
            i.putExtra(DetailEnded.KEY_NUM, number);
            i.putExtra(DetailEnded.KEY_CUSTN, customer_name);
            i.putExtra(DetailEnded.KEY_CONT, contract);
            i.putExtra(DetailEnded.KEY_DESC, description);
            i.putExtra(DetailEnded.KEY_CIT, it_category);
            i.putExtra(DetailEnded.KEY_IDI, id_hardware);
            i.putExtra(DetailEnded.KEY_IDS, id_software);
            i.putExtra(DetailEnded.KEY_HAR, code);
            i.putExtra(DetailEnded.KEY_SOF, version);
            startActivity(i);
          }
        });
        rcvTiket.setAdapter(adapterTiketAll);
      }

  private void loadDataTiketonhold() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketheld(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(getContext(), DetailOnHold.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailOnHold.KEY_URI, idtiket);
            i.putExtra(DetailOnHold.KEY_CAT, category);
            i.putExtra(DetailOnHold.KEY_TICK, ticket_type);
            i.putExtra(DetailOnHold.KEY_CUST, customer_id);
            i.putExtra(DetailOnHold.KEY_ACTI, id_activity);
            i.putExtra(DetailOnHold.KEY_SNAME, staff_name);
            i.putExtra(DetailOnHold.KEY_SPHN, staff_phone);
            i.putExtra(DetailOnHold.KEY_INST, instrument_type);
            i.putExtra(DetailOnHold.KEY_INS, instrument);
            i.putExtra(DetailOnHold.KEY_PRIO, priority);
            i.putExtra(DetailOnHold.KEY_NUM, number);
            i.putExtra(DetailOnHold.KEY_CUSTN, customer_name);
            i.putExtra(DetailOnHold.KEY_CONT, contract);
            i.putExtra(DetailOnHold.KEY_DESC, description);
            i.putExtra(DetailOnHold.KEY_CIT, it_category);
            i.putExtra(DetailOnHold.KEY_IDI, id_hardware);
            i.putExtra(DetailOnHold.KEY_IDS, id_software);
            i.putExtra(DetailOnHold.KEY_HAR, code);
            i.putExtra(DetailOnHold.KEY_SOF, version);
            startActivity(i);
          }});
        rcvTiket.setAdapter(adapterTiketAll);
      }

  private void loadDataTiketonprogress() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketstarted(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0),
            getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee)  {
            Log.e("loadDataTiketonprogress", "MyTiketFragment" + category);
            Log.e("loadDataTiketonprogress", "MyTiketFragment" + hardware_id);
            if (id_division.equals("3") && category.equals("Installation")) {
              if (it_category.equals("Hardware")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailOnProgressInstallAnalyzer.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CAT, category);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INS, instrument);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_NUM, number);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CONT, contract);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_DESC, description);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_HAR, code);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SOF, version);
                startActivity(i);
              } else if (it_category.equals("Software")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailOnProgressInstallHclab.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressInstallHclab.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressInstallHclab.KEY_CAT, category);
                i.putExtra(DetailOnProgressInstallHclab.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressInstallHclab.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressInstallHclab.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressInstallHclab.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressInstallHclab.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressInstallHclab.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressInstallHclab.KEY_INS, instrument);
                i.putExtra(DetailOnProgressInstallHclab.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressInstallHclab.KEY_NUM, number);
                i.putExtra(DetailOnProgressInstallHclab.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressInstallHclab.KEY_CONT, contract);
                i.putExtra(DetailOnProgressInstallHclab.KEY_DESC, description);
                i.putExtra(DetailOnProgressInstallHclab.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressInstallHclab.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressInstallHclab.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressInstallHclab.KEY_HAR, code);
                i.putExtra(DetailOnProgressInstallHclab.KEY_SOF, version);
                startActivity(i);
              } else if (it_category.equals("Interfaces")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                        DetailOnProgressInstallAnalyzer.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CAT, category);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_TICK, ticket_type);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_ACTI, id_activity);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SNAME, staff_name);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INST, instrument_type);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_INS, instrument);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_PRIO, priority);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_NUM, number);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CUSTN, customer_name);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CONT, contract);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_DESC, description);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_CIT, it_category);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDI, id_hardware);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_IDS, id_software);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_HAR, code);
                i.putExtra(DetailOnProgressInstallAnalyzer.KEY_SOF, version);
                startActivity(i);
              }
            } else if (id_division.equals("3") && category.equals("PM")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(),
                  DetailPmIt.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailPmIt.KEY_URI, idtiket);
              i.putExtra(DetailPmIt.KEY_CAT, category);
              i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
              i.putExtra(DetailPmIt.KEY_CUST, customer_id);
              i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
              i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
              i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
              i.putExtra(DetailPmIt.KEY_INST, instrument_type);
              i.putExtra(DetailPmIt.KEY_INS, instrument);
              i.putExtra(DetailPmIt.KEY_PRIO, priority);
              i.putExtra(DetailPmIt.KEY_NUM, number);
              i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
              i.putExtra(DetailPmIt.KEY_CONT, contract);
              i.putExtra(DetailPmIt.KEY_DESC, description);
              i.putExtra(DetailPmIt.KEY_CIT, it_category);
              i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
              i.putExtra(DetailPmIt.KEY_IDS, id_software);
              i.putExtra(DetailPmIt.KEY_HAR, code);
              i.putExtra(DetailPmIt.KEY_SOF, version);
              startActivity(i);
            } else if (id_division.equals("3") && category.equals("PU")) {
                Intent i = new Intent(MyTiketFragment.this.getContext(),
                    DetailPmIt.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                String id_hardware = String.valueOf(hardware_id);
                String id_software = String.valueOf(software_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailPmIt.KEY_URI, idtiket);
                i.putExtra(DetailPmIt.KEY_CAT, category);
                i.putExtra(DetailPmIt.KEY_TICK, ticket_type);
                i.putExtra(DetailPmIt.KEY_CUST, customer_id);
                i.putExtra(DetailPmIt.KEY_ACTI, id_activity);
                i.putExtra(DetailPmIt.KEY_SNAME, staff_name);
                i.putExtra(DetailPmIt.KEY_SPHN, staff_phone);
                i.putExtra(DetailPmIt.KEY_INST, instrument_type);
                i.putExtra(DetailPmIt.KEY_INS, instrument);
                i.putExtra(DetailPmIt.KEY_PRIO, priority);
                i.putExtra(DetailPmIt.KEY_NUM, number);
                i.putExtra(DetailPmIt.KEY_CUSTN, customer_name);
                i.putExtra(DetailPmIt.KEY_CONT, contract);
                i.putExtra(DetailPmIt.KEY_DESC, description);
                i.putExtra(DetailPmIt.KEY_CIT, it_category);
                i.putExtra(DetailPmIt.KEY_IDI, id_hardware);
                i.putExtra(DetailPmIt.KEY_IDS, id_software);
                i.putExtra(DetailPmIt.KEY_HAR, code);
                i.putExtra(DetailPmIt.KEY_SOF, version);
                startActivity(i);
            } else if (id_division.equals("3") && category.equals("Visit")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(),
                  DetailOnProgressVisitIT.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOnProgressVisitIT.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressVisitIT.KEY_CAT, category);
              i.putExtra(DetailOnProgressVisitIT.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgressVisitIT.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgressVisitIT.KEY_ACTI, id_activity);
              i.putExtra(DetailOnProgressVisitIT.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgressVisitIT.KEY_SPHN, staff_phone);
              i.putExtra(DetailOnProgressVisitIT.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgressVisitIT.KEY_INS, instrument);
              i.putExtra(DetailOnProgressVisitIT.KEY_PRIO, priority);
              i.putExtra(DetailOnProgressVisitIT.KEY_NUM, number);
              i.putExtra(DetailOnProgressVisitIT.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgressVisitIT.KEY_CONT, contract);
              i.putExtra(DetailOnProgressVisitIT.KEY_DESC, description);
              i.putExtra(DetailOnProgressVisitIT.KEY_CIT, it_category);
              i.putExtra(DetailOnProgressVisitIT.KEY_IDI, id_hardware);
              i.putExtra(DetailOnProgressVisitIT.KEY_IDS, id_software);
              i.putExtra(DetailOnProgressVisitIT.KEY_HAR, code);
              i.putExtra(DetailOnProgressVisitIT.KEY_SOF, version);
              startActivity(i);
            } else if (category.equals("Visit")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(),
                  DetailOnProgresvisitPmOther.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
              startActivity(i);
            } else if (category.equals("PM")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(),
                  DetailOnProgresvisitPmOther.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI, id_activity);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_INS, instrument);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO, priority);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM, number);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT, contract);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC, description);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_CIT, it_category);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_IDI, id_hardware);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_IDS, id_software);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_HAR, code);
              i.putExtra(DetailOnProgresvisitPmOther.KEY_SOF, version);
              startActivity(i);
            } else if (category.equals("Installation")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(), DetailInstrumentForm.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
              i.putExtra(DetailInstrumentForm.KEY_CAT, category);
              i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
              i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
              i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
              i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
              i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
              i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
              i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
              i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
              i.putExtra(DetailInstrumentForm.KEY_NUM, number);
              i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
              i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
              i.putExtra(DetailInstrumentForm.KEY_DESC, description);
              i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
              i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
              i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
              i.putExtra(DetailInstrumentForm.KEY_HAR, code);
              i.putExtra(DetailInstrumentForm.KEY_SOF, version);
              startActivity(i);
            } else if (category.equals("Return")) {
              Intent i = new Intent(MyTiketFragment.this.getContext(), DetailInstrumentForm.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
              i.putExtra(DetailInstrumentForm.KEY_CAT, category);
              i.putExtra(DetailInstrumentForm.KEY_TICK, ticket_type);
              i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
              i.putExtra(DetailInstrumentForm.KEY_ACTI, id_activity);
              i.putExtra(DetailInstrumentForm.KEY_SNAME, staff_name);
              i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
              i.putExtra(DetailInstrumentForm.KEY_INST, instrument_type);
              i.putExtra(DetailInstrumentForm.KEY_INS, instrument);
              i.putExtra(DetailInstrumentForm.KEY_PRIO, priority);
              i.putExtra(DetailInstrumentForm.KEY_NUM, number);
              i.putExtra(DetailInstrumentForm.KEY_CUSTN, customer_name);
              i.putExtra(DetailInstrumentForm.KEY_CONT, contract);
              i.putExtra(DetailInstrumentForm.KEY_DESC, description);
              i.putExtra(DetailInstrumentForm.KEY_CIT, it_category);
              i.putExtra(DetailInstrumentForm.KEY_IDI, id_hardware);
              i.putExtra(DetailInstrumentForm.KEY_IDS, id_software);
              i.putExtra(DetailInstrumentForm.KEY_HAR, code);
              i.putExtra(DetailInstrumentForm.KEY_SOF, version);
              startActivity(i);
            } else {
              Intent i = new Intent(MyTiketFragment.this.getContext(), DetailOnProgressNew.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              String id_hardware = String.valueOf(hardware_id);
              String id_software = String.valueOf(software_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressNew.KEY_CAT, category);
              i.putExtra(DetailOnProgressNew.KEY_TICK, ticket_type);
              i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgressNew.KEY_ACTI, id_activity);
              i.putExtra(DetailOnProgressNew.KEY_SNAME, staff_name);
              i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
              i.putExtra(DetailOnProgressNew.KEY_INST, instrument_type);
              i.putExtra(DetailOnProgressNew.KEY_INS, instrument);
              i.putExtra(DetailOnProgressNew.KEY_PRIO, priority);
              i.putExtra(DetailOnProgressNew.KEY_NUM, number);
              i.putExtra(DetailOnProgressNew.KEY_CUSTN, customer_name);
              i.putExtra(DetailOnProgressNew.KEY_CONT, contract);
              i.putExtra(DetailOnProgressNew.KEY_DESC, description);
              i.putExtra(DetailOnProgressNew.KEY_CIT, it_category);
              i.putExtra(DetailOnProgressNew.KEY_IDI, id_hardware);
              i.putExtra(DetailOnProgressNew.KEY_IDS, id_software);
              i.putExtra(DetailOnProgressNew.KEY_HAR, code);
              i.putExtra(DetailOnProgressNew.KEY_SOF, version);
              startActivity(i);
            }
          }
        });
        rcvTiket.setAdapter(adapterTiketAll);
      }

  public void loadDataTiketconfirm() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketsconfirmed(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(MyTiketFragment.this.getContext(), DetailConfirmedTiket.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
            i.putExtra(DetailConfirmedTiket.KEY_CAT, category);
            i.putExtra(DetailConfirmedTiket.KEY_TICK, ticket_type);
            i.putExtra(DetailConfirmedTiket.KEY_CUST, customer_id);
            i.putExtra(DetailConfirmedTiket.KEY_ACTI, id_activity);
            i.putExtra(DetailConfirmedTiket.KEY_SNAME, staff_name);
            i.putExtra(DetailConfirmedTiket.KEY_SPHN, staff_phone);
            i.putExtra(DetailConfirmedTiket.KEY_INST, instrument_type);
            i.putExtra(DetailConfirmedTiket.KEY_INS, instrument);
            i.putExtra(DetailConfirmedTiket.KEY_PRIO, priority);
            i.putExtra(DetailConfirmedTiket.KEY_NUM, number);
            i.putExtra(DetailConfirmedTiket.KEY_CUSTN, customer_name);
            i.putExtra(DetailConfirmedTiket.KEY_CONT, contract);
            i.putExtra(DetailConfirmedTiket.KEY_DESC, description);
            i.putExtra(DetailConfirmedTiket.KEY_CIT, it_category);
            i.putExtra(DetailConfirmedTiket.KEY_IDI, id_hardware);
            i.putExtra(DetailConfirmedTiket.KEY_IDS, id_software);
            i.putExtra(DetailConfirmedTiket.KEY_HAR, code);
            i.putExtra(DetailConfirmedTiket.KEY_SOF, version);
            startActivity(i);
          }
        });
        rcvTiket.setAdapter(adapterTiketAll);
    Log.e("MyTiketFragment", "loadDataTiketconfirm: cconfirm" );
      }

  private void loadDataTiketOpen() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi.getTiketsnew(accessToken)
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {pDialog.dismiss();
          Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(MyTiketFragment.this.getContext(), DetailOpenTiket.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
            i.putExtra(DetailOpenTiket.KEY_CAT, category);
            i.putExtra(DetailOpenTiket.KEY_TICK, ticket_type);
            i.putExtra(DetailOpenTiket.KEY_CUST, customer_id);
            i.putExtra(DetailOpenTiket.KEY_ACTI, id_activity);
            i.putExtra(DetailOpenTiket.KEY_SNAME, staff_name);
            i.putExtra(DetailOpenTiket.KEY_SPHN, staff_phone);
            i.putExtra(DetailOpenTiket.KEY_INST, instrument_type);
            i.putExtra(DetailOpenTiket.KEY_INS, instrument);
            i.putExtra(DetailOpenTiket.KEY_PRIO, priority);
            i.putExtra(DetailOpenTiket.KEY_NUM, number);
            i.putExtra(DetailOpenTiket.KEY_CUSTN, customer_name);
            i.putExtra(DetailOpenTiket.KEY_CONT, contract);
            i.putExtra(DetailOpenTiket.KEY_DESC, description);
            i.putExtra(DetailOpenTiket.KEY_CIT, it_category);
            i.putExtra(DetailOpenTiket.KEY_IDI, id_hardware);
            i.putExtra(DetailOpenTiket.KEY_IDS, id_software);
            i.putExtra(DetailOpenTiket.KEY_HAR, code);
            i.putExtra(DetailOpenTiket.KEY_SOF, version);
            startActivity(i);
          }
        });
        rcvTiket.setAdapter(adapterTiketAll);
      }

  private void loadDataTicketCancel() {
    pDialog.show();
    Observable<ResponseTikets> respontiket = mApi
        .getTiketscancelled(accessToken)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    respontiket.subscribe(responseTikets -> {
      if (responseTikets.getData() != null) {
        pDialog.dismiss();
        adapterTiketAll.UpdateTikets(responseTikets.getData());
      } else {
        pDialog.dismiss();
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    }, throwable -> {pDialog.dismiss();
      Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(getContext(), DetailEnded.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailEnded.KEY_URI, idtiket);
            i.putExtra(DetailEnded.KEY_CAT, category);
            i.putExtra(DetailEnded.KEY_TICK, ticket_type);
            i.putExtra(DetailEnded.KEY_CUST, customer_id);
            i.putExtra(DetailEnded.KEY_ACTI, id_activity);
            i.putExtra(DetailEnded.KEY_SNAME, staff_name);
            i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
            i.putExtra(DetailEnded.KEY_INST, instrument_type);
            i.putExtra(DetailEnded.KEY_INS, instrument);
            i.putExtra(DetailEnded.KEY_PRIO, priority);
            i.putExtra(DetailEnded.KEY_NUM, number);
            i.putExtra(DetailEnded.KEY_CUSTN, customer_name);
            i.putExtra(DetailEnded.KEY_CONT, contract);
            i.putExtra(DetailEnded.KEY_DESC, description);
            i.putExtra(DetailEnded.KEY_CIT, it_category);
            i.putExtra(DetailEnded.KEY_IDI, id_hardware);
            i.putExtra(DetailEnded.KEY_IDS, id_software);
            i.putExtra(DetailEnded.KEY_HAR, code);
            i.putExtra(DetailEnded.KEY_SOF, version);
            startActivity(i);
          }
        });
    rcvTiket.setAdapter(adapterTiketAll);
  }

  private void loadDataTicketClose() {
    pDialog.show();
    Observable<ResponseTikets> respontiket = mApi
        .getTiketsclosed(accessToken)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    respontiket.subscribe(responseTikets -> {
      if (responseTikets.getData() != null) {
        pDialog.dismiss();
        adapterTiketAll.UpdateTikets(responseTikets.getData());
      } else {
        pDialog.dismiss();
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    }, throwable -> {pDialog.dismiss();
      Utils.showToast(getContext(),"Check your connection and Try Again");});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
        new OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id, String status, String ticket_type,
              int id_customer,
              String category, int activity_id, String staff_name, String staff_phone,
              String instrument_type, String instrument, String priority, String number,
              String customer_name, String contract, String description, String it_category,
              int hardware_id, int software_id, String code, String version,int id_employee) {
            Intent i = new Intent(getContext(), DetailEnded.class);
            String idtiket = String.valueOf(id);
            String customer_id = String.valueOf(id_customer);
            String id_activity = String.valueOf(activity_id);
            String id_hardware = String.valueOf(hardware_id);
            String id_software = String.valueOf(software_id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailEnded.KEY_URI, idtiket);
            i.putExtra(DetailEnded.KEY_CAT, category);
            i.putExtra(DetailEnded.KEY_TICK, ticket_type);
            i.putExtra(DetailEnded.KEY_CUST, customer_id);
            i.putExtra(DetailEnded.KEY_ACTI, id_activity);
            i.putExtra(DetailEnded.KEY_SNAME, staff_name);
            i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
            i.putExtra(DetailEnded.KEY_INST, instrument_type);
            i.putExtra(DetailEnded.KEY_INS, instrument);
            i.putExtra(DetailEnded.KEY_PRIO, priority);
            i.putExtra(DetailEnded.KEY_NUM, number);
            i.putExtra(DetailEnded.KEY_CUSTN, customer_name);
            i.putExtra(DetailEnded.KEY_CONT, contract);
            i.putExtra(DetailEnded.KEY_DESC, description);
            i.putExtra(DetailEnded.KEY_CIT, it_category);
            i.putExtra(DetailEnded.KEY_IDI, id_hardware);
            i.putExtra(DetailEnded.KEY_IDS, id_software);
            i.putExtra(DetailEnded.KEY_HAR, code);
            i.putExtra(DetailEnded.KEY_SOF, version);
            startActivity(i);
          }
        });
    rcvTiket.setAdapter(adapterTiketAll);
  }

  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTiket.setHasFixedSize(true);
        rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

  private void onRefresh() {
      if (key.equals("open")) {
        loadDataTiketOpen();
      } else if (key.equals("confirm")) {
        loadDataTiketconfirm();
      } else if (key.equals("progres new")) {
        loadDataTiketonprogress();
      } else if (key.equals("hold")) {
        loadDataTiketonhold();
      } else if (key.equals("ended")) {
        loadDataTiketended();
      } else if (key.equals("progres hold")) {
        loadDataTiketonprogresshold();
      } else if (key.equals("all")) {
        loadDataTiketall();
      } else if (key.equals("dialihkan_staff")){
        loadDataTicketScouting();
      } else if (key.equals("close")){
        loadDataTicketClose();
      } else if (key.equals("cancel")){
        loadDataTicketCancel();
      }
  }
}
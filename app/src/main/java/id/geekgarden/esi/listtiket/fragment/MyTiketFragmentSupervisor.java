package id.geekgarden.esi.listtiket.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.paginate.Paginate;
import com.paginate.Paginate.Callbacks;
import com.paginate.recycler.LoadingListItemSpanLookup;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketAll;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketAll.OnTiketPostItemListener;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailEnded;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailInstrumentForm;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressHold;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressInstallAnalyzer;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressInstallHclab;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressVisitIT;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailPmIt;
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
import id.geekgarden.esi.data.model.tikets.supervisorticket.adapter.AdapterTiketAllSpv;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailEndedSpv;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnHold;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressNew;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOpenTiket;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailHoldTiketSpv;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailOpenTiketSpv;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MyTiketFragmentSupervisor extends Fragment {
  private static final String KEY = "key";
  @BindView(R.id.etSearch)
  EditText etSearch;
  String id_ticket;
  private Api mApi;
  private Unbinder unbinder;
  private AdapterTiketAllSpv adapterTiketAllSpv;
  private GlobalPreferences glpref;
  private String accessToken;
  private String key;
  private String id_division;
  private static ProgressDialog pDialog;
  private String supervisor;
  private static final int GRID_SPAN = 3;
  private boolean loading = false;
  private int page = 0;
  private Handler handler;
  private Paginate paginate;
  private AdapterSearchTiket adapterSearchTiket;

  public MyTiketFragmentSupervisor() {
  }
  @BindView(R.id.rcvTiket)
  RecyclerView rcvTiket;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      key = getArguments().getString(KEY);
      Log.e("onCreate", "MyTiketFragment" + key);
    }
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
        supervisor = glpref.read(PrefKey.position_name,String.class);
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        id_division = glpref.read(PrefKey.division_id,String.class);
        handler = new Handler();
        Log.e("onCreate", "MyTiketFragment" + key);
        return v;
    }

  @Override
  public void onResume() {
    super.onResume();
    onRefresh();
  }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void setEtSearch (CharSequence q){
        if (q.length()>=2){
            String name = q.toString();
            queryearchTiket(name);
        }if (q.length()==0){
            loaddataspvall();
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
                      Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailOpenTiketSpv.KEY_URI, idtiket);
                      i.putExtra(DetailOpenTiketSpv.KEY_CAT, category);
                      i.putExtra(DetailOpenTiketSpv.KEY_TICK, ticket_type);
                      i.putExtra(DetailOpenTiketSpv.KEY_CUST, customer_id);
                      i.putExtra(DetailOpenTiketSpv.KEY_ACTI, id_activity);
                      i.putExtra(DetailOpenTiketSpv.KEY_SNAME, staff_name);
                      i.putExtra(DetailOpenTiketSpv.KEY_SPHN, staff_phone);
                      i.putExtra(DetailOpenTiketSpv.KEY_INST, instrument_type);
                      i.putExtra(DetailOpenTiketSpv.KEY_INS, instrument);
                      i.putExtra(DetailOpenTiketSpv.KEY_PRIO, priority);
                      i.putExtra(DetailOpenTiketSpv.KEY_NUM, number);
                      i.putExtra(DetailOpenTiketSpv.KEY_CUSTN, customer_name);
                      i.putExtra(DetailOpenTiketSpv.KEY_CONT, contract);
                      i.putExtra(DetailOpenTiketSpv.KEY_DESC, description);
                      i.putExtra(DetailOpenTiketSpv.KEY_CIT, it_category);
                      i.putExtra(DetailOpenTiketSpv.KEY_IDI, id_hardware);
                      i.putExtra(DetailOpenTiketSpv.KEY_IDS, id_software);
                      i.putExtra(DetailOpenTiketSpv.KEY_HAR, code);
                      i.putExtra(DetailOpenTiketSpv.KEY_SOF, version);
                      startActivity(i);
                    } else if (status.equals("confirmed")) {
                    } else if (status.equals("started")) {
                      if (id_division.equals("3") && category.equals("Installation")) {
                        if (it_category.equals("Hardware")) {
                        } else if (it_category.equals("Software")) {
                        }
                      } else if (id_division.equals("3") && category.equals("PM")) {
                      } else if (id_division.equals("3") && category.equals("Visit")) {
                      } else if (category.equals("Visit")) {
                      } else if (category.equals("PM")) {
                      } else if (category.equals("Installation")) {
                      } else if (category.equals("Return")) {
                      } else {
                      }} else if (status.equals("held")) {
                      Intent i = new Intent(getContext(), DetailHoldTiketSpv.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailHoldTiketSpv.KEY_URI, idtiket);
                      i.putExtra(DetailHoldTiketSpv.KEY_CAT, category);
                      i.putExtra(DetailHoldTiketSpv.KEY_TICK, ticket_type);
                      i.putExtra(DetailHoldTiketSpv.KEY_CUST, customer_id);
                      i.putExtra(DetailHoldTiketSpv.KEY_ACTI, id_activity);
                      i.putExtra(DetailHoldTiketSpv.KEY_SNAME, staff_name);
                      i.putExtra(DetailHoldTiketSpv.KEY_SPHN, staff_phone);
                      i.putExtra(DetailHoldTiketSpv.KEY_INST, instrument_type);
                      i.putExtra(DetailHoldTiketSpv.KEY_INS, instrument);
                      i.putExtra(DetailHoldTiketSpv.KEY_PRIO, priority);
                      i.putExtra(DetailHoldTiketSpv.KEY_NUM, number);
                      i.putExtra(DetailHoldTiketSpv.KEY_CUSTN, customer_name);
                      i.putExtra(DetailHoldTiketSpv.KEY_CONT, contract);
                      i.putExtra(DetailHoldTiketSpv.KEY_DESC, description);
                      i.putExtra(DetailHoldTiketSpv.KEY_CIT, it_category);
                      i.putExtra(DetailHoldTiketSpv.KEY_IDI, id_hardware);
                      i.putExtra(DetailHoldTiketSpv.KEY_IDS, id_software);
                      i.putExtra(DetailHoldTiketSpv.KEY_HAR, code);
                      i.putExtra(DetailHoldTiketSpv.KEY_SOF, version);
                      startActivity(i);
                    } else if (status.equals("restarted")) {
                    } else if (status.equals("done")) {
                      Intent i = new Intent(getContext(), DetailEndedSpv.class);
                      String idtiket = String.valueOf(id);
                      String customer_id = String.valueOf(id_customer);
                      String id_activity = String.valueOf(activity_id);
                      String id_hardware = String.valueOf(hardware_id);
                      String id_software = String.valueOf(software_id);
                      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      i.putExtra(DetailEndedSpv.KEY_URI, idtiket);
                      i.putExtra(DetailEndedSpv.KEY_CAT, category);
                      i.putExtra(DetailEndedSpv.KEY_TICK, ticket_type);
                      i.putExtra(DetailEndedSpv.KEY_CUST, customer_id);
                      i.putExtra(DetailEndedSpv.KEY_ACTI, id_activity);
                      i.putExtra(DetailEndedSpv.KEY_SNAME, staff_name);
                      i.putExtra(DetailEndedSpv.KEY_SPHN, staff_phone);
                      i.putExtra(DetailEndedSpv.KEY_INST, instrument_type);
                      i.putExtra(DetailEndedSpv.KEY_INS, instrument);
                      i.putExtra(DetailEndedSpv.KEY_PRIO, priority);
                      i.putExtra(DetailEndedSpv.KEY_NUM, number);
                      i.putExtra(DetailEndedSpv.KEY_CUSTN, customer_name);
                      i.putExtra(DetailEndedSpv.KEY_CONT, contract);
                      i.putExtra(DetailEndedSpv.KEY_DESC, description);
                      i.putExtra(DetailEndedSpv.KEY_CIT, it_category);
                      i.putExtra(DetailEndedSpv.KEY_IDI, id_hardware);
                      i.putExtra(DetailEndedSpv.KEY_IDS, id_software);
                      i.putExtra(DetailEndedSpv.KEY_HAR, code);
                      i.putExtra(DetailEndedSpv.KEY_SOF, version);
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

    private void loaddataspvonprogresshold() {
        pDialog.show();
        Observable<ResponseTikets> getticketonprogressholdsspv = mApi
                .getticketonprogressholdspvcust(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        getticketonprogressholdsspv.subscribe(responseTikets -> {
            pDialog.dismiss();
            if (responseTikets.getData() != null) {
                adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
            } else {
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }

        },throwable -> {});
        adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
                (int id, String status, String ticket_type,
                    int id_customer,
                    String category, int activity_id, String staff_name, String staff_phone,
                    String instrument_type, String instrument, String priority, String number,
                    String customer_name, String contract, String description, String it_category,
                    int hardware_id, int software_id, String code, String version,int id_employee) -> {});
        rcvTiket.setAdapter(adapterTiketAllSpv);
    }


  private void loaddataspvended() {
    pDialog.show();
    Observable<ResponseTikets> getticketendedsspv = mApi
        .getticketendedspvcust(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getticketendedsspv.subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
          Intent i = new Intent(getContext(), DetailEndedSpv.class);
          String idtiket = String.valueOf(id);
          String customer_id = String.valueOf(id_customer);
          String id_activity = String.valueOf(activity_id);
          String id_hardware = String.valueOf(hardware_id);
          String id_software = String.valueOf(software_id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailEndedSpv.KEY_URI, idtiket);
          i.putExtra(DetailEndedSpv.KEY_CAT, category);
          i.putExtra(DetailEndedSpv.KEY_TICK, ticket_type);
          i.putExtra(DetailEndedSpv.KEY_CUST, customer_id);
          i.putExtra(DetailEndedSpv.KEY_ACTI, id_activity);
          i.putExtra(DetailEndedSpv.KEY_SNAME, staff_name);
          i.putExtra(DetailEndedSpv.KEY_SPHN, staff_phone);
          i.putExtra(DetailEndedSpv.KEY_INST, instrument_type);
          i.putExtra(DetailEndedSpv.KEY_INS, instrument);
          i.putExtra(DetailEndedSpv.KEY_PRIO, priority);
          i.putExtra(DetailEndedSpv.KEY_NUM, number);
          i.putExtra(DetailEndedSpv.KEY_CUSTN, customer_name);
          i.putExtra(DetailEndedSpv.KEY_CONT, contract);
          i.putExtra(DetailEndedSpv.KEY_DESC, description);
          i.putExtra(DetailEndedSpv.KEY_CIT, it_category);
          i.putExtra(DetailEndedSpv.KEY_IDI, id_hardware);
          i.putExtra(DetailEndedSpv.KEY_IDS, id_software);
          i.putExtra(DetailEndedSpv.KEY_HAR, code);
          i.putExtra(DetailEndedSpv.KEY_SOF, version);
          startActivity(i);
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void loaddataspvonhold() {
    pDialog.show();
    Observable<ResponseTikets> getticketonholdsspv = mApi
        .getticketonholdspvcust(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getticketonholdsspv.subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
          Intent i = new Intent(getContext(), DetailHoldTiketSpv.class);
          String idtiket = String.valueOf(id);
          String customer_id = String.valueOf(id_customer);
          String id_activity = String.valueOf(activity_id);
          String id_hardware = String.valueOf(hardware_id);
          String id_software = String.valueOf(software_id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailHoldTiketSpv.KEY_URI, idtiket);
          i.putExtra(DetailHoldTiketSpv.KEY_CAT, category);
          i.putExtra(DetailHoldTiketSpv.KEY_TICK, ticket_type);
          i.putExtra(DetailHoldTiketSpv.KEY_CUST, customer_id);
          i.putExtra(DetailHoldTiketSpv.KEY_ACTI, id_activity);
          i.putExtra(DetailHoldTiketSpv.KEY_SNAME, staff_name);
          i.putExtra(DetailHoldTiketSpv.KEY_SPHN, staff_phone);
          i.putExtra(DetailHoldTiketSpv.KEY_INST, instrument_type);
          i.putExtra(DetailHoldTiketSpv.KEY_INS, instrument);
          i.putExtra(DetailHoldTiketSpv.KEY_PRIO, priority);
          i.putExtra(DetailHoldTiketSpv.KEY_NUM, number);
          i.putExtra(DetailHoldTiketSpv.KEY_CUSTN, customer_name);
          i.putExtra(DetailHoldTiketSpv.KEY_CONT, contract);
          i.putExtra(DetailHoldTiketSpv.KEY_DESC, description);
          i.putExtra(DetailHoldTiketSpv.KEY_CIT, it_category);
          i.putExtra(DetailHoldTiketSpv.KEY_IDI, id_hardware);
          i.putExtra(DetailHoldTiketSpv.KEY_IDS, id_software);
          i.putExtra(DetailHoldTiketSpv.KEY_HAR, code);
          i.putExtra(DetailHoldTiketSpv.KEY_SOF, version);
          startActivity(i);
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void loaddataspvonprogress() {
    pDialog.show();
    Observable<ResponseTikets> getticketonprogressspv = mApi
        .getticketonprogressspvcust(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getticketonprogressspv.subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {});
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void loaddataspvopen() {
    pDialog.show();
    Observable<ResponseTikets> getticketopenspv = mApi
        .getticketopenspvcust(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getticketopenspv.subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
          Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
          String idtiket = String.valueOf(id);
          String customer_id = String.valueOf(id_customer);
          String id_activity = String.valueOf(activity_id);
          String id_hardware = String.valueOf(hardware_id);
          String id_software = String.valueOf(software_id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailOpenTiketSpv.KEY_URI, idtiket);
          i.putExtra(DetailOpenTiketSpv.KEY_CAT, category);
          i.putExtra(DetailOpenTiketSpv.KEY_TICK, ticket_type);
          i.putExtra(DetailOpenTiketSpv.KEY_CUST, customer_id);
          i.putExtra(DetailOpenTiketSpv.KEY_ACTI, id_activity);
          i.putExtra(DetailOpenTiketSpv.KEY_SNAME, staff_name);
          i.putExtra(DetailOpenTiketSpv.KEY_SPHN, staff_phone);
          i.putExtra(DetailOpenTiketSpv.KEY_INST, instrument_type);
          i.putExtra(DetailOpenTiketSpv.KEY_INS, instrument);
          i.putExtra(DetailOpenTiketSpv.KEY_PRIO, priority);
          i.putExtra(DetailOpenTiketSpv.KEY_NUM, number);
          i.putExtra(DetailOpenTiketSpv.KEY_CUSTN, customer_name);
          i.putExtra(DetailOpenTiketSpv.KEY_CONT, contract);
          i.putExtra(DetailOpenTiketSpv.KEY_DESC, description);
          i.putExtra(DetailOpenTiketSpv.KEY_CIT, it_category);
          i.putExtra(DetailOpenTiketSpv.KEY_IDI, id_hardware);
          i.putExtra(DetailOpenTiketSpv.KEY_IDS, id_software);
          i.putExtra(DetailOpenTiketSpv.KEY_HAR, code);
          i.putExtra(DetailOpenTiketSpv.KEY_SOF, version);
          startActivity(i);
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void loaddataspvconfirm() {
    pDialog.show();
   Observable<ResponseTikets> getticketconfirmspvcust= mApi
      .getticketconfirmspvcust(accessToken)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
   getticketconfirmspvcust.subscribe(responseTikets -> {
     pDialog.dismiss();
     if (responseTikets.getData() != null) {
       adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
     } else {
       Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
     }
   }, throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {});
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }
  
  private void loaddataspvall() {
    pDialog.show();
    Observable<ResponseTikets> getticketallspvcust = mApi
        .getticketallspvcust(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getticketallspvcust.subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
      adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
              (int id, String status, String ticket_type,
                  int id_customer,
                  String category, int activity_id, String staff_name, String staff_phone,
                  String instrument_type, String instrument, String priority, String number,
                  String customer_name, String contract, String description, String it_category,
                  int hardware_id, int software_id, String code, String version,int id_employee) -> {
                  /*if (status != null) {
                      if (status.equals("new")) {
                          Intent i = new Intent(getContext(), DetailOpenTiket.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
                          startActivity(i);
                      } else if (status.equals("confirmed")) {
                          Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
                          startActivity(i);
                      } else if (status.equals("started")) {
                      } else if (status.equals("held")) {
                          Intent i = new Intent(getContext(), DetailOnHold.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailOnHold.KEY_URI, idtiket);
                          startActivity(i);
                      } else if (status.equals("restarted")) {
                      } else if (status.equals("done")) {
                          Intent i = new Intent(getContext(), DetailEndedSpv.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailEndedSpv.KEY_URI, idtiket);
                          startActivity(i);
                      } else {
                          glpref.read(PrefKey.statustiket, String.class);
                      }
                  }*/
              });
      rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void getcomplain() {
    pDialog.show();
    mApi.getTiketscomplain(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseTikets -> {
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void getcancel() {
    pDialog.show();
    mApi.getSpvTiketscancelled(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseTikets -> {
          pDialog.dismiss();
          if (responseTikets.getData() != null) {
            adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
          } else {
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
          Intent i = new Intent(getContext(), DetailEndedSpv.class);
          String idtiket = String.valueOf(id);
          String customer_id = String.valueOf(id_customer);
          String id_activity = String.valueOf(activity_id);
          String id_hardware = String.valueOf(hardware_id);
          String id_software = String.valueOf(software_id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailEndedSpv.KEY_URI, idtiket);
          i.putExtra(DetailEndedSpv.KEY_CAT, category);
          i.putExtra(DetailEndedSpv.KEY_TICK, ticket_type);
          i.putExtra(DetailEndedSpv.KEY_CUST, customer_id);
          i.putExtra(DetailEndedSpv.KEY_ACTI, id_activity);
          i.putExtra(DetailEndedSpv.KEY_SNAME, staff_name);
          i.putExtra(DetailEndedSpv.KEY_SPHN, staff_phone);
          i.putExtra(DetailEndedSpv.KEY_INST, instrument_type);
          i.putExtra(DetailEndedSpv.KEY_INS, instrument);
          i.putExtra(DetailEndedSpv.KEY_PRIO, priority);
          i.putExtra(DetailEndedSpv.KEY_NUM, number);
          i.putExtra(DetailEndedSpv.KEY_CUSTN, customer_name);
          i.putExtra(DetailEndedSpv.KEY_CONT, contract);
          i.putExtra(DetailEndedSpv.KEY_DESC, description);
          i.putExtra(DetailEndedSpv.KEY_CIT, it_category);
          i.putExtra(DetailEndedSpv.KEY_IDI, id_hardware);
          i.putExtra(DetailEndedSpv.KEY_IDS, id_software);
          i.putExtra(DetailEndedSpv.KEY_HAR, code);
          i.putExtra(DetailEndedSpv.KEY_SOF, version);
          startActivity(i);
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  private void getclosed() {
    pDialog.show();
    mApi.getSpvTiketsclose(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseTikets -> {
          pDialog.dismiss();
          if (responseTikets.getData() != null) {
            adapterTiketAllSpv.UpdateTikets(responseTikets.getData());
          } else {
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        },throwable -> {});
    adapterTiketAllSpv = new AdapterTiketAllSpv(new ArrayList<Datum>(0), getContext(),
        (int id, String status, String ticket_type,
            int id_customer,
            String category, int activity_id, String staff_name, String staff_phone,
            String instrument_type, String instrument, String priority, String number,
            String customer_name, String contract, String description, String it_category,
            int hardware_id, int software_id, String code, String version,int id_employee) -> {
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
        });
    rcvTiket.setAdapter(adapterTiketAllSpv);
  }

  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTiket.setHasFixedSize(true);
        rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));

    }

  private void onRefresh() {
    if (key.equals("open")) {
      loaddataspvopen();
    } else if (key.equals("confirm")) {
      loaddataspvconfirm();
    } else if (key.equals("progres new")) {
      loaddataspvonprogress();
    } else if (key.equals("hold")) {
      loaddataspvonhold();
    } else if (key.equals("ended")) {
      loaddataspvended();
    } else if (key.equals("progres hold")) {
      loaddataspvonprogresshold();
    } else if (key.equals("all")) {
      loaddataspvall();
    } else if (key.equals("complain")){
      getcomplain();
    } else if (key.equals("close")) {
      getclosed();
    } else if (key.equals("cancel")) {
      getcancel();
    }
  }

  @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
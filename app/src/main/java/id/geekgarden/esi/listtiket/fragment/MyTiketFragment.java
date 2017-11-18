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
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketSwitch;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailInstrumentForm;
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
        return v;
    }

  @Override
  public void onResume() {
    super.onResume();
    onRefresh();
  }

  private void loadDataTicketScouting() {
    pDialog.show();
    Observable<ResponseTikets> responseTikets = mApi
        .getTiketswitch(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    responseTikets.subscribe(responseTikets1 -> {
      pDialog.dismiss();
      if (responseTikets1.getData() != null) {
        adapterTiketSwitch.UpdateTikets(responseTikets1.getData());
      } else {
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketSwitch = new AdapterTiketSwitch(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {});
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
        adapterSearchTiket = new AdapterSearchTiket(new ArrayList<id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket.Datum>(), getContext(),
            (id, status,id_customer,ticket_type,category) -> {
              if (status != null) {
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
                  if (category.equals("Visit")) {
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String ID_customer = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                    startActivity(i);
                  } else
                  if (category.equals("PM")) {
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String ID_customer = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                    startActivity(i);
                  } else
                  if (category.equals("Installation")) {
                    Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                    i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                    startActivity(i);
                  } else {
                    Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                    String idtiket = String.valueOf(id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                    startActivity(i);
                  }
                } else if (status.equals("held")) {
                  Intent i = new Intent(getContext(), DetailOnHold.class);
                  String idtiket = String.valueOf(id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnHold.KEY_URI, idtiket);
                  startActivity(i);
                } else if (status.equals("restarted")) {
                    Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                    String idtiket = String.valueOf(id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                    startActivity(i);
                } else if (status.equals("done")) {
                  Intent i = new Intent(getContext(), DetailEnded.class);
                  String idtiket = String.valueOf(id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailEnded.KEY_URI, idtiket);
                  startActivity(i);
                }
              } else {
                glpref.read(PrefKey.statustiket, String.class);
              }});
      Observable<ResponseSearchTiket> responseSearchTiket = mApi
          .searchtiket(accessToken,name)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread());
      responseSearchTiket.subscribe(responseSearchTiket1 -> {
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
        }, throwable -> {});
        adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
              if (status != null) {
                if (status.equals("new")) {
                  Intent i = new Intent(getContext(), DetailOpenTiket.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
                  i.putExtra(DetailOpenTiket.KEY_CAT,category);
                  i.putExtra(DetailOpenTiket.KEY_TICK,ticket_type);
                  i.putExtra(DetailOpenTiket.KEY_CUST, customer_id);
                  i.putExtra(DetailOpenTiket.KEY_ACTI,id_activity);
                  i.putExtra(DetailOpenTiket.KEY_SNAME,staff_name);
                  i.putExtra(DetailOpenTiket.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOpenTiket.KEY_INST,instrument_type);
                  i.putExtra(DetailOpenTiket.KEY_INS,instrument);
                  i.putExtra(DetailOpenTiket.KEY_PRIO,priority);
                  i.putExtra(DetailOpenTiket.KEY_NUM,number);
                  i.putExtra(DetailOpenTiket.KEY_CUSTN,customer_name);
                  i.putExtra(DetailOpenTiket.KEY_CONT,contract);
                  i.putExtra(DetailOpenTiket.KEY_DESC,description);
                  startActivity(i);
                } else if (status.equals("confirmed")) {
                  Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
                  i.putExtra(DetailConfirmedTiket.KEY_CAT,category);
                  i.putExtra(DetailConfirmedTiket.KEY_TICK,ticket_type);
                  i.putExtra(DetailConfirmedTiket.KEY_CUST, customer_id);
                  i.putExtra(DetailConfirmedTiket.KEY_ACTI,id_activity);
                  i.putExtra(DetailConfirmedTiket.KEY_SNAME,staff_name);
                  i.putExtra(DetailConfirmedTiket.KEY_SPHN, staff_phone);
                  i.putExtra(DetailConfirmedTiket.KEY_INST,instrument_type);
                  i.putExtra(DetailConfirmedTiket.KEY_INS,instrument);
                  i.putExtra(DetailConfirmedTiket.KEY_PRIO,priority);
                  i.putExtra(DetailConfirmedTiket.KEY_NUM,number);
                  i.putExtra(DetailConfirmedTiket.KEY_CUSTN,customer_name);
                  i.putExtra(DetailConfirmedTiket.KEY_CONT,contract);
                  i.putExtra(DetailConfirmedTiket.KEY_DESC,description);
                  startActivity(i);
                } else if (status.equals("started")) {
                  if (category.equals("Visit")) {
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,id_activity);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
                    startActivity(i);
                  } else if (category.equals("PM")) {
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,id_activity);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
                    startActivity(i);
                  } else if (category.equals("Installation")) {
                    Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                    i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                    startActivity(i);
                  } else {
                    Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    String id_activity = String.valueOf(activity_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgressNew.KEY_CAT,category);
                    i.putExtra(DetailOnProgressNew.KEY_TICK,ticket_type);
                    i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
                    i.putExtra(DetailOnProgressNew.KEY_ACTI,id_activity);
                    i.putExtra(DetailOnProgressNew.KEY_SNAME,staff_name);
                    i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
                    i.putExtra(DetailOnProgressNew.KEY_INST,instrument_type);
                    i.putExtra(DetailOnProgressNew.KEY_INS,instrument);
                    i.putExtra(DetailOnProgressNew.KEY_PRIO,priority);
                    i.putExtra(DetailOnProgressNew.KEY_NUM,number);
                    i.putExtra(DetailOnProgressNew.KEY_CUSTN,customer_name);
                    i.putExtra(DetailOnProgressNew.KEY_CONT,contract);
                    i.putExtra(DetailOnProgressNew.KEY_DESC,description);
                    startActivity(i);
                  }
                } else if (status.equals("held")) {
                  Intent i = new Intent(getContext(), DetailOnHold.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnHold.KEY_URI, idtiket);
                  i.putExtra(DetailOnHold.KEY_CAT,category);
                  i.putExtra(DetailOnHold.KEY_TICK,ticket_type);
                  i.putExtra(DetailOnHold.KEY_CUST, customer_id);
                  i.putExtra(DetailOnHold.KEY_ACTI,id_activity);
                  i.putExtra(DetailOnHold.KEY_SNAME,staff_name);
                  i.putExtra(DetailOnHold.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnHold.KEY_INST,instrument_type);
                  i.putExtra(DetailOnHold.KEY_INS,instrument);
                  i.putExtra(DetailOnHold.KEY_PRIO,priority);
                  i.putExtra(DetailOnHold.KEY_NUM,number);
                  i.putExtra(DetailOnHold.KEY_CUSTN,customer_name);
                  i.putExtra(DetailOnHold.KEY_CONT,contract);
                  i.putExtra(DetailOnHold.KEY_DESC,description);
                  startActivity(i);
                } else if (status.equals("restarted")) {
                  Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                  i.putExtra(DetailOnProgressNew.KEY_CAT,category);
                  i.putExtra(DetailOnProgressNew.KEY_TICK,ticket_type);
                  i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
                  i.putExtra(DetailOnProgressNew.KEY_ACTI,id_activity);
                  i.putExtra(DetailOnProgressNew.KEY_SNAME,staff_name);
                  i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
                  i.putExtra(DetailOnProgressNew.KEY_INST,instrument_type);
                  i.putExtra(DetailOnProgressNew.KEY_INS,instrument);
                  i.putExtra(DetailOnProgressNew.KEY_PRIO,priority);
                  i.putExtra(DetailOnProgressNew.KEY_NUM,number);
                  i.putExtra(DetailOnProgressNew.KEY_CUSTN,customer_name);
                  i.putExtra(DetailOnProgressNew.KEY_CONT,contract);
                  i.putExtra(DetailOnProgressNew.KEY_DESC,description);
                  startActivity(i);
                } else if (status.equals("done")) {
                  Intent i = new Intent(getContext(), DetailEnded.class);
                  String idtiket = String.valueOf(id);
                  String customer_id = String.valueOf(id_customer);
                  String id_activity = String.valueOf(activity_id);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.putExtra(DetailEnded.KEY_URI, idtiket);
                  i.putExtra(DetailEnded.KEY_CAT,category);
                  i.putExtra(DetailEnded.KEY_TICK,ticket_type);
                  i.putExtra(DetailEnded.KEY_CUST, customer_id);
                  i.putExtra(DetailEnded.KEY_ACTI,id_activity);
                  i.putExtra(DetailEnded.KEY_SNAME,staff_name);
                  i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
                  i.putExtra(DetailEnded.KEY_INST,instrument_type);
                  i.putExtra(DetailEnded.KEY_INS,instrument);
                  i.putExtra(DetailEnded.KEY_PRIO,priority);
                  i.putExtra(DetailEnded.KEY_NUM,number);
                  i.putExtra(DetailEnded.KEY_CUSTN,customer_name);
                  i.putExtra(DetailEnded.KEY_CONT,contract);
                  i.putExtra(DetailEnded.KEY_DESC,description);
                  startActivity(i);
                } else {
                  glpref.read(PrefKey.statustiket, String.class);
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
        }, throwable -> {
        });
      adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0),
            getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument, String priority, String number, String customer_name, String contract, String description) -> {
              Intent i = new Intent(getContext(), DetailOnProgressHold.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOnProgressHold.KEY_URI, idtiket);
              i.putExtra(DetailOnProgressHold.KEY_CAT,category);
              i.putExtra(DetailOnProgressHold.KEY_TICK,ticket_type);
              i.putExtra(DetailOnProgressHold.KEY_CUST, customer_id);
              i.putExtra(DetailOnProgressHold.KEY_ACTI,id_activity);
              i.putExtra(DetailOnProgressHold.KEY_SNAME,staff_name);
              i.putExtra(DetailOnProgressHold.KEY_SPHN, staff_phone);
              i.putExtra(DetailOnProgressHold.KEY_INST,instrument_type);
              i.putExtra(DetailOnProgressHold.KEY_INS,instrument);
              i.putExtra(DetailOnProgressHold.KEY_PRIO,priority);
              i.putExtra(DetailOnProgressHold.KEY_NUM,number);
              i.putExtra(DetailOnProgressHold.KEY_CUSTN,customer_name);
              i.putExtra(DetailOnProgressHold.KEY_CONT,contract);
              i.putExtra(DetailOnProgressHold.KEY_DESC,description);
                startActivity(i);
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
        }, throwable -> {});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
              Intent i = new Intent(getContext(), DetailEnded.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailEnded.KEY_URI, idtiket);
              i.putExtra(DetailEnded.KEY_CAT,category);
              i.putExtra(DetailEnded.KEY_TICK,ticket_type);
              i.putExtra(DetailEnded.KEY_CUST, customer_id);
              i.putExtra(DetailEnded.KEY_ACTI,id_activity);
              i.putExtra(DetailEnded.KEY_SNAME,staff_name);
              i.putExtra(DetailEnded.KEY_SPHN, staff_phone);
              i.putExtra(DetailEnded.KEY_INST,instrument_type);
              i.putExtra(DetailEnded.KEY_INS,instrument);
              i.putExtra(DetailEnded.KEY_PRIO,priority);
              i.putExtra(DetailEnded.KEY_NUM,number);
              i.putExtra(DetailEnded.KEY_CUSTN,customer_name);
              i.putExtra(DetailEnded.KEY_CONT,contract);
              i.putExtra(DetailEnded.KEY_DESC,description);
              startActivity(i);
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
        }, throwable -> {});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
      Intent i = new Intent(getContext(), DetailOnHold.class);
      String idtiket = String.valueOf(id);
      String customer_id = String.valueOf(id_customer);
      String id_activity = String.valueOf(activity_id);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.putExtra(DetailOnHold.KEY_URI, idtiket);
      i.putExtra(DetailOnHold.KEY_CAT,category);
      i.putExtra(DetailOnHold.KEY_TICK,ticket_type);
      i.putExtra(DetailOnHold.KEY_CUST, customer_id);
      i.putExtra(DetailOnHold.KEY_ACTI,id_activity);
      i.putExtra(DetailOnHold.KEY_SNAME,staff_name);
      i.putExtra(DetailOnHold.KEY_SPHN, staff_phone);
      i.putExtra(DetailOnHold.KEY_INST,instrument_type);
      i.putExtra(DetailOnHold.KEY_INS,instrument);
      i.putExtra(DetailOnHold.KEY_PRIO,priority);
      i.putExtra(DetailOnHold.KEY_NUM,number);
      i.putExtra(DetailOnHold.KEY_CUSTN,customer_name);
      i.putExtra(DetailOnHold.KEY_CONT,contract);
      i.putExtra(DetailOnHold.KEY_DESC,description);
      startActivity(i);
    });
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
        }, throwable -> {});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0),
            getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
              Log.e("loadDataTiketonprogress", "MyTiketFragment" + category);
              if (category.equals("Visit")) {
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,id_activity);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
                startActivity(i);
              } else
              if (category.equals("PM")) {
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT,category);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_TICK,ticket_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_ACTI,id_activity);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SNAME,staff_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INST,instrument_type);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_INS,instrument);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_PRIO,priority);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_NUM,number);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUSTN,customer_name);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CONT,contract);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_DESC,description);
                startActivity(i);
              } else
              if (category.equals("Installation")) {
                Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                i.putExtra(DetailInstrumentForm.KEY_CAT,category);
                i.putExtra(DetailInstrumentForm.KEY_TICK,ticket_type);
                i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                i.putExtra(DetailInstrumentForm.KEY_ACTI,id_activity);
                i.putExtra(DetailInstrumentForm.KEY_SNAME,staff_name);
                i.putExtra(DetailInstrumentForm.KEY_SPHN, staff_phone);
                i.putExtra(DetailInstrumentForm.KEY_INST,instrument_type);
                i.putExtra(DetailInstrumentForm.KEY_INS,instrument);
                i.putExtra(DetailInstrumentForm.KEY_PRIO,priority);
                i.putExtra(DetailInstrumentForm.KEY_NUM,number);
                i.putExtra(DetailInstrumentForm.KEY_CUSTN,customer_name);
                i.putExtra(DetailInstrumentForm.KEY_CONT,contract);
                i.putExtra(DetailInstrumentForm.KEY_DESC,description);
                startActivity(i);
              } else {
                Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                String id_activity = String.valueOf(activity_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                i.putExtra(DetailOnProgressNew.KEY_CAT,category);
                i.putExtra(DetailOnProgressNew.KEY_TICK,ticket_type);
                i.putExtra(DetailOnProgressNew.KEY_CUST, customer_id);
                i.putExtra(DetailOnProgressNew.KEY_ACTI,id_activity);
                i.putExtra(DetailOnProgressNew.KEY_SNAME,staff_name);
                i.putExtra(DetailOnProgressNew.KEY_SPHN, staff_phone);
                i.putExtra(DetailOnProgressNew.KEY_INST,instrument_type);
                i.putExtra(DetailOnProgressNew.KEY_INS,instrument);
                i.putExtra(DetailOnProgressNew.KEY_PRIO,priority);
                i.putExtra(DetailOnProgressNew.KEY_NUM,number);
                i.putExtra(DetailOnProgressNew.KEY_CUSTN,customer_name);
                i.putExtra(DetailOnProgressNew.KEY_CONT,contract);
                i.putExtra(DetailOnProgressNew.KEY_DESC,description);
                startActivity(i);
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
        }, throwable -> {});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
              Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
              i.putExtra(DetailConfirmedTiket.KEY_CAT,category);
              i.putExtra(DetailConfirmedTiket.KEY_TICK,ticket_type);
              i.putExtra(DetailConfirmedTiket.KEY_CUST, customer_id);
              i.putExtra(DetailConfirmedTiket.KEY_ACTI,id_activity);
              i.putExtra(DetailConfirmedTiket.KEY_SNAME,staff_name);
              i.putExtra(DetailConfirmedTiket.KEY_SPHN, staff_phone);
              i.putExtra(DetailConfirmedTiket.KEY_INST,instrument_type);
              i.putExtra(DetailConfirmedTiket.KEY_INS,instrument);
              i.putExtra(DetailConfirmedTiket.KEY_PRIO,priority);
              i.putExtra(DetailConfirmedTiket.KEY_NUM,number);
              i.putExtra(DetailConfirmedTiket.KEY_CUSTN,customer_name);
              i.putExtra(DetailConfirmedTiket.KEY_CONT,contract);
              i.putExtra(DetailConfirmedTiket.KEY_DESC,description);
              startActivity(i);
            });
        rcvTiket.setAdapter(adapterTiketAll);
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
        }, throwable -> {});
    adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                String staff_name, String staff_phone, String instrument_type, String instrument,
                String priority, String number, String customer_name, String contract, String description) -> {
              Intent i = new Intent(getContext(), DetailOpenTiket.class);
              String idtiket = String.valueOf(id);
              String customer_id = String.valueOf(id_customer);
              String id_activity = String.valueOf(activity_id);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
              i.putExtra(DetailOpenTiket.KEY_CAT,category);
              i.putExtra(DetailOpenTiket.KEY_TICK,ticket_type);
              i.putExtra(DetailOpenTiket.KEY_CUST, customer_id);
              i.putExtra(DetailOpenTiket.KEY_ACTI,id_activity);
              i.putExtra(DetailOpenTiket.KEY_SNAME,staff_name);
              i.putExtra(DetailOpenTiket.KEY_SPHN, staff_phone);
              i.putExtra(DetailOpenTiket.KEY_INST,instrument_type);
              i.putExtra(DetailOpenTiket.KEY_INS,instrument);
              i.putExtra(DetailOpenTiket.KEY_PRIO,priority);
              i.putExtra(DetailOpenTiket.KEY_NUM,number);
              i.putExtra(DetailOpenTiket.KEY_CUSTN,customer_name);
              i.putExtra(DetailOpenTiket.KEY_CONT,contract);
              i.putExtra(DetailOpenTiket.KEY_DESC,description);
              startActivity(i);
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
      }
  }
}
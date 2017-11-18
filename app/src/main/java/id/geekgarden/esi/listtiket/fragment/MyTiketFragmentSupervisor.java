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
import id.geekgarden.esi.data.model.tikets.supervisorticket.adapter.AdapterTiketAllSpv;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailEnded;
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
    private static ProgressDialog pDialog;
    private String supervisor;

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
                        if (ticket_type.equals("Installation")) {
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
//

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
                (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                    String staff_name, String staff_phone, String instrument_type, String instrument,
                    String priority, String number, String customer_name, String contract, String description) -> {});
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
        (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
            String staff_name, String staff_phone, String instrument_type, String instrument,
            String priority, String number, String customer_name, String contract, String description) -> {});
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
        (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
            String staff_name, String staff_phone, String instrument_type, String instrument,
            String priority, String number, String customer_name, String contract, String description) -> {
          Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
          id_ticket = String.valueOf(id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailHoldTiketSpv.KEY_ID, id_ticket);
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
        (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
            String staff_name, String staff_phone, String instrument_type, String instrument,
            String priority, String number, String customer_name, String contract, String description) -> {});
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
        (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
            String staff_name, String staff_phone, String instrument_type, String instrument,
            String priority, String number, String customer_name, String contract, String description) -> {
          Log.e("loaddataspvopen", "MyTiketFragmentSupervisor" + id);
          Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
          id_ticket = String.valueOf(id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailOpenTiketSpv.KEY_ID, id_ticket);
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
        (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
            String staff_name, String staff_phone, String instrument_type, String instrument,
            String priority, String number, String customer_name, String contract, String description) -> {});
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
              (int id, String status, String ticket_type, int id_customer, String category, int activity_id,
                  String staff_name, String staff_phone, String instrument_type, String instrument,
                  String priority, String number, String customer_name, String contract, String description) -> {
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
                      } else if (status.equals("held")) {
                          Intent i = new Intent(getContext(), DetailOnHold.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailOnHold.KEY_URI, idtiket);
                          startActivity(i);
                      } else if (status.equals("restarted")) {
                      } else if (status.equals("done")) {
                          Intent i = new Intent(getContext(), DetailEnded.class);
                          String idtiket = String.valueOf(id);
                          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          i.putExtra(DetailEnded.KEY_URI, idtiket);
                          startActivity(i);
                      } else {
                          glpref.read(PrefKey.statustiket, String.class);
                      }
                  }
              });
      rcvTiket.setAdapter(adapterTiketAllSpv);

//
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
    }
  }

  @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
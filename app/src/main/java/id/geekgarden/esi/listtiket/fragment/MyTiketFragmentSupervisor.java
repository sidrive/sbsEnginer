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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.supervisorticket.AdapterTiketAllSpv;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.listtiket.activityticketsupervisor.DetailOpenTiketSpv;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTiketFragmentSupervisor extends Fragment {
    private static final String KEY = "key";
    @BindView(R.id.etSearch)
    EditText etSearch;
    private Api mApi;
    private Unbinder unbinder;
    private AdapterTiketAllSpv adapterTiketAllSpv;
    private GlobalPreferences glpref;
    private String accessToken;
    private String key;
    private static ProgressDialog pDialog;
    private String supervisor;

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
    public void onResume() {
        super.onResume();
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
        mApi = ApiService.getervice();
        supervisor = glpref.read(PrefKey.position_name,String.class);
        accessToken = glpref.read(PrefKey.accessToken, String.class);
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
        Log.e("onCreate", "MyTiketFragment" + key);
        return v;
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
        (id, status, ticket_type,id_customer) -> {});
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
        (id, status, ticket_type,id_customer) -> {});
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
        (id, status, ticket_type,id_customer) -> {
          Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
          String idtiket = String.valueOf(id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailOpenTiketSpv.KEY_ID, idtiket);
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
        (id, status, ticket_type,id_customer) -> {});
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
        (id, status, ticket_type,id_customer) -> {
          Intent i = new Intent(getContext(), DetailOpenTiketSpv.class);
          String idtiket = String.valueOf(id);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra(DetailOpenTiketSpv.KEY_ID, idtiket);
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
        (id, status, ticket_type,id_customer) -> {});
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
        (id, status, ticket_type,id_customer) -> {});
    rcvTiket.setAdapter(adapterTiketAllSpv);
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
}
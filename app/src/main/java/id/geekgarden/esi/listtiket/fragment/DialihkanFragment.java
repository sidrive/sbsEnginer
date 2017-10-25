package id.geekgarden.esi.listtiket.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.supervisorticket.AdapterTiketNewAlihSpv;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Observable;

public class DialihkanFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
  private final static  String TAG = DialihkanFragment.class.getSimpleName();
  private static final String KEY = "key";
  private String keyFragment;
  private GlobalPreferences glpref;
  private Api mApi;
  private Unbinder unbinder;
  private String accessToken;
  private String key;
  private static ProgressDialog pDialog;
  private String supervisor;
  private AdapterTiketNewAlihSpv adapterTiketNewAlihSpv;
  public DialihkanFragment() {}
  @BindView(R.id.rcvTiket)RecyclerView rcvTiket;
  @BindView(R.id.progressBar)ProgressBar progressBar;
  @BindView(R.id.swipeRefresh)SwipeRefreshLayout swipeRefresh;
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = this.getArguments();
    if (getArguments() != null) {
      key = getArguments().getString(KEY);
      Log.e("onCreate", "MyTiketFragment" + key);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_dialihkan, container, false);
    unbinder = ButterKnife.bind(this, v);
    pDialog = new ProgressDialog(getContext());
    pDialog.setTitle("Loading....");
    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    pDialog.setCancelable(false);
    glpref = new GlobalPreferences(getContext());
    mApi = ApiService.getervice();
    supervisor = glpref.read(PrefKey.position_name,String.class);
    Log.e("onCreateView", "MyTiketFragment" + supervisor);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    if (key.equals("open")) {
        openSpvAlih();
    } else if (key.equals("confirm")) {
        openSpvConfirm();
    } else if (key.equals("progres new")) {
        progressNewSpv();
    } else if (key.equals("hold")) {
        holdSpv();
    } else if (key.equals("ended")) {
        endedSpv();
    } else if (key.equals("progres hold")) {
        progressHoldalih();
    } else if (key.equals("all")) {
        allSpv();
    }
    swipeRefresh.setOnRefreshListener(this);
    Log.e("onCreate", "MyTiketFragment" + key);
    return v;
  }

  private void allSpv() {
    pDialog.show();
    Observable<ResponseTikets> getallalih = mApi
        .getticketallspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getallalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void progressHoldalih() {
    pDialog.show();
    Observable<ResponseTikets> getonprogressholdalih = mApi
        .getticketonprogressholdspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getonprogressholdalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void endedSpv() {
    pDialog.show();
    Observable<ResponseTikets> getendedspv = mApi
        .getticketendedspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getendedspv.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void holdSpv() {
    pDialog.show();
    Observable<ResponseTikets> getholdalih = mApi
        .getticketheldspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getholdalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void progressNewSpv() {
    pDialog.show();
    Observable<ResponseTikets> getonprogressalih = mApi
        .getticketonprogressnewspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getonprogressalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void openSpvConfirm() {
    pDialog.show();
    Observable<ResponseTikets> getconfirmalih = mApi
        .getticketconfirmedspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getconfirmalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
  }

  private void openSpvAlih() {
    pDialog.show();
    Observable<ResponseTikets> getopenalih = mApi
        .getticketopenspvalih(accessToken)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    getopenalih.subscribe(responseTikets -> {
      adapterTiketNewAlihSpv.notifyDataSetChanged();
      pDialog.dismiss();
      if (responseTikets.getData() != null) {
        swipeRefresh.setRefreshing(false);
        adapterTiketNewAlihSpv.UpdateTikets(responseTikets.getData());
      } else {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
      }
    },throwable -> {});
    adapterTiketNewAlihSpv = new AdapterTiketNewAlihSpv(new ArrayList<Datum>(0), getContext(),
        (id, status, ticket_type,id_customer) -> {
          Log.e("loaddataspvall", "MyTiketFragment" + id);
          Log.e("loaddataspvall", "MyTiketFragment" + status);
          Log.e("loaddataspvall", "MyTiketFragment" + ticket_type);
          Log.e("loaddataspvall", "MyTiketFragment" + id_customer);
        });
    rcvTiket.setAdapter(adapterTiketNewAlihSpv);
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

  @Override
  public void onResume() {
    super.onResume();
    onRefresh();
  }

  @Override
  public void onRefresh() {
    swipeRefresh.setRefreshing(false);
    if (key.equals("open")) {
      openSpvAlih();
    } else if (key.equals("confirm")) {
      openSpvConfirm();
    } else if (key.equals("progres new")) {
      progressNewSpv();
    } else if (key.equals("hold")) {
      holdSpv();
    } else if (key.equals("ended")) {
      endedSpv();
    } else if (key.equals("progres hold")) {
      progressHoldalih();
    } else if (key.equals("all")) {
      allSpv();
    }
  }
}

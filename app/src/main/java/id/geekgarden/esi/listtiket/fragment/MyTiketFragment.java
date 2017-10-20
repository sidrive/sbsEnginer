package id.geekgarden.esi.listtiket.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterSearchTiket;
import id.geekgarden.esi.data.model.tikets.AdapterTiketAll;
import id.geekgarden.esi.data.model.tikets.AdapterTiketConfirmed;
import id.geekgarden.esi.data.model.tikets.AdapterTiketEnded;
import id.geekgarden.esi.data.model.tikets.AdapterTiketNew;
import id.geekgarden.esi.data.model.tikets.AdapterTiketOnHeld;
import id.geekgarden.esi.data.model.tikets.AdapterTiketOnProgressHeld;
import id.geekgarden.esi.data.model.tikets.AdapterTiketOnProgressNew;
import id.geekgarden.esi.data.model.tikets.searchtiket.ResponseSearchTiket;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.listtiket.activitymyticket.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activitymyticket.DetailEnded;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOnHold;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOnProgressHold;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOnProgressNew;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOpenTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MyTiketFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String KEY = "key";
    @BindView(R.id.etSearch)
    EditText etSearch;
    private Api mApi;
    private Unbinder unbinder;
    private AdapterTiketNew adapterTiketNew;
    private AdapterTiketOnProgressNew adapterTiketOnProgressNew;
    private AdapterTiketConfirmed adapterTiketConfirmed;
    private AdapterTiketOnHeld adapterTiketOnHeld;
    private AdapterTiketEnded adapterTiketEnded;
    private AdapterTiketOnProgressHeld adapterTiketOnProgressHeld;
    private AdapterTiketAll adapterTiketAll;
    private AdapterSearchTiket adapterSearchTiket;
    private GlobalPreferences glpref;
    private String accessToken;
    private String key;
    private static ProgressDialog pDialog;

    public MyTiketFragment() {
    }
    @BindView(R.id.rcvTiket)
    RecyclerView rcvTiket;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

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
        onRefresh();
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
        accessToken = glpref.read(PrefKey.accessToken, String.class);
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
        }
        swipeRefresh.setOnRefreshListener(this);
        Log.e("onCreate", "MyTiketFragment" + key);
        return v;
    }

    @OnTextChanged(value = R.id.etSearch,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void setEtSearch(CharSequence q){
        Log.e("searchTiket", "MyTiketFragment" + q);
        if (q.length()>=2){
          String name = q.toString();
            queryearchTiket(name);
        }if (q.length()==0){
            loadDataTiketall();
        }
    }

    private void queryearchTiket(String name) {
        pDialog.show();
        adapterSearchTiket = new AdapterSearchTiket(new ArrayList<id.geekgarden.esi.data.model.tikets.searchtiket.Datum>(), getContext(),
            (id, status,id_customer,ticket_type) -> {
              Log.e(TAG, "onPostClickListener: "+status);
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
                  if (ticket_type == 2){
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST,customer_id);
                    startActivity(i);
                  }else {
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
                  if (ticket_type == 2){
                    Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                    String idtiket = String.valueOf(id);
                    String customer_id = String.valueOf(id_customer);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                    i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST,customer_id);
                    startActivity(i);
                  }else {
                    Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                    String idtiket = String.valueOf(id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                    startActivity(i);
                  }
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
          swipeRefresh.setRefreshing(false);
          adapterSearchTiket.UpdateTikets(responseSearchTiket1.getData());
        } else {
          swipeRefresh.setRefreshing(false);
          Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
        }
      },throwable -> pDialog.dismiss());
      rcvTiket.setAdapter(adapterSearchTiket);
    }

    private void loadDataTiketall() {
        pDialog.show();
        final Observable<ResponseTikets> respontiket = mApi
            .getTiketall(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          adapterTiketAll.notifyDataSetChanged();
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
            adapterTiketAll.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {});
        adapterTiketAll = new AdapterTiketAll(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type,id_customer) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
              Log.e("loadDataTiketall", "MyTiketFragment" + id_customer);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
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
              if (ticket_type == 2){
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String customer_id = String.valueOf(id_customer);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST,customer_id);
                startActivity(i);
              }else{
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
              if (ticket_type == 2){
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                startActivity(i);
              }else{
                Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                startActivity(i);
              }
            } else if (status.equals("done")) {
                Intent i = new Intent(getContext(), DetailEnded.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailEnded.KEY_URI, idtiket);
                startActivity(i);
            }
        } else {
            glpref.read(PrefKey.statustiket, String.class);
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
          adapterTiketOnProgressHeld.notifyDataSetChanged();
          Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            adapterTiketOnProgressHeld.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }},throwable -> {});
        adapterTiketOnProgressHeld = new AdapterTiketOnProgressHeld(new ArrayList<Datum>(0), getContext(),
            (id, status,ticket_type) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
              if (ticket_type == 2){
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                startActivity(i);
              }else{
                Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                startActivity(i);
              }
            });
        rcvTiket.setAdapter(adapterTiketOnProgressHeld);
    }

    private void loadDataTiketended() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketended(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          adapterTiketEnded.notifyDataSetChanged();
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            adapterTiketEnded.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        },throwable -> {});
        adapterTiketEnded = new AdapterTiketEnded(new ArrayList<Datum>(0), getContext(),
            (id, status) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
                Intent i = new Intent(getContext(), DetailEnded.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailEnded.KEY_URI, idtiket);
                startActivity(i);
            });
        rcvTiket.setAdapter(adapterTiketEnded);
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
            swipeRefresh.setRefreshing(false);
            adapterTiketOnHeld.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {});
        adapterTiketOnHeld = new AdapterTiketOnHeld(new ArrayList<Datum>(0), getContext(),
            (id, status) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
                String idtiket = String.valueOf(id);
                Intent i = new Intent(getContext(), DetailOnHold.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnHold.KEY_URI, idtiket);
                startActivity(i);
            });
        rcvTiket.setAdapter(adapterTiketOnHeld);
    }

    private void loadDataTiketonprogress() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketstarted(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          adapterTiketOnProgressNew.notifyDataSetChanged();
          Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            adapterTiketOnProgressNew.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {

        });
        adapterTiketOnProgressNew = new AdapterTiketOnProgressNew(new ArrayList<Datum>(0), getContext(),
            (id, id_customer,ticket_type) -> {
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
              if (ticket_type == 2){
                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                String idtiket = String.valueOf(id);
                String ID_customer = String.valueOf(id_customer);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                startActivity(i);
              }else{
                Intent i = new Intent(getContext(), DetailOnProgressNew.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOnProgressNew.KEY_URI, idtiket);
                startActivity(i);
              }
            });
        rcvTiket.setAdapter(adapterTiketOnProgressNew);
    }

    public void loadDataTiketconfirm() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi
            .getTiketsconfirmed(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          adapterTiketConfirmed.notifyDataSetChanged();
          Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            adapterTiketConfirmed.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {});
        adapterTiketConfirmed = new AdapterTiketConfirmed(new ArrayList<Datum>(0), getContext(),
            (id, status) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
                Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailConfirmedTiket.KEY_URI, idtiket);
                startActivity(i);
            });
        rcvTiket.setAdapter(adapterTiketConfirmed);
    }


    private void loadDataTiketOpen() {
        pDialog.show();
        Observable<ResponseTikets> respontiket = mApi.getTiketsnew(accessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respontiket.subscribe(responseTikets -> {
          adapterTiketNew.notifyDataSetChanged();
          Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
          swipeRefresh.setRefreshing(false);
          if (responseTikets.getData() != null) {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            adapterTiketNew.UpdateTikets(responseTikets.getData());
          } else {
            pDialog.dismiss();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
          }
        }, throwable -> {});
        adapterTiketNew = new AdapterTiketNew(new ArrayList<Datum>(0), getContext(),
            (id, status) -> {
                Log.e(TAG, "onPostClickListener: " + id);
                Log.e(TAG, "onPostClickListener: " + status);
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
                Intent i = new Intent(getContext(), DetailOpenTiket.class);
                String idtiket = String.valueOf(id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(DetailOpenTiket.KEY_URI, idtiket);
                startActivity(i);
            });
        rcvTiket.setAdapter(adapterTiketNew);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTiket.setHasFixedSize(true);
        rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
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
        }
    }
}
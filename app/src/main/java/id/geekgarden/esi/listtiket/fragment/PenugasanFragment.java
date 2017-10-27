package id.geekgarden.esi.listtiket.fragment;

import android.app.ProgressDialog;
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
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSearchTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketSwitch;
import id.geekgarden.esi.data.model.tikets.supervisorticket.AdapterTiketAllTugasSpv;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PenugasanFragment extends Fragment {
    private static final String KEY = "key";
    @BindView(R.id.etSearch)
    EditText etSearch;
    private Api mApi;
    private Unbinder unbinder;
    private AdapterTiketAllTugasSpv adapterTiketAllTugasSpv;
    private AdapterSearchTiket adapterSearchTiket;
    private AdapterTiketSwitch adapterTiketSwitch;
    private GlobalPreferences glpref;
    private String accessToken;
    private String key;
    private static ProgressDialog pDialog;

    public PenugasanFragment() {
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
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (key.equals("open")) {
            spvOpen();
        } else if (key.equals("confirm")) {
            spvConfirm();
        } else if (key.equals("progres new")) {
            spvProgressNew();
        } else if (key.equals("hold")) {
            spvhold();
        } else if (key.equals("ended")) {
            spvdone();
        } else if (key.equals("progres hold")) {
            spvProgressHold();
        } else if (key.equals("all")) {
            spvall();
        }
        return v;
    }

    private void spvall() {
        pDialog.show();
        Observable<ResponseTikets> getspvall= mApi
            .getticketallspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getspvall.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvProgressHold() {
        pDialog.show();
        Observable<ResponseTikets> getprogressholdspv= mApi
            .getticketonprogressholdspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getprogressholdspv.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvdone() {
        pDialog.show();
        Observable<ResponseTikets> getdonespv= mApi
            .getticketendedspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getdonespv.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvhold() {
        pDialog.show();
        Observable<ResponseTikets> getspvhold= mApi
            .getticketheldspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getspvhold.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvProgressNew() {
        pDialog.show();
        Observable<ResponseTikets> getspvprogressnew= mApi
            .getticketonprogressnewspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getspvprogressnew.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {
                glpref.write(PrefKey.idtiket, String.valueOf(id), String.class);
                glpref.write(PrefKey.statustiket, status, String.class);
            });
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvConfirm() {
        pDialog.show();
        Observable<ResponseTikets> getspvconfirm= mApi
            .getticketconfirmspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getspvconfirm.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
    }

    private void spvOpen() {
        pDialog.show();
        Observable<ResponseTikets> getspvopen= mApi
            .getticketopenspv(accessToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        getspvopen.subscribe(responseTikets -> {
            adapterTiketAllTugasSpv.notifyDataSetChanged();
            if (responseTikets.getData() != null) {
                pDialog.dismiss();
                adapterTiketAllTugasSpv.UpdateTikets(responseTikets.getData());
            } else {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
            (id, status, ticket_type, id_customer, category) -> {});
        rcvTiket.setAdapter(adapterTiketAllTugasSpv);
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
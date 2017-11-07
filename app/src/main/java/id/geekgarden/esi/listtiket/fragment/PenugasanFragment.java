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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSearchTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterTiketSwitch;
import id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket.ResponseSearchTiket;
import id.geekgarden.esi.data.model.tikets.supervisorticket.adapter.AdapterTiketAllTugasSpv;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailEnded;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailInstrumentForm;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnHold;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgressNew;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOpenTiket;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

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

//
    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void setEtSearch (CharSequence q){
        if (q.length()>=2){
            String name = q.toString();
            queryearchTiket(name);
        }if (q.length()==0){
            spvall();
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
                adapterSearchTiket.UpdateTikets(responseSearchTiket1.getData());
            } else {
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        },throwable -> pDialog.dismiss());
        rcvTiket.setAdapter(adapterSearchTiket);
    }
//


    private void spvall() {
        pDialog.show();
        Observable<ResponseTikets> getspvall = mApi
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
        }, throwable -> {
        });
//        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
//            (id, status, ticket_type, id_customer, category) -> {});
        adapterTiketAllTugasSpv = new AdapterTiketAllTugasSpv(new ArrayList<Datum>(0), getContext(),
                (id, status, ticket_type, id_customer, category) -> {
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
                            }
                            if (category.equals("PM")) {
                                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                                String idtiket = String.valueOf(id);
                                String ID_customer = String.valueOf(id_customer);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                                startActivity(i);
                            }
                            if (category.equals("Other")) {
                                Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                                String idtiket = String.valueOf(id);
                                String customer_id = String.valueOf(id_customer);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                                i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                                startActivity(i);
                            }
                            if (category.equals("")) {
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
                            if (category.equals("Visit")) {
                                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                                String idtiket = String.valueOf(id);
                                String ID_customer = String.valueOf(id_customer);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                                startActivity(i);
                            }
                            if (category.equals("PM")) {
                                Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
                                String idtiket = String.valueOf(id);
                                String ID_customer = String.valueOf(id_customer);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_URI, idtiket);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CUST, ID_customer);
                                i.putExtra(DetailOnProgresvisitPmOther.KEY_CAT, category);
                                startActivity(i);
                            }
                            if (category.equals("Other")) {
                                Intent i = new Intent(getContext(), DetailInstrumentForm.class);
                                String idtiket = String.valueOf(id);
                                String customer_id = String.valueOf(id_customer);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra(DetailInstrumentForm.KEY_URI, idtiket);
                                i.putExtra(DetailInstrumentForm.KEY_CUST, customer_id);
                                startActivity(i);
                            }
                            if (category.equals("")) {
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
                        } else {
                            glpref.read(PrefKey.statustiket, String.class);
                        }
                    }
                });
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
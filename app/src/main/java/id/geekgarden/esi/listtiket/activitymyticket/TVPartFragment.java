package id.geekgarden.esi.listtiket.activitymyticket;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.model.tikets.AdapterOnHoldPart;
import id.geekgarden.esi.data.model.tikets.part.Datum;
import id.geekgarden.esi.data.model.tikets.part.ResponsePart;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raka on 10/2/17.
 */

@SuppressLint("ValidFragment")
public class TVPartFragment extends DialogFragment {
    @BindView(R.id.rvsparepart)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private AdapterOnHoldPart adapterOnHoldPart;
    private GlobalPreferences glpref;
    private Api mApi;
    private String id_tiket;
    private String id_tiket_act;

    @SuppressLint("ValidFragment")
    public TVPartFragment(GlobalPreferences glpref, Api mApi, String id_tiket, String id_tiket_act) {
        this.glpref = glpref;
        this.mApi = mApi;
        this.id_tiket = id_tiket;
        this.id_tiket_act = id_tiket_act;
    }

    Observable<ResponsePart> responsepart;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_listpart, container);
        unbinder = ButterKnife.bind(this, rootView);
        rv = rootView.findViewById(R.id.rvsparepart);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapterOnHoldPart = new AdapterOnHoldPart(new ArrayList<Datum>(0), this.getActivity());
        responsepart = mApi
            .getpart(glpref.read(PrefKey.accessToken, String.class), id_tiket, id_tiket_act)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
        responsepart.subscribe(responsePart -> {
            if (responsePart.getData().size() != 0) {
                adapterOnHoldPart.UpdateTikets(responsePart.getData());
                tvEmpty.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
            } else {
                rv.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        },throwable -> {});
        rv.setAdapter(adapterOnHoldPart);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

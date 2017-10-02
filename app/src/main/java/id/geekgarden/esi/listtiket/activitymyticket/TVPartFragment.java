package id.geekgarden.esi.listtiket.activitymyticket;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterOnHoldPart;
import id.geekgarden.esi.data.model.tikets.part.Datum;
import id.geekgarden.esi.data.model.tikets.part.ResponsePart;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static id.geekgarden.esi.preference.PrefKey.accessToken;

/**
 * Created by raka on 10/2/17.
 */

public class TVPartFragment extends DialogFragment {
    public static final String KEY_URI = "id_ticket";
    public static final String KEY_ACT = "id_ticket_activity";
    private List<Datum> listarray = new ArrayList<Datum>();
    @BindView(R.id.rvsparepart)
    RecyclerView rvsparepart;
    Unbinder unbinder;
    private AdapterOnHoldPart adapterOnHoldPart;
    private GlobalPreferences glpref;
    private Api mApi;

    RecyclerView rv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public TVPartFragment() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getContext());
        glpref.read(PrefKey.accessToken, String.class);
        Bundle bundle = getArguments();
        String idtiket = bundle.getString(KEY_URI);
        String id_ticket_activity = bundle.getString(KEY_ACT);
        Observable<ResponsePart> responsepart = mApi.getpart(accessToken, idtiket, id_ticket_activity).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responsepart.subscribe(new Observer<ResponsePart>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(ResponsePart responsePart) {
                for (int i1 = 0; i1 < responsePart.getData().size(); i1++) {
                    Datum sp = new Datum();
                    sp.setPartNumber(responsePart.getData().get(i1).getPartNumber());
                    sp.setDescription(responsePart.getData().get(i1).getDescription());
                    sp.setQuantity(responsePart.getData().get(i1).getQuantity());
                    sp.setStatus(responsePart.getData().get(i1).getStatus());
                    sp.setRemarks(responsePart.getData().get(i1).getRemarks());
                    listarray.add(sp);
                    Log.e("", "onNext: " + listarray.size());
                    adapterOnHoldPart.UpdateTikets(listarray);
                }
            }
        });
    }




    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_listpart, container);

        //RECYCER
        rv = (RecyclerView) rootView.findViewById(R.id.rvsparepart);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
        adapterOnHoldPart = new AdapterOnHoldPart(new ArrayList<Datum>(0), this.getActivity());
        rv.setAdapter(adapterOnHoldPart);

        this.getDialog().setTitle("TV Shows");


        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

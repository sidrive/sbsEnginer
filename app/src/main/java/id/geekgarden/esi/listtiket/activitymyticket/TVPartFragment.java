package id.geekgarden.esi.listtiket.activitymyticket;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;

import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.AdapterOnHoldPart;
import id.geekgarden.esi.data.model.tikets.part.ResponsePart;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static id.geekgarden.esi.preference.PrefKey.accessToken;
import static id.geekgarden.esi.preference.PrefKey.idtiket;

/**
 * Created by raka on 10/2/17.
 */

public class TVPartFragment extends DialogFragment {
    public static final String KEY_URI = "id_ticket";
    public static final String KEY_ACT = "id_ticket_activity";
    private AdapterOnHoldPart adapterOnHoldPart;
    private GlobalPreferences glpref;
    private Api mApi;

    RecyclerView rv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public TVPartFragment() {
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getContext());
        glpref.read(PrefKey.accessToken, String.class);
        idtiket = arg.getStringExtra(KEY_URI);
        idticketactivity =
        Observable<ResponsePart> responsepart = mApi.getpart(accessToken, idtiket).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.activity_listpart,container);

        //RECYCER
        rv= (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
        adapterOnHoldPart = new AdapterOnHoldPart(this.getActivity(),tvshows);
        rv.setAdapter(adapterOnHoldPart);

        this.getDialog().setTitle("TV Shows");


        return rootView;
    }
}

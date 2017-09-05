package id.geekgarden.esi.listtiket.fragment;


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

import id.geekgarden.esi.data.apis.ApiService;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.model.tikets.AdapterTiketNew;
import id.geekgarden.esi.data.model.tikets.Data;
import id.geekgarden.esi.data.model.tikets.Datum;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.TiketsHolder;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  private Unbinder unbinder;
  private AdapterTiketNew adapterTiketNew;
  private GlobalPreferences glpref;
  private String accessToken;
  private String key;
  private PrefKey prefKey;
  public MyTiketFragment() {


  }

  @BindView(R.id.rcvTiket)
  RecyclerView rcvTiket;

  /*@BindView(R.id.fab)FloatingActionButton fab;
  @OnClick(R.id.fab)void OpenNewTiket(View view){
    Intent i = new Intent(getContext(), OpenTiketIT.class);
    startActivity(i);
  }*/
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments()!=null){
    key = getArguments().getString(KEY);
      Log.e("onCreate", "MyTiketFragment" + key);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_mytiket, container, false);
    unbinder = ButterKnife.bind(this, v);
    glpref = new GlobalPreferences(getContext());
    mApi = ApiService.getervice();
    accessToken = glpref.read(PrefKey.accessToken, String.class);

    if (key.equals("all")){
      loadDataTiketAll();
    }else if (key.equals("")){

    }else if (key.equals("")){

    }else if (key.equals("")){

    }


    return v;
  }

  private void loadDataTiketAll() {

    Observable<ResponseTikets> respontiket = mApi.getTikets(accessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respontiket.subscribe(new Observer<ResponseTikets>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("onError", "MyTiketFragment" + e.getMessage());
      }

      @Override
      public void onNext(ResponseTikets responseTikets) {
        Log.e("onNext", "MyTiketFragment" + responseTikets.getMessage());
        Log.e("onNext", "MyTiketFragment" + responseTikets.getStatusCode());
        Log.e("onNext", "MyTiketFragment" + responseTikets.getData().size());
        if (responseTikets.getSuccess()){
          adapterTiketNew.UpdateTikets(responseTikets.getData());
        }
      }
    });
    adapterTiketNew = new AdapterTiketNew(new ArrayList<Datum>(0), getContext(), new AdapterTiketNew.OnTiketPostItemListener() {
      @Override
      public void onPostClickListener(int id, String status) {
        Log.e(TAG, "onPostClickListener: "+id);
        Log.e(TAG, "onPostClickListener: "+status);
        glpref.write(PrefKey.idtiket,String.valueOf(id),String.class);
        glpref.write(PrefKey.statustiket,status,String.class);
      }
    });
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rcvTiket.setHasFixedSize(true);
    rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));

    rcvTiket.setAdapter(adapterTiketNew);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
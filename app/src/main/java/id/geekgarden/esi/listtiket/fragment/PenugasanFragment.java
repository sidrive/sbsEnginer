package id.geekgarden.esi.listtiket.fragment;


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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.listtiket.activity.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activity.DetailEnded;
import id.geekgarden.esi.listtiket.activity.DetailOnHold;
import id.geekgarden.esi.listtiket.activity.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activity.DetailOpenTiket;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PenugasanFragment extends Fragment {
  private final static String TAG = PenugasanFragment.class.getSimpleName();
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  public PenugasanFragment() {  }
  @BindView(R.id.rcvTiket)RecyclerView rcvTiket;
  /*@BindView(R.id.fab)FloatingActionButton fab;
  @OnClick(R.id.fab)void OpenNewTiket(View view){
    Intent i = new Intent(getContext(), OpenTiketIT.class);
    startActivity(i);
  }*/
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = this.getArguments();
    if (bundle!=null){
      keyFragment = getArguments().getString(KEY);
      Log.e("PenugasanFragment", "onCreate = " + keyFragment);
      mApi = ApiService.getervice();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_penugasan, container, false);
    ButterKnife.bind(this,v);
    /*loadData();*/
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }
  /*private void loadData() {
    final Observable<ResponseTiketsPenugasan> respon = mApi.getTiketsPenugasan().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(new Observer<ResponseTiketsPenugasan>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: "+e.getMessage() );
      }

      @Override
      public void onNext(ResponseTiketsPenugasan responseTiketsPenugasan) {
        adapter.UpdateTiket(responseTiketsPenugasan.getTiketsPenugasan());}
    });*/


}



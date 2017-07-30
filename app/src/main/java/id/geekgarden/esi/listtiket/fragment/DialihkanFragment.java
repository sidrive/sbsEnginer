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
import id.geekgarden.esi.data.model.tikets_dialihkan.AdapterTiketsDialihkan;
import id.geekgarden.esi.data.model.tikets_dialihkan.ResponseTiketsDialihkan;
import id.geekgarden.esi.data.model.tikets_dialihkan.TiketsDialihkanItem;
import id.geekgarden.esi.listtiket.activity.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activity.DetailDialihkan;
import id.geekgarden.esi.listtiket.activity.DetailEnded;
import id.geekgarden.esi.listtiket.activity.DetailOnHold;
import id.geekgarden.esi.listtiket.activity.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activity.DetailOpenTiket;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DialihkanFragment extends Fragment {
  private final static  String TAG = DialihkanFragment.class.getSimpleName();
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  private AdapterTiketsDialihkan adapter;
  public DialihkanFragment() {}
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
      Log.e("DialihkanFragment", "onCreate = " + keyFragment);
      mApi = ApiService.getervice();
  }

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_dialihkan, container, false);
    ButterKnife.bind(this,v);
    loadData();
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter = new AdapterTiketsDialihkan(getContext(), new ArrayList<TiketsDialihkanItem>(0), new AdapterTiketsDialihkan.PostItemListener() {
      @Override
      public void OnPostClickItemListener(long id, String s) {


          Intent i = new Intent(getContext(), DetailDialihkan.class);
          startActivity(i);

      }
    });


    rcvTiket.setHasFixedSize(true);
    rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    rcvTiket.setAdapter(adapter);
  }
  private void loadData() {
    Observable<ResponseTiketsDialihkan> respon = mApi.getTiketsDialihkan().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(new Observer<ResponseTiketsDialihkan>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: "+e.getMessage() );
      }

      @Override
      public void onNext(ResponseTiketsDialihkan responseTiketsDialihkan) {
        adapter.UpdateItem(responseTiketsDialihkan.getTiketsDialihkan());

      }
    });


  }

}

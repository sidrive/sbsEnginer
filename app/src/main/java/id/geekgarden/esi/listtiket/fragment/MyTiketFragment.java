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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;



import id.geekgarden.esi.data.model.tikets.AdapterTiket;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.TiketsItem;
import id.geekgarden.esi.listtiket.activity.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activity.DetailEnded;
import id.geekgarden.esi.listtiket.activity.DetailOnHold;
import id.geekgarden.esi.listtiket.activity.DetailOnProgresvisitPmOther;
import id.geekgarden.esi.listtiket.activity.DetailOpenTiket;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;

  private AdapterTiket adapter;
  private Unbinder unbinder;
    public MyTiketFragment() {}
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
      Log.e("MyTiketFragment", "onCreate = " + keyFragment);
      mApi = ApiService.getervice();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v =  inflater.inflate(R.layout.fragment_mytiket, container, false);
    unbinder = ButterKnife.bind(this,v);
    loadData();

    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter = new AdapterTiket(getContext(), new ArrayList<TiketsItem>(0), new AdapterTiket.PostItemListener() {
      @Override
      public void onPostClickLsitener(long id, String status) {
        if (status.equals("open")){
          Intent i = new Intent(getContext(), DetailOpenTiket.class);
          startActivity(i);
        }
        if (status.equals("Confirm")){
          Intent i = new Intent(getContext(), DetailConfirmedTiket.class);
          startActivity(i);
        }
        if (status.equals("On Progress")){
          Intent i = new Intent(getContext(), DetailOnProgresvisitPmOther.class);
          startActivity(i);
        }
        if (status.equals("On Hold")){
          Intent i = new Intent(getContext(), DetailOnHold.class);
          startActivity(i);
        }
        if (status.equals("Ended")){
          Intent i = new Intent(getContext(), DetailEnded.class);
          startActivity(i);
        }
      }
    });

    rcvTiket.setHasFixedSize(true);
    rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    rcvTiket.setAdapter(adapter);

  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void loadData() {
      Observable<ResponseTikets> responseTiketsObservable = mApi.getTikets().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
      responseTiketsObservable.subscribe(new Observer<ResponseTikets>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          Log.e("TAG", "onError: "+e.getMessage());

        }

        @Override
        public void onNext(ResponseTikets responseTikets) {
          adapter.UpdateTikets(responseTikets.getTikets());

        }
      });

  }

}

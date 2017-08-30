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


import id.geekgarden.esi.data.model.tikets.AdapterTiketFirebase;
import id.geekgarden.esi.data.model.tikets.FirebaseHolder;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.TiketsItem;
import id.geekgarden.esi.helper.Const;
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

import static android.content.ContentValues.TAG;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  private Unbinder unbinder;
  private FirebaseDatabase mDatabse;
  private DatabaseReference mRef;
  private DatabaseReference mListTiket;
  private AdapterTiketFirebase adapterTiketFirebase;
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
    //loadData();
    mDatabse = FirebaseDatabase.getInstance();
    mRef = mDatabse.getReference().child(Const.TiketRef);
    mListTiket = mRef.child(Const.ListTiketRef);
    populateDataTiketFromFirebase(mListTiket);

    return v;
  }

  private void populateDataTiketFromFirebase(DatabaseReference mListTiket) {
    mListTiket.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        TiketsItem tiketsItem = dataSnapshot.getValue(TiketsItem.class);
        Log.e(TAG, "onChildAdded: "+tiketsItem.getNamaCustomer().toString() );
        adapterTiketFirebase.notifyDataSetChanged();
        Log.e("onChildAdded", "onChildAdded: "+dataSnapshot.toString() );
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        adapterTiketFirebase.notifyDataSetChanged();
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        adapterTiketFirebase.notifyDataSetChanged();
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        adapterTiketFirebase.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, "onCancelled: "+databaseError.getDetails().toString() );
      }
    });

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    /*adapter = new AdapterTiket(getContext(), new ArrayList<TiketsItem>(0), new AdapterTiket.PostItemListener() {
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
    });*/

    adapterTiketFirebase = new AdapterTiketFirebase(TiketsItem.class,R.layout.item_list_tiket,FirebaseHolder.class,mListTiket);
    rcvTiket.setHasFixedSize(true);
    rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    rcvTiket.setAdapter(adapterTiketFirebase);

  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

 /* private void loadData() {
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

  }*/

}

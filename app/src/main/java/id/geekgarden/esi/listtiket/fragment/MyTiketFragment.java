package id.geekgarden.esi.listtiket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  private Unbinder unbinder;
  /*private FirebaseDatabase mDatabse;
  private DatabaseReference mRef;
  private DatabaseReference mListTiket;
  private AdapterTiket adapterTiketFirebase;*/
  private GlobalPreferences glpref;

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

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_mytiket, container, false);
    unbinder = ButterKnife.bind(this, v);
    glpref = new GlobalPreferences(getContext());
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    Observable<ResponseTikets> respontiket = mApi.getTikets(accessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respontiket.subscribe(new Observer<ResponseTikets>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(ResponseTikets responseTikets) {

      }
    });
    //loadData();
    /*mDatabse = FirebaseDatabase.getInstance();
    mRef = mDatabse.getReference().child(Const.TiketRef);
    populateDataTiketFromFirebase(mListTiket);*/
    return v;
  }

 /* private void populateDataTiketFromFirebase(DatabaseReference mListTiket) {
    mListTiket.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        TiketsItem tiketsItem = dataSnapshot.getValue(TiketsItem.class);
        Log.e(TAG, "onChildAdded: " + tiketsItem.getNamaCustomer().toString());
        adapterTiketFirebase.notifyDataSetChanged();
        Log.e("onChildAdded", "onChildAdded: " + dataSnapshot.toString());
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
        Log.e(TAG, "onCancelled: " + databaseError.getDetails().toString());
      }
    });

  }*/

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
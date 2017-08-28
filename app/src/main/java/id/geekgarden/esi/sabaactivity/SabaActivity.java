package id.geekgarden.esi.sabaactivity;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;

import java.util.ArrayList;

import id.geekgarden.esi.data.model.tikets.AdapterTiket;
import id.geekgarden.esi.data.model.tikets.AdapterTiketFirebase;
import id.geekgarden.esi.data.model.tikets.FirebaseHolder;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.TiketsItem;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SabaActivity extends AppCompatActivity {
  private ActionBar actionBar;
  private Api mApi;
  private AdapterTiket adapter;
  private FirebaseDatabase mData;
  private DatabaseReference mRef;
  private DatabaseReference ref;
  private ListView mListView;
  private AdapterTiketFirebase mAdapter;
  @BindView(R.id.rcvActSaba)RecyclerView rcvActSaba;
  @OnClick(R.id.fab)void AddActivitySaba(View view){
    Intent i  = new Intent(this,TambahSabaActivity.class);
    startActivity(i);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_saba);
    ButterKnife.bind(this);
    mData = FirebaseDatabase.getInstance();
    mRef =  mData.getReference();
    ref = mRef.child("Tiket").child("ListTiket");
    getdatafromfirebase();
    initActionBar();
    /*showDummyData();*/
    /*initRecycleView();*/
  }

    private void getdatafromfirebase() {

      ChildEventListener  listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
                Log.e("TAG", "onChildAdded: "+ dataSnapshot );
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled: "+ databaseError.getMessage());
            }
        };

          ref.addChildEventListener(listener);

        mAdapter = new AdapterTiketFirebase(TiketsItem.class,R.layout.item_list_tiket, FirebaseHolder.class, ref);
        rcvActSaba.setHasFixedSize(true);
        rcvActSaba.setLayoutManager(new LinearLayoutManager(this));
        rcvActSaba.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcvActSaba.setAdapter(mAdapter);

    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
    }
*/
    private void initRecycleView() {
    /*adapter = new AdapterTiket(getApplicationContext(), new ArrayList<TiketsItem>(0), new AdapterTiket.PostItemListener() {
      @Override
      public void onPostClickLsitener(long id, String status) {
       Intent i = new Intent(getApplicationContext(),DetailSabaActivity.class);
       startActivity(i);
      }
    });*/

  }

 /* private void showDummyData() {
    Observable<ResponseTikets> respon = mApi.getTikets().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(new Observer<ResponseTikets>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(ResponseTikets responseTikets) {
        adapter.UpdateTikets(responseTikets.getTikets());
      }
    });
  }*/

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Activity SABA");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}

package id.geekgarden.esi.listtiket.fragment;


import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.AdapterTiketSample;
import id.geekgarden.esi.data.model.DataAdapter;
import id.geekgarden.esi.data.model.DataAdapter.PostItemListener;
import id.geekgarden.esi.data.model.DataItem;
import id.geekgarden.esi.data.model.ResponseTiketSample;
import id.geekgarden.esi.data.model.ResponseUsers;
import id.geekgarden.esi.data.model.TiketsItem;
import id.geekgarden.esi.listtiket.activity.DetailOpenTiket;
import id.geekgarden.esi.listtiket.activity.OpenTiket;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
  private DataAdapter adapter;
  private AdapterTiketSample adapterTiketSample;
  private Unbinder unbinder;
  private List<DataItem> dataItem;

  public MyTiketFragment() {}
  @BindView(R.id.rcvTiket)RecyclerView rcvTiket;
  @BindView(R.id.fab)FloatingActionButton fab;
  @OnClick(R.id.fab)void OpenNewTiket(View view){
    Intent i = new Intent(getContext(), OpenTiket.class);
    startActivity(i);
  }
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
    adapter = new DataAdapter(getContext(), new ArrayList<DataItem>(0), new PostItemListener() {
      @Override
      public void onPostClickLsitener(long id) {
        OpenDetailTiket();
      }
    });
    adapterTiketSample = new AdapterTiketSample(getContext(), new ArrayList<TiketsItem>(0),
        new AdapterTiketSample.PostItemListener() {
          @Override
          public void onPostClickLsitener(long id) {

          }
        });
    rcvTiket.setHasFixedSize(true);
    rcvTiket.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    rcvTiket.setLayoutManager(new LinearLayoutManager(getContext()));
    rcvTiket.setAdapter(adapterTiketSample);

  }

  private void OpenDetailTiket() {
    Intent i = new Intent(getContext(), DetailOpenTiket.class);
    startActivity(i);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void loadData() {

    /*Observable<ResponseUsers> responseUsersObservable = mApi.getdataUsers().subscribeOn(Schedulers.newThread()).observeOn(
        AndroidSchedulers.mainThread());
    responseUsersObservable.subscribe(new Observer<ResponseUsers>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("onError", "MyTiketFragment" + e.getMessage());
      }

      @Override
      public void onNext(ResponseUsers responseUsers) {
        adapter.UpdateData(responseUsers.getData());
      }
    });*/
    Observable<ResponseTiketSample> responseUsersObservable = mApi.getdataTiketSample().subscribeOn(Schedulers.newThread()).observeOn(
        AndroidSchedulers.mainThread());
    responseUsersObservable.subscribe(new Observer<ResponseTiketSample>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("onError", "MyTiketFragment" + e.getMessage());
      }

      @Override
      public void onNext(ResponseTiketSample responseTiketSample) {
        adapterTiketSample.UpdateData(responseTiketSample.getTikets());
      }
    });
  }

}

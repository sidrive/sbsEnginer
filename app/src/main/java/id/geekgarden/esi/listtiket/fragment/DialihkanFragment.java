package id.geekgarden.esi.listtiket.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;

public class DialihkanFragment extends Fragment {
  private final static  String TAG = DialihkanFragment.class.getSimpleName();
  private static final String KEY = "key";
  private String keyFragment;
  private Api mApi;
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



  }
  private void loadData() {



  }

}

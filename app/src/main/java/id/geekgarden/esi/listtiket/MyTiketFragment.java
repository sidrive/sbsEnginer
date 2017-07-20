package id.geekgarden.esi.listtiket;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

public class MyTiketFragment extends Fragment {
  private static final String KEY = "key";
  private String keyFragment;
  public MyTiketFragment() {}
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      Bundle bundle = this.getArguments();
      if (bundle!=null){
      keyFragment = getArguments().getString(KEY);
      Log.e("MyTiketFragment", "onCreate = " + keyFragment);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v =  inflater.inflate(R.layout.fragment_my_tiket, container, false);
    ButterKnife.bind(this,v);
    tvSample.setText("MyTiketFragment "+keyFragment);
    return v;
  }
  @BindView(R.id.tvSample)TextView tvSample;
}

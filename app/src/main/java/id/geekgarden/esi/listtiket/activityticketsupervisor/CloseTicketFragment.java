package id.geekgarden.esi.listtiket.activityticketsupervisor;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.geekgarden.esi.MainActivity;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.BodyClose;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.listtiket.fragment.MyTiketFragmentSupervisor;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rakasn on 30/01/18.
 */

@SuppressLint("ValidFragment")
public class CloseTicketFragment extends DialogFragment {

  String id;
  @BindView(R.id.inpDescription)
  EditText inpDescription;
  @BindView(R.id.ratingBar)
  RatingBar ratingBar;
  @BindView(R.id.btnSubmit)
  Button btnSubmit;
  private Api mApi;
  private GlobalPreferences glpref;
  private Context mcontext;

  @SuppressLint("ValidFragment")
  public CloseTicketFragment(String id, Api mApi, GlobalPreferences glpref, Context context) {
    this.glpref = glpref;
    this.id = id;
    this.mApi = mApi;
    this.mcontext = context;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_close_ticket, container);
    ButterKnife.bind(this, view);
    ratingBar.setStepSize(0.5f);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    init();
  }

  private void init() {
    Log.e("init", "CloseTicketFragment" + id);
  }

  @OnClick(R.id.btnSubmit)
  public void closeTicket() {
    if (ratingBar.getRating() < 0.5) {
      Utils.showToast(mcontext,"Silahkan Memberi Rating");
      return;
    }
    BodyClose bodyClose = new BodyClose();
    bodyClose.setRating(ratingBar.getRating());
    bodyClose.setComment(inpDescription.getText().toString().trim());
    mApi.closeticket(glpref.read(PrefKey.accessToken, String.class),id,bodyClose)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
        }, error -> {
        });
    getDialog().dismiss();
    getActivity().finish();
  }

  @Override public void dismiss() {
    Intent intent = new Intent();
    getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK,
        intent);
    super.dismiss();
  }
}

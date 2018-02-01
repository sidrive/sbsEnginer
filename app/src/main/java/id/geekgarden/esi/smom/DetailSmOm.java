package id.geekgarden.esi.smom;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.smom.AdapterSmOmDetail;
import id.geekgarden.esi.data.model.smom.detailsmom.Datum;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rakasn on 24/01/18.
 */

public class DetailSmOm extends AppCompatActivity {

  public static final String KEY_URI = "id";
  private ActionBar actionBar;
  private AdapterSmOmDetail adapter;
  private Api mApi;
  private GlobalPreferences glpref;
  private String id;
  String accessToken;
  @BindView(R.id.rcvSmOm)RecyclerView rcvSmOm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sm_om);
    ButterKnife.bind(this);
    mApi = ApiService.getService();
    glpref = new GlobalPreferences(this);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    initActionBar();
    initRecycleview();
    initData();
  }

  private void initData() {
    if (getIntent() != null) {
      id = getIntent().getStringExtra(KEY_URI);
    } else {}
    mApi.getsmomdetail(accessToken,id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapter.UpdateTikets(object.getData());
        }, throwable -> {
          Utils.showToast(this,"Check Your Connection");
        });
  }

  private void initRecycleview() {
    adapter =  new AdapterSmOmDetail(new ArrayList<Datum>(0),this,
        new AdapterSmOmDetail.OnTiketPostItemListener() {
          @Override
          public void onPostClickListener(int id) {
           initDownload(id);
          }
  });
    rcvSmOm.setLayoutManager(new LinearLayoutManager(this));
    rcvSmOm.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    rcvSmOm.setAdapter(adapter);
  }

  private void initDownload(int id) {
    Utils.showProgress(this).show();
    Call<ResponseBody> call = mApi.downloadsmom(accessToken, id);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Utils.dismissProgress();
          Log.d("", "server contacted and has file");
          boolean writtenToDisk = writeResponseBodyToDisk(response.body());
          Utils.showToast(getApplicationContext(), "PDF Success Download wait to open");
          Log.e("onResponse", "DetailEnded" + response.body().byteStream());
          Log.d("", "file download was a success? " + writtenToDisk);
        } else {
          Log.d("", "server contact failed");
        }
      }
      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("", "error");
      }
    });
  }

  private boolean writeResponseBodyToDisk(ResponseBody body) {
    File futureStudioIconFile = new File("/sdcard/smom.pdf");
    Intent intent = new Intent(Intent.ACTION_VIEW);
    try {
      if (Build.VERSION.SDK_INT >= 23) {
        Uri path = Uri.fromFile(futureStudioIconFile);
        Uri realpath = Uri.parse(path.toString().replaceAll("file","content"));
        Log.e("writeResponseBodyToDisk", "DetailEnded" + realpath);
        intent.setDataAndType(realpath, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      } else if (Build.VERSION.SDK_INT < 23) {
        Uri path = Uri.fromFile(futureStudioIconFile);
        Log.e("writeResponseBodyToDisk", "DetailEndedasd" + path);
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      }
      InputStream inputStream = null;
      OutputStream outputStream = null;

      try {
        byte[] fileReader = new byte[4096];

        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;

        inputStream = body.byteStream();
        outputStream = new FileOutputStream(futureStudioIconFile);

        while (true) {
          int read = inputStream.read(fileReader);

          if (read == -1) {
            break;
          }
          outputStream.write(fileReader, 0, read);
          fileSizeDownloaded += read;
        }
        outputStream.flush();
        return true;
      } catch (IOException e) {
        return false;
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }
        if (outputStream != null) {
          outputStream.close();
        }
      }
    } catch (IOException e) {
      return false;
    }
  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail SM & OM ");
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
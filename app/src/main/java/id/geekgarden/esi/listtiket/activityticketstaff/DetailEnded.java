package id.geekgarden.esi.listtiket.activityticketstaff;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.barteksc.pdfviewer.PDFView;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.helper.Utils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEnded extends AppCompatActivity {

  public static final String KEY_URI = "id";
  public static final String KEY_CAT = "category";
  public static final String KEY_TICK = "ticket_type";
  public static final String KEY_CUST = "id_customer";
  public static final String KEY_ACTI = "activity_id";
  public static final String KEY_SNAME = "staff_name";
  public static final String KEY_SPHN = "staff_phonenumber";
  public static final String KEY_INST = "instrument_type";
  public static final String KEY_INS = "instrument";
  public static final String KEY_PRIO = "priority";
  public static final String KEY_NUM = "number";
  public static final String KEY_CUSTN = "customer_name";
  public static final String KEY_CONT = "contract";
  public static final String KEY_DESC = "description";
  public static final String KEY_CIT = "it_category";
  public static final String KEY_IDI = "hardware_id";
  public static final String KEY_IDS = "software_id";
  public static final String KEY_HAR = "hardware";
  public static final String KEY_SOF = "software";
  private static final String TAG = DetailEnded.APPWIDGET_SERVICE;
  @BindView(R.id.pdf)
  Button pdf;
  private String idtiket;
  private String category;
  private String ticket_type;
  private String id_customer;
  private String activity_id;
  private String staff_name;
  private String staff_phonenumber;
  private String instrument_type;
  private String instrument;
  private String priority;
  private String number;
  private String customer_name;
  private String contract;
  private String description;
  private String id_division;
  private String it_category;
  private String hardware_id;
  private String software_id;
  private String hardware;
  private String software;
  String accessToken;
  @BindView(R.id.tvDescTiket)
  TextView tvDescTiket;
  private Api mApi;
  private GlobalPreferences glpref;
  @BindView(R.id.tvnamaanalis)
  TextView tvnamaanalis;
  @BindView(R.id.tvnotelp)
  TextView tvnotelp;
  @BindView(R.id.tvtipealat)
  TextView tvtipealat;
  @BindView(R.id.tvLabelalat)
  TextView tvlabelalat;
  @BindView(R.id.tvurgency)
  TextView tvurgency;
  @BindView(R.id.tvnumber)
  TextView tvnumber;
  @BindView(R.id.tvsnalat)
  TextView tvsnalat;
  @BindView(R.id.tvkategori)
  TextView tvkategori;
  @BindView(R.id.tvstatusalat)
  TextView tvstatusalat;
  public static final String MESSAGE_PROGRESS = "message_progress";
  private static final int PERMISSION_REQUEST_CODE = 1;
  private Builder notificationBuilder;
  private NotificationManager notificationManager;
  private int totalFileSize;
  private ActionBar actionBar;
  private PDFView pdfView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mApi = ApiService.getService();
    setContentView(R.layout.activity_detail_ended);
    ButterKnife.bind(this);
    glpref = new GlobalPreferences(getApplicationContext());
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    id_division = glpref.read(PrefKey.division_id,String.class);
    initActionbar();
    initData();
    initViewData();
  }

  private void initData() {
    if (getIntent() != null) {
      idtiket = getIntent().getStringExtra(KEY_URI);
    } else {}
    if (getIntent() != null) {
      category = getIntent().getStringExtra(KEY_CAT);
    } else {}
    if (getIntent() != null) {
      ticket_type = getIntent().getStringExtra(KEY_TICK);
    } else {}
    if (getIntent() != null) {
      id_customer = getIntent().getStringExtra(KEY_CUST);
    } else {}
    if (getIntent() != null) {
      activity_id = getIntent().getStringExtra(KEY_ACTI);
    } else {}
    if (getIntent() != null) {
      staff_name= getIntent().getStringExtra(KEY_SNAME);
    } else {}
    if (getIntent() != null) {
      staff_phonenumber = getIntent().getStringExtra(KEY_SPHN);
    } else {}
    if (getIntent() != null) {
      instrument_type = getIntent().getStringExtra(KEY_INST);
    } else {}
    if (getIntent() != null) {
      instrument = getIntent().getStringExtra(KEY_INS);
    } else {}
    if (getIntent() != null) {
      priority = getIntent().getStringExtra(KEY_PRIO);
    } else {}
    if (getIntent() != null) {
      number = getIntent().getStringExtra(KEY_NUM);
    } else {}
    if (getIntent() != null) {
      customer_name = getIntent().getStringExtra(KEY_CUSTN);
    } else {}
    if (getIntent() != null) {
      contract = getIntent().getStringExtra(KEY_CONT);
    } else {}
    if (getIntent() != null) {
      description = getIntent().getStringExtra(KEY_DESC);
    } else {}
    if (getIntent() != null) {
      it_category = getIntent().getStringExtra(KEY_CIT);
    } else {}
    if (getIntent() != null) {
      hardware_id = getIntent().getStringExtra(KEY_IDI);
    } else {}
    if (getIntent() != null) {
      software_id = getIntent().getStringExtra(KEY_IDS);
    } else {}
    if (getIntent() != null) {
      hardware = getIntent().getStringExtra(KEY_HAR);
    } else {}
    if (getIntent() != null) {
      software = getIntent().getStringExtra(KEY_SOF);
    } else {}
    Utils.showProgress(this).show();
  }

  private void initViewData() {
    tvnamaanalis.setText(staff_name);
    tvnotelp.setText(staff_phonenumber);
    tvurgency.setText(priority);
    tvnumber.setText(number);
    tvsnalat.setText(instrument);
    tvstatusalat.setText(contract);
    tvDescTiket.setText(description);
    initTypeDeviceDueDivision();
    Utils.dismissProgress();
  }

  private void initTypeDeviceDueDivision(){
    if(id_division.equals("3")){
      tvlabelalat.setText("Device");
      tvtipealat.setText(software+hardware);
    }
    tvtipealat.setText(instrument_type);
    Log.e("division","id_divison"+id_division);
    Log.e("software","software"+software);
    Log.e("hardware","hardware"+hardware);
    Utils.dismissProgress();
  }

  @OnClick(R.id.pdf)
  public void onViewClicked() {
    Utils.showProgress(this).show();
    initDownload();
  }

  private void initDownload() {
    Call<ResponseBody> call = mApi.downloadpdf(accessToken, idtiket);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Utils.dismissProgress();
          Log.d(TAG, "server contacted and has file");
          boolean writtenToDisk = writeResponseBodyToDisk(response.body());
          Utils.showToast(getApplicationContext(), "PDF Success Download wait to open");
          Log.e("onResponse", "DetailEnded" + response.body().byteStream());
          Log.d(TAG, "file download was a success? " + writtenToDisk);
        } else {
          Log.d(TAG, "server contact failed");
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(TAG, "error");
      }
    });
  }

  private boolean writeResponseBodyToDisk(ResponseBody body) {
    try {
      File futureStudioIconFile = new File("/sdcard/ticket.pdf");
      Uri path = Uri.fromFile(futureStudioIconFile);
      Uri realpath = Uri.parse(path.toString().replace("file","content"));
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setDataAndType(realpath, "application/pdf");
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      try {
        startActivity(intent);
      }
      catch (ActivityNotFoundException e) {
        Toast.makeText(this,
            "No Application Available to View PDF",
            Toast.LENGTH_SHORT).show();
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

          Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
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

  private void initActionbar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Detail Ended");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    getSupportFragmentManager().findFragmentByTag("ended");
    finish();
  }
}

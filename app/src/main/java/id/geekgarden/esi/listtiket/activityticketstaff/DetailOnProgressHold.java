package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Datum;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.Part;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.helper.ImagePicker;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailOnProgressHold extends AppCompatActivity {
    private final static int FILECHOOSER_RESULTCODE = 1;
    boolean is_empty = false;
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private Bitmap bitmap;
    private File file = null;
    private DatabaseHandler db;
    @BindView(R.id.rcvreoccurence)
    RecyclerView rcvreoccurence;
    private List<Part> listarray = new ArrayList<Part>();
    @BindView(R.id.tvproblem)
    TextInputEditText tvproblem;
    @BindView(R.id.tvfault)
    TextInputEditText tvfault;
    @BindView(R.id.tvsolution)
    TextInputEditText tvsolution;
    String accessToken;
    String idtiket;
    @BindView(R.id.ckbsparepart)
    CheckBox ckbsparepart;
    @BindView(R.id.tvtraveltime)
    TextView tvtraveltime;
    private Api mApi;
    private GlobalPreferences glpref;
    public static String KEY_URI = "id";
    @BindView(R.id.tvnamaanalis)
    TextView tvnamaanalis;
    @BindView(R.id.tvnohp)
    TextView tvnohp;
    @BindView(R.id.tvtipealat)
    TextView tvtipealat;
    @BindView(R.id.tvurgency)
    TextView tvurgency;
    @BindView(R.id.tvnumber)
    TextView tvnumber;
    @BindView(R.id.tvnamacustomer)
    TextView tvnamacustomer;
    @BindView(R.id.tvcategory)
    TextView tvcategory;
    @BindView(R.id.tvstatusalat)
    TextView tvstatusalat;
    private ActionBar actionBar;
    @BindView(R.id.bntHold)
    Button bntHold;
    @BindView(R.id.btnEnd)
    Button btnEnd;
    @BindView(R.id.btncamera)
    Button btncamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_onprogress_service_hold);
        ButterKnife.bind(this);
        db = new DatabaseHandler(getApplicationContext());
        rcvreoccurence.setVisibility(View.GONE);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        idtiket = getIntent().getStringExtra(KEY_URI);
        initActionBar();
        initviewdata();
    }

    @OnClick(R.id.btncamera)
    void openCamera(View view) {
        getCameraClick();
    }

    @OnClick(R.id.bntHold)
    void holdTiket(View view) {
        onholdclick();
    }

    @OnClick(R.id.btnEnd)
    void endTiket(View view) {
        if (is_empty == true) {
            uploadimage();
            onendclick();
        } else {
            getCameraClick();
        }
    }

    private void initviewdata() {
        Observable<ResponseDetailTiket> responsedetailtiket = mApi
                .detailtiket(accessToken, idtiket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responsedetailtiket.subscribe(responseDetailTiket -> {
            tvnamaanalis.setText(responseDetailTiket.getData().getStaffName());
            tvnohp.setText(responseDetailTiket.getData().getStaffPhoneNumber());
            tvtipealat.setText(responseDetailTiket.getData().getInstrument().getData().getType());
            tvurgency.setText(responseDetailTiket.getData().getPriority());
            tvtraveltime.setText(responseDetailTiket.getData().getTravelTime());
            tvnumber.setText(responseDetailTiket.getData().getNumber());
            tvnamacustomer.setText(responseDetailTiket.getData().getCustomerName());
            tvstatusalat.setText(responseDetailTiket.getData().getInstrument().getData().getContractType());
            tvDescTiket.setText(responseDetailTiket.getData().getDescription());
        }, throwable -> {
        });
    }


    private void onholdclick() {
        for (int i = 0; i < db.getAllSparepart().size(); i++) {
            Part sp = new Part();
            sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
            sp.setDescription(db.getAllSparepart().get(i).getDescription());
            sp.setQuantity(db.getAllSparepart().get(i).getQty());
            sp.setStatus(db.getAllSparepart().get(i).getStatus());
            sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
            listarray.add(sp);
        }
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
        } else {

        }
        BodyOnProgress bodyOnProgress = new BodyOnProgress();
        bodyOnProgress.setProblem(tvproblem.getText().toString());
        bodyOnProgress.setFaultDescription(tvfault.getText().toString());
        bodyOnProgress.setSolution(tvsolution.getText().toString());
        bodyOnProgress.setParts(listarray);
        if (TextUtils.isEmpty(tvproblem.getText().toString())) {
            tvproblem.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        if (TextUtils.isEmpty(tvfault.getText().toString())) {
            tvfault.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        if (TextUtils.isEmpty(tvsolution.getText().toString())) {
            tvsolution.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        Observable<ResponseOnProgress> respononprogress = mApi
                .updateonholdtiket(accessToken, idtiket, bodyOnProgress)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        respononprogress.subscribe(responseOnProgress -> {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            db.deleteAllsparepart(new SQLiteSparepart());
            onBackPressed();
        }, throwable -> {
        });
    }

    private void onendclick() {
        for (int i = 0; i < db.getAllSparepart().size(); i++) {
            Part sp = new Part();
            sp.setPartNumber(db.getAllSparepart().get(i).getPartnumber());
            sp.setDescription(db.getAllSparepart().get(i).getDescription());
            sp.setQuantity(db.getAllSparepart().get(i).getQty());
            sp.setStatus(db.getAllSparepart().get(i).getStatus());
            sp.setRemarks(db.getAllSparepart().get(i).getKeterangan());
            listarray.add(sp);
        }
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
        } else {
        }
        BodyOnProgress bodyOnProgress = new BodyOnProgress();
        bodyOnProgress.setProblem(tvproblem.getText().toString());
        bodyOnProgress.setFaultDescription(tvfault.getText().toString());
        bodyOnProgress.setSolution(tvsolution.getText().toString());
        bodyOnProgress.setParts(listarray);
        Log.e("", "onendclick: " + listarray);
        if (TextUtils.isEmpty(tvproblem.getText().toString())) {
            tvproblem.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        if (TextUtils.isEmpty(tvfault.getText().toString())) {
            tvfault.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        if (TextUtils.isEmpty(tvsolution.getText().toString())) {
            tvsolution.setError("This");
            UiUtils.showToast(getApplicationContext(), "Please Fill Empty Data");
        }
        Observable<ResponseOnProgressEnd> respononprogressend = mApi
                .updateonendtiket(accessToken, idtiket, bodyOnProgress)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        respononprogressend.subscribe(responseOnProgressEnd -> {
            db.deleteAllsparepart(new SQLiteSparepart());
            onBackPressed();
        }, throwable -> {
        });
    }

    private void getCameraClick() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
        startActivityForResult(chooseImageIntent, FILECHOOSER_RESULTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILECHOOSER_RESULTCODE:
                onFileChooserResult(resultCode, data);
                break;
        }
    }

    private void onFileChooserResult(int resultCode, Intent data) {
        data = null;
        if (resultCode == RESULT_OK) {
            bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
            ImageView view = findViewById(R.id.imgcapture);
            file = ImagePicker.getTempFile(this);
            view.setImageBitmap(bitmap);
            is_empty = true;
            btnEnd.setBackgroundResource(R.color.colorPrimary);
        } else {
            is_empty = false;
            btnEnd.setBackgroundResource(R.color.colorGray);
        }
    }

    private void uploadimage() {
        MultipartBody.Part imageBody = null;
        if (file != null) {
            RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
            imageBody = MultipartBody.Part.createFormData("image", file.getName(), image);
        }
        Observable<RequestBody> requestBodyImage = mApi
                .updateimage(accessToken, idtiket, imageBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        requestBodyImage.subscribe(requestBody -> {

        }, throwable -> {
        });
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("On Progress Hold");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.bntHold, R.id.btnEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bntHold:
                break;
            case R.id.btnEnd:
                break;
        }
    }

    @OnClick(R.id.ckbsparepart)
    public void onViewClicked() {
        boolean checked = ckbsparepart.isChecked();
        switch (ckbsparepart.getId()) {
            case R.id.ckbsparepart:
                if (checked) {
                    Intent i = new Intent(getApplicationContext(), Sparepart.class);
                    startActivity(i);
                } else {

                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().findFragmentByTag("progres hold");
        finish();
    }
}
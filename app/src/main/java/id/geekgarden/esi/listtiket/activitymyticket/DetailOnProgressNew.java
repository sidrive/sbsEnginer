package id.geekgarden.esi.listtiket.activitymyticket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import id.geekgarden.esi.data.model.reocurrence.ResponseReocurrence;
import id.geekgarden.esi.data.model.tikets.AdapterReocurrence;
import id.geekgarden.esi.data.model.tikets.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.Selectable;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Datum;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.relatedticket.ResponseRelatedTicket;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.Part;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.hold.ResponseOnProgress;
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

public class DetailOnProgressNew extends AppCompatActivity implements OnItemSelectedListener,
        Selectable {
    private final static int FILECHOOSER_RESULTCODE = 1;
    boolean is_empty = false;
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private Bitmap bitmap;
    private File file = null;
    private DatabaseHandler db;
    @BindView(R.id.rcvreoccurence)
    RecyclerView rcvreoccurence;
    @BindView(R.id.tvnodata)
    TextView tvnodata;
    @BindView(R.id.btncamera)
    Button btncamera;
    private List<Part> listarray = new ArrayList<Part>();
    @BindView(R.id.tvproblem)
    TextInputEditText tvproblem;
    @BindView(R.id.tvfault)
    TextInputEditText tvfault;
    @BindView(R.id.tvsolution)
    TextInputEditText tvsolution;
    private AdapterSpinnerOnProgress adapterSpinnerOnProgress;
    private AdapterReocurrence adapterReocurrence;
    String itemnumber;
    String accessToken;
    String idtiket;
    String TicketRelationID;
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

    @OnClick(R.id.btncamera)
    void openCamera(View view) {
        if (is_empty == true) {
            onendclick();
        } else {
            getCameraClick();
        }
    }

    @OnClick(R.id.bntHold)
    void holdTiket(View view) {
        onholdclick();
        if (itemnumber.equals("2")) {
            addrelationoccurence();
        }
    }

    @OnClick(R.id.btnEnd)
    void endTiket(View view) {
        if (is_empty == true) {
            uploadimage();
            onendclick();
        } else {
            getCameraClick();
        }
        if (itemnumber.equals("2")) {
            addrelationoccurence();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ApiService.getervice();
        setContentView(R.layout.activity_onprogress_service_report);
        ButterKnife.bind(this);
        glpref = new GlobalPreferences(getApplicationContext());
        accessToken = glpref.read(PrefKey.accessToken, String.class);
        db = new DatabaseHandler(this);
        idtiket = getIntent().getStringExtra(KEY_URI);
        initrecycleview();
        initActionBar();
        initSpinner();
        initViewData();
        getdataspinner();

    }

    private void initViewData() {
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

    private void initrecycleview() {
        rcvreoccurence.setHasFixedSize(true);
        rcvreoccurence.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rcvreoccurence.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
            file = ImagePicker.getTempFile(this);
            ImageView view = findViewById(R.id.imgcapture);
            view.setImageBitmap(bitmap);
            is_empty = true;
            btnEnd.setBackgroundResource(R.color.colorPrimary);
        } else {
            is_empty = false;
            btnEnd.setBackgroundResource(R.color.colorGray);
        }
    }

    private void uploadimage() {
        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }
        Observable<RequestBody> requestBodyImage = mApi
                .updateimage(accessToken, idtiket, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        requestBodyImage.subscribe(requestBody -> {
        }, throwable -> {
        });
    }

    private void showreoccurence() {
        Observable<ResponseReocurrence> responseReocurrence = mApi
                .getreocurrence(accessToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseReocurrence.subscribe(responseReocurrence1 -> {
            if (responseReocurrence1.getData() != null) {
                tvnodata.setVisibility(View.GONE);
            } else {
                tvnodata.setVisibility(View.VISIBLE);
            }
            adapterReocurrence.UpdateTikets(responseReocurrence1.getData());
        }, throwable -> {
        });
        adapterReocurrence = new AdapterReocurrence(
                new ArrayList<id.geekgarden.esi.data.model.reocurrence.Datum>(0), getApplicationContext(),
                id -> {
                    Log.e("onPostClickListener", "DetailOnProgressNew" + id);
                    TicketRelationID = String.valueOf(id);
                    glpref.write(PrefKey.related_ticket_id, TicketRelationID, String.class);
                }, this);
        rcvreoccurence.setAdapter(adapterReocurrence);
    }

    private void getdataspinner() {
        Observable<Responsespinneronprogress> responspinneronprogress = mApi
                .getSpinneronprogress(accessToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responspinneronprogress.subscribe(responsespinneronprogress ->
                        adapterSpinnerOnProgress.UpdateOption(responsespinneronprogress.getData())
                , throwable -> {
                });
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinnerdata);
        spinner.setOnItemSelectedListener(this);
        adapterSpinnerOnProgress = new AdapterSpinnerOnProgress(this, android.R.layout.simple_spinner_item, new ArrayList<Datum>());
        adapterSpinnerOnProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerOnProgress);
    }

    private void addrelationoccurence() {
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
            Log.e("", "onclickdataupdate: " + idtiket);
        } else {
            Log.e("", "null: ");
        }
        String relationticket = glpref.read(PrefKey.related_ticket_id, String.class);
        Log.e("addrelationoccurence",
                "DetailOnProgressNew" + glpref.read(PrefKey.related_ticket_id, String.class));
        Observable<ResponseRelatedTicket> responseRelatedTicket = mApi
                .putrelatedticket(accessToken, idtiket, relationticket)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        responseRelatedTicket.subscribe(responseRelatedTicket1 -> {
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
        if (getIntent() != null) {
            idtiket = getIntent().getStringExtra(KEY_URI);
        } else {

        }
        BodyOnProgress bodyOnProgress = new BodyOnProgress();
        bodyOnProgress.setTicketActivityId(itemnumber);
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
                    db.deleteAllsparepart(new SQLiteSparepart());
                    onBackPressed();
                }
                , throwable -> {
                });
    }

    private void onendclick() {
        DatabaseHandler db = new DatabaseHandler(this);
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
        bodyOnProgress.setTicketActivityId(itemnumber);
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


    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("On Progress New");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Datum selecteditem = (Datum) adapterView.getItemAtPosition(i);
        Log.e("onItemSelected", "DetailSpinner" + selecteditem.getId());
        itemnumber = selecteditem.getId().toString();
        if (itemnumber.equals("2")) {
            showreoccurence();
            rcvreoccurence.setVisibility(View.VISIBLE);
        } else {
            rcvreoccurence.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        getSupportFragmentManager().findFragmentByTag("progres new");
        finish();
    }

    @Override
    public void ChangeBackground(LinearLayout ll, int position, int selected_position) {
        Log.e("s", "ChangeBackground: " + position);
        Log.e("s", "ChangeBackground: " + selected_position);
        ll.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void ChangeBackgroundTransparent(LinearLayout ll, int position, int selected_position) {
        ll.setBackgroundColor(Color.TRANSPARENT);
    }
}

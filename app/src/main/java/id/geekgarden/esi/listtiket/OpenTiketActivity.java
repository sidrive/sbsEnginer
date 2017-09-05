package id.geekgarden.esi.listtiket;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.FCM.Data;
import id.geekgarden.esi.data.model.engginer.AdapterSpinnerEngginer;
import id.geekgarden.esi.data.model.engginer.EngginerItem;
import id.geekgarden.esi.data.model.engginer.ResponseEngginer;
import id.geekgarden.esi.data.model.kode_kegiatan.AdapterSpinnerKegiatan;
import id.geekgarden.esi.data.model.kode_kegiatan.KodeKegiatanItem;
import id.geekgarden.esi.data.model.kode_kegiatan.ResponseKodeKegiatan;
import id.geekgarden.esi.data.model.prioritys.AdapterSpinnerPriority;
import id.geekgarden.esi.data.model.prioritys.PrioritysItem;
import id.geekgarden.esi.data.model.prioritys.ResponsePrioritys;
import id.geekgarden.esi.data.model.projects.AdapterSpinnerProjects;
import id.geekgarden.esi.data.model.projects.ProjectsItem;
import id.geekgarden.esi.data.model.projects.ResponseProjects;
import id.geekgarden.esi.data.model.shi.AdapterSpinnerShi;
import id.geekgarden.esi.data.model.shi.ResponseShi;
import id.geekgarden.esi.data.model.shi.ShiItem;
import id.geekgarden.esi.data.model.sn_alat.AdapterSpinnerSnAlat;
import id.geekgarden.esi.data.model.sn_alat.ResponseSnAlat;
import id.geekgarden.esi.data.model.sn_alat.SnAlatItem;
/*import id.geekgarden.esi.data.model.tikets.AdapterSpinnerCustomer;*/
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenTiketActivity extends AppCompatActivity{
    private final static String TAG = OpenTiketActivity.class.getSimpleName();
    public final static String KEY ="key";
    private ActionBar actionBar;
    private Api mApi;
    private String key;
    private AdapterSpinnerEngginer adapterSpinnerEngginer;
    private AdapterSpinnerKegiatan adapterSpinnerKegiatan;
    private AdapterSpinnerPriority adapterSpinnerPriority;
    private AdapterSpinnerProjects adapterSpinnerProjects;
    private AdapterSpinnerSnAlat adapterSpinnerSnAlat;
    private AdapterSpinnerShi adapterSpinnerShi;
    /*private AdapterSpinnerCustomer adapterSpinnerCustomer;*/
    private List<Data> tiketsItems;
    private List<ShiItem> shiItems;
    private List<EngginerItem> engginerItems;
    private List<SnAlatItem> snAlatItems;
    private List<KodeKegiatanItem> kodeKegiatanItems;
    private List<PrioritysItem> prioritysItems;
    private List<ProjectsItem> projectsItems;
    private GlobalPreferences glpref;
    @BindView(R.id.spnEngginer)Spinner spnEngginer;
    @BindView(R.id.spnCustomer)Spinner spnCustomer;
    @BindView(R.id.spnSnAlat)Spinner spnSnAlat;
    @BindView(R.id.spnKegiatan)Spinner spnKegiatan;
    @BindView(R.id.spnPriority)Spinner spnPriority;
    @BindView(R.id.spnProjects)Spinner spnProjects;
    @BindView(R.id.spnShi)Spinner spnShi;
    @BindView(R.id.lytSpnSnAlat)TextInputLayout lytSpnSnAlat;
    @BindView(R.id.lytSpnShi)TextInputLayout lytSpnShi;
    @OnClick(R.id.btnOpenTiket) void OpenTiket(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_tiket);
        ButterKnife.bind(this);
        key = getIntent().getStringExtra(KEY);
        mApi = ApiService.getervice();
        glpref = new GlobalPreferences(getApplicationContext());
        initActionbar();
        if (key!=null){
            if (key.equals("IT")){
                lytSpnSnAlat.setVisibility(View.GONE);
                lytSpnShi.setVisibility(View.VISIBLE);
                initSpinnerShi();
            }else {
                lytSpnSnAlat.setVisibility(View.VISIBLE);
                lytSpnShi.setVisibility(View.GONE);
                initSpinnerSn();
            }
        }else {
            Toast.makeText(this,"Key is Null",Toast.LENGTH_LONG).show();
        }
        /*initSpinnerCustomer();*/
        //initSpinnerEngginer();
        //initSpinnerKegiatan();
        //initSpinnerProjects();
        //initSpinnerPriority();
    }

    private void initSpinnerShi() {
        Observable<ResponseShi> respons = mApi.getSHI().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponseShi>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseShi responseShi) {
                shiItems = new ArrayList<>(0);
                shiItems.addAll(responseShi.getShi());
                adapterSpinnerShi =  new AdapterSpinnerShi(getApplicationContext(),R.layout.item_spinner,shiItems);
                spnShi.setAdapter(adapterSpinnerShi);

            }
        });
    }

    private void initSpinnerPriority() {
        Observable<ResponsePrioritys> respons = mApi.getPriority().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponsePrioritys>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponsePrioritys responsePrioritys) {
                prioritysItems = new ArrayList<>(0);
                prioritysItems.addAll(responsePrioritys.getPrioritys());
                adapterSpinnerPriority = new AdapterSpinnerPriority(getApplicationContext(),R.layout.item_spinner,prioritysItems);
                spnPriority.setAdapter(adapterSpinnerPriority);
            }
        });
    }

    private void initSpinnerProjects() {
        final Observable<ResponseProjects> respons = mApi.getProjects().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponseProjects>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseProjects responseProjects) {
                projectsItems = new ArrayList<>(0);
                projectsItems.addAll(responseProjects.getProjects());
                adapterSpinnerProjects = new AdapterSpinnerProjects(getApplicationContext(),R.layout.item_spinner,projectsItems);
                spnProjects.setAdapter(adapterSpinnerProjects);

            }
        });
    }

    private void initSpinnerKegiatan() {
        Observable<ResponseKodeKegiatan> respons = mApi.getKodeKegiatan().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponseKodeKegiatan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseKodeKegiatan responseKodeKegiatan) {
                kodeKegiatanItems = new ArrayList<>(0);
                kodeKegiatanItems.addAll(responseKodeKegiatan.getKodeKegiatan());
                adapterSpinnerKegiatan = new AdapterSpinnerKegiatan(getApplicationContext(),R.layout.item_spinner,kodeKegiatanItems);
                spnKegiatan.setAdapter(adapterSpinnerKegiatan);

            }
        });

    }

    private void initSpinnerSn() {
        Observable<ResponseSnAlat> respons = mApi.getSnAlat().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponseSnAlat>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseSnAlat responseSnAlat) {
                snAlatItems = new ArrayList<>(0);
                snAlatItems.addAll(responseSnAlat.getSnAlat());
                adapterSpinnerSnAlat = new AdapterSpinnerSnAlat(getApplicationContext(),R.layout.item_spinner, snAlatItems);
                spnSnAlat.setAdapter(adapterSpinnerSnAlat);
            }
        });

    }

    /*private void initSpinnerCustomer() {
        final String accesstoken = glpref.read(PrefKey.accessToken, String.class);
        final Observable<ResponseTikets> respons = mApi.getTikets(accesstoken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respons.subscribe(new Observer<ResponseTikets>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseTikets responseTikets) {
                spnCustomer = new Spinner(null);
                adapterSpinnerCustomer = new AdapterSpinnerCustomer(getApplicationContext(),R.layout.item_spinner,tiketsItems);
                spnCustomer.setAdapter(adapterSpinnerCustomer);
            }
        });

    }*/

    private void initSpinnerEngginer() {
        Observable<ResponseEngginer> responseEngginerObservable = mApi.getEgginer().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseEngginerObservable.subscribe(new Observer<ResponseEngginer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseEngginer responseEngginer) {
                engginerItems = new ArrayList<>(0);
                engginerItems.addAll(responseEngginer.getEngginer());
                adapterSpinnerEngginer = new AdapterSpinnerEngginer(getApplicationContext(),R.layout.item_spinner,engginerItems);
                spnEngginer.setAdapter(adapterSpinnerEngginer);
            }
        });

    }



    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Open Tiket "+key);
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

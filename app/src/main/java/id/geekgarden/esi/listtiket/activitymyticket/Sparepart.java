package id.geekgarden.esi.listtiket.activitymyticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.model.tikets.AdapterSparepart;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;

public class Sparepart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<SQLiteSparepart> listarray = new ArrayList<SQLiteSparepart>();
    @BindView(R.id.rcvsparepart)
    RecyclerView rcvsparepart;
    private AdapterSparepart adapterSparepart;
    @BindView(R.id.btnDone)
    Button btnDone;
    private ActionBar actionBar;

    @OnClick(R.id.btnDone)
    void actionDone(View view) {
        onBackPressed();
    }

    @OnClick(R.id.fabaddsparepart)
    void addSparepart() {
        Intent i = new Intent(this, AddSparepart.class);
        startActivity(i);
        finish();
    }

    private static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart);
        ButterKnife.bind(this);
        initActionbar();
        showdatafromSQLite();
        adapterSparepart = new AdapterSparepart(listarray, getApplicationContext(), partnumber -> {
            Intent i = new Intent(getApplicationContext(), DetailSparepart.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(DetailSparepart.KEY_PN, partnumber);
            startActivity(i);
            finish();
        });
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setTitle("Loading....");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        adapterSparepart.notifyDataSetChanged();
        rcvsparepart.setHasFixedSize(true);
        rcvsparepart.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rcvsparepart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcvsparepart.setAdapter(adapterSparepart);
        swipeRefresh.setOnRefreshListener(this);
    }

    private void showdatafromSQLite() {
        DatabaseHandler db = new DatabaseHandler(this);
        Log.e("Retrive Data", "showdatafromSQLite: " + db.getAllSparepart().size());
        if (db.getAllSparepart().size() == 0) {
            Intent i = new Intent(getApplicationContext(), AddSparepart.class);
            startActivity(i);
            finish();
        } else {
            for (int i = 0; i < db.getAllSparepart().size(); i++) {
                SQLiteSparepart sp = new SQLiteSparepart();
                sp.setPartnumber(db.getAllSparepart().get(i).getPartnumber());
                sp.setDescription(db.getAllSparepart().get(i).getDescription());
                sp.setQty(db.getAllSparepart().get(i).getQty());
                sp.setStatus(db.getAllSparepart().get(i).getStatus());
                sp.setKeterangan(db.getAllSparepart().get(i).getKeterangan());
                listarray.add(sp);
            }
            if (listarray.size() != 0) {
                rcvsparepart.setVisibility(View.VISIBLE);
                swipeRefresh.setRefreshing(false);
            } else {
                rcvsparepart.setVisibility(View.GONE);
            }
        }
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Tambah Sparepart");
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
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        showdatafromSQLite();
    }
}

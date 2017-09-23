package id.geekgarden.esi.listtiket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.model.tikets.AdapterListProjects;
import id.geekgarden.esi.data.model.tikets.AdapterSparepart;
import id.geekgarden.esi.data.model.tikets.AdapterSpinnerOnProgress;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;

public class Sparepart extends AppCompatActivity {
    private AdapterSparepart adapterSparepart;
    @BindView(R.id.lvPart)
    ListView lvPart;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    @BindView(R.id.btnDone)
    Button btnDone;
    private ActionBar actionBar;
    @OnClick(R.id.btnDone)
    void actionDone(View view) {

    }
    @OnClick(R.id.fabaddsparepart)

    void addSparepart() {
        Intent i  = new Intent(this,AddSparepart.class);
        startActivity(i);
    }
    String partnumber;
    String description;
    String qty;
    String status;
    String keterangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart);
        ButterKnife.bind(this);
        initActionbar();
        showdatafromSQLite();
    }

    private void showdatafromSQLite() {
        DatabaseHandler db = new DatabaseHandler(this);
        Log.e("Retrive Data :", "showdatafromSQLite: "+db.getAllSparepart());

        if (db.getAllSparepart().isEmpty()){
            Intent i = new Intent(getApplicationContext(),AddSparepart.class);
            startActivity(i);
        }else{
            List<SQLiteSparepart> list = db.getAllSparepart();
            Log.e("retrive data", "showdatafromSQLite: "+list.toString() );
            adapterSparepart.UpdateTikets(list);
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
}

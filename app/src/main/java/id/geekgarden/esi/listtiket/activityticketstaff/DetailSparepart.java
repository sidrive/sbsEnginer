package id.geekgarden.esi.listtiket.activityticketstaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.model.tikets.staffticket.SQLiteSparepart;

public class DetailSparepart extends AppCompatActivity {
    public static final String KEY_PN = "partnumber";
    String sparepart;
    String partnumber;
    String description;
    String qty;
    String status;
    String keterangan;
    private DatabaseHandler db;
    @BindView(R.id.tvpartnumber)
    EditText tvpartnumber;
    @BindView(R.id.tvqty)
    EditText tvqty;
    @BindView(R.id.tvdesc)
    EditText tvdesc;
    @BindView(R.id.tvstatus)
    EditText tvstatus;
    @BindView(R.id.tvketerangan)
    EditText tvketerangan;
    @BindView(R.id.btnDel)
    Button btnDel;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sparepart);
        ButterKnife.bind(this);
        db = new DatabaseHandler(getApplicationContext());
        sparepart = getIntent().getStringExtra(KEY_PN);
        initActionbar();
        initdatasqlite();
    }
    @OnClick(R.id.btnAdd)
    void updateSparepart(View view) {
        updatedatasqlite();
    }

    @OnClick(R.id.btnDel)
    void deleteSparepart(View view) {
        deletedatasqlite();
    }

    private void deletedatasqlite() {
        db.deleteSparepart(new SQLiteSparepart(partnumber,description,qty,status,keterangan), sparepart);
        Intent i = new Intent(getApplicationContext(),Sparepart.class);
        startActivity(i);
        finish();
    }

    private void initdatasqlite() {
        Log.e("", "initdatasqlite: "+sparepart );
        SQLiteSparepart getdata = db.getSparepart(sparepart);
        tvpartnumber.setText(getdata.getPartnumber());
        tvdesc.setText(getdata.getDescription());
        tvqty.setText(getdata.getQty());
        tvstatus.setText(getdata.getStatus());
        tvketerangan.setText(getdata.getKeterangan());
    }

    private void updatedatasqlite() {
        this.partnumber = tvpartnumber.getText().toString();
        this.description = tvdesc.getText().toString();
        this.qty = tvqty.getText().toString();
        this.status = tvstatus.getText().toString();
        this.keterangan = tvketerangan.getText().toString();
        db.updateSparepart(new SQLiteSparepart(partnumber,description,qty,status,keterangan), sparepart);
        Log.e("", "updatedatasqlite: "+db.updateSparepart(new SQLiteSparepart(partnumber,description,qty,status,keterangan), sparepart));
        Intent i = new Intent(getApplicationContext(),Sparepart.class);
        startActivity(i);
        finish();
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Sparepart");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnDel, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDel:
                break;
            case R.id.btnAdd:
                break;
        }
    }
}

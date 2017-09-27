package id.geekgarden.esi.listtiket.activitymyticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;
import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;

import static id.geekgarden.esi.listtiket.activitymyticket.DetailOnHold.TAG;

/**
 * Created by komuri on 20/09/2017.
 */

public class AddSparepart extends AppCompatActivity {
    private ActionBar actionBar;
    public AddSparepart(){

    }
    @BindView(R.id.tvpartnumber)
    EditText tvpartnumber;
    @BindView(R.id.tvdesc)
    EditText tvdesc;
    @BindView(R.id.tvqty)
    EditText tvqty;
    @BindView(R.id.tvstatus)
    EditText tvstatus;
    @BindView(R.id.tvketerangan)
    EditText tvketerangan;
    @BindView(R.id.btnStart)
    Button btnStart;

    String partnumber;
    String description;
    String qty;
    String status;
    String keterangan;

    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sparepart);
        ButterKnife.bind(this);
        initActionBar();
    }

    @OnClick(R.id.btnStart)
    public void saveIntoSQLite() {
        this.partnumber = tvpartnumber.getText().toString();
        this.description = tvdesc.getText().toString();
        this.qty = tvqty.getText().toString();
        this.status = tvstatus.getText().toString();
        this.keterangan = tvketerangan.getText().toString();
        db.addSparepart(new SQLiteSparepart(partnumber,description,qty,status,keterangan));
        Log.e(TAG, "saveIntoSQLite: "+partnumber);
        Intent i = new Intent(getApplicationContext(),Sparepart.class);
        startActivity(i);
        finish();
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Tiket Confirm");
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
        finish();
    }

}




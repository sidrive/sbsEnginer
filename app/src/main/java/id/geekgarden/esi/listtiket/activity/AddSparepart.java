package id.geekgarden.esi.listtiket.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.DatabaseHandler;

/**
 * Created by komuri on 20/09/2017.
 */

public class AddSparepart extends Activity {
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
    }


    public AddSparepart(String partnumber, String description, String qty, String status, String keterangan) {
        this.partnumber = tvpartnumber.getText().toString();
        this.description = tvdesc.getText().toString();
        this.qty = tvqty.getText().toString();
        this.status = tvstatus.getText().toString();
        this.keterangan = tvketerangan.getText().toString();
    }

    public String getPartnumber(){
        return this.partnumber;
    }

    // setting id
    public void setPartnumber(String partnumber){
        this.partnumber = partnumber;
    }

    // getting name
    public String getDescription(){
        return this.description;
    }

    // setting name
    public void setDescription(String description){
        this.description = description;
    }

    // getting phone number
    public String getQty(){
        return this.qty;
    }

    // setting phone number
    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan(){
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @OnClick(R.id.btnStart)
    public void saveIntoSQLite() {
        db.addSparepart(new AddSparepart(partnumber,description,qty,status,keterangan));
    }
}



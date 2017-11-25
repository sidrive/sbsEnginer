package id.geekgarden.esi.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.tvemail)
    TextView tvemail;
    @BindView(R.id.tvnama)
    TextView tvnama;
    @BindView(R.id.tvnotelp)
    TextView tvnotelp;
    @BindView(R.id.tvdivisi)
    TextView tvdivisi;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initActionbar();
        GlobalPreferences glpref = new GlobalPreferences(getApplicationContext());
        String email = glpref.read(PrefKey.email,String.class);
        String nama = glpref.read(PrefKey.full_name,String.class);
        String notelp = glpref.read(PrefKey.phone_number,String.class);
        String divisi = glpref.read(PrefKey.userType,String.class);

        tvemail.setText(email);
        tvnama.setText(nama);
        tvnotelp.setText(notelp);
        tvdivisi.setText(divisi);

    }


    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Profile");
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

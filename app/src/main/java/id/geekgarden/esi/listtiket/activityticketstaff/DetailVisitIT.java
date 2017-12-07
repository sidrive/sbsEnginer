package id.geekgarden.esi.listtiket.activityticketstaff;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

public class DetailVisitIT extends AppCompatActivity {
    @BindView(R.id.tvDescTiket)
    TextView tvDescTiket;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_on_progresvisit_it);
        ButterKnife.bind(this);

        initActionbar();
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Dialihkan");
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
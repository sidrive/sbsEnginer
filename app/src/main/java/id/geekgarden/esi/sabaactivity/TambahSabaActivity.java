package id.geekgarden.esi.sabaactivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.ModelSabaActivity;

public class TambahSabaActivity extends AppCompatActivity {
  private ActionBar actionBar;
  @BindView(R.id.etSbActivity)
  EditText etSbActivity;

  @OnClick(R.id.btnStart)void AddActivity(View view){
    String act = etSbActivity.getText().toString();
    FirebaseDatabase mD = FirebaseDatabase.getInstance();
    DatabaseReference mSabaActRef = mD.getReference().child("SabaActivity");

    ModelSabaActivity model = new ModelSabaActivity();
    model.setActivity(act);
    mSabaActRef.push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        finish();
      }
    });

  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tambah_saba);
    ButterKnife.bind(this);
    initActionBar();

  }

  private void showDetailDummyData() {

  }

  private void initActionBar() {
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setTitle("Tambah Activity");
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

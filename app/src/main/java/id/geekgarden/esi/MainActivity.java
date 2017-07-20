package id.geekgarden.esi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }
  @OnClick(R.id.btnListTiket) void openListTiket(View view){
    Intent i = new Intent(this,ListTiketActivity.class);
    startActivity(i);
  }
  @OnClick(R.id.btnActivitySaba) void openActivitySaba(View view){

  }
  @OnClick(R.id.btnSmOm) void openSmOm(View view){

  }
  @OnClick(R.id.btnListProject) void openListProject(View view){

  }
  @OnClick(R.id.btnProfile) void openProfile(View view){

  }
  @OnClick(R.id.btnLogout) void goLogout(View view){

  }
}

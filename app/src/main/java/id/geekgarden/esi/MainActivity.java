package id.geekgarden.esi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.geekgarden.esi.listprojects.ListProjects;
import id.geekgarden.esi.listtiket.ListTiketActivity;
import id.geekgarden.esi.sabaactivity.SabaActivity;
import id.geekgarden.esi.smom.SmOmActivity;

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
    Intent i = new Intent(this,SabaActivity.class);
    startActivity(i);
  }
  @OnClick(R.id.btnSmOm) void openSmOm(View view){
    Intent i = new Intent(this,SmOmActivity.class);
    startActivity(i);
  }
  @OnClick(R.id.btnListProject) void openListProject(View view){
    Intent i = new Intent(this,ListProjects.class);
    startActivity(i);
  }
  @OnClick(R.id.btnProfile) void openProfile(View view){
    Intent i = new Intent(this,ListTiketActivity.class);
    startActivity(i);
  }
  @OnClick(R.id.btnLogout) void goLogout(View view){
    Intent i = new Intent(this,ListTiketActivity.class);
    startActivity(i);
  }
}

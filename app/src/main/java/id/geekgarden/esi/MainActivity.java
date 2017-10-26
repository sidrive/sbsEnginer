package id.geekgarden.esi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.data.model.User.ResponseUser;
import id.geekgarden.esi.helper.DialogListener;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.listprojects.ListProjects;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.login.LoginActivity;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import id.geekgarden.esi.profile.ProfileActivity;
import id.geekgarden.esi.sabaactivity.SabaActivity;
/*import id.geekgarden.esi.smom.SmOmActivity;*/
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements DialogListener {
  private FirebaseAnalytics mFirebaseAnalytics;
  private Api mApi;
  private CompositeSubscription subscription;
  private GlobalPreferences glpref;
  private String[] permission = new String[]{android.Manifest.permission.CAMERA,
      android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
      android.Manifest.permission.CALL_PHONE};
  private static final int PERMISSION_CALLBACK_CONSTANT = 100;
  String accessToken;
  String refreshtoken;
  String fcm_token;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    ButterKnife.bind(this);
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(getApplicationContext());
    fcm_token = FirebaseInstanceId.getInstance().getToken();
    refreshtoken = glpref.read(PrefKey.refreshToken,String.class);
    accessToken = glpref.read(PrefKey.accessToken, String.class);
    if (refreshtoken!=null){
      if (refreshtoken.equals(fcm_token)){
        sendTokenToServer(refreshtoken);
      }else{
        sendTokenToServer(fcm_token);
      }
    }else{
      sendTokenToServer(fcm_token);
    }
    Log.e("onCreate", "MainActivity " + fcm_token);
    subscription = new CompositeSubscription();
    GetDataUser();
    requestPermisisonAll();
  }

  private void requestPermisisonAll() {
    if (ActivityCompat.checkSelfPermission(this, permission[0]) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, permission[1])
        != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, permission[2])
        != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[0])
          || ActivityCompat.shouldShowRequestPermissionRationale(this, permission[1])
          || ActivityCompat.shouldShowRequestPermissionRationale(this, permission[2])
          || ActivityCompat.shouldShowRequestPermissionRationale(this, permission[3])) {
      } else {
        showDialog(this, this, permission);
      }
    }
  }

  private AlertDialog showDialog(Context context, DialogListener listener, String[] permission) {
    Builder builder = new Builder(context);
    builder.setTitle("Request Permission");
    builder.setMessage("Permission Camera, Storage, Phone");
    builder.setPositiveButton("GRANT", (dialogInterface, i) -> {
      listener.dialogPositive(dialogInterface, permission);
    });
    builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
      listener.dialogNegative(dialogInterface);
    });
    return builder.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
      boolean allGrant = false;
      for (int i = 0; i < grantResults.length; i++) {
        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          allGrant = true;
        } else {
          allGrant = false;
        }
      }
      if (allGrant) {
        UiUtils.showToast(this, "ALL GRANTED");
      } else {
        requestPermisisonAll();
      }
    }
  }


  private void GetDataUser() {
    Observable<ResponseUser> userrespon = mApi
        .GetUserData(accessToken)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    userrespon.subscribe(responseUser -> {
      String Id = responseUser.getData().getId().toString();
      String Id_Employee = responseUser.getData().getEmployeeId().toString();
      String email = responseUser.getData().getEmail();
      String name = responseUser.getData().getName();
      String phone_number = responseUser.getData().getPhoneNumber();
      String user_type = responseUser.getData().getType();
      String is_supervisor = responseUser.getData().getPositionName();
      glpref.write(PrefKey.position_name,is_supervisor, String.class);
      glpref.write(PrefKey.id, Id,String.class);
      glpref.write(PrefKey.id_employee_, Id_Employee,String.class);
      glpref.write(PrefKey.email, email, String.class);
      glpref.write(PrefKey.full_name, name, String.class);
      glpref.write(PrefKey.phone_number, phone_number, String.class);
      glpref.write(PrefKey.userType,user_type,String.class);
    },throwable -> {});
  }

  private void sendTokenToServer(final String token) {
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    BodyFCM bodyFCM = new BodyFCM();
    bodyFCM.setFcmToken(token);
    rx.Observable<ResponseFCM> respon = mApi.updateFcmToken(accessToken,bodyFCM).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(responseFCM -> {},throwable -> {});
  }

  @OnClick(R.id.btnListTiket) void openListTiket(View view){
    Intent i = new Intent(this,ListTiket.class);
    startActivity(i);
  }
  @OnClick(R.id.btnActivitySaba) void openActivitySaba(View view){
    Intent i = new Intent(this,SabaActivity.class);
    startActivity(i);
  }
  /*@OnClick(R.id.btnSmOm) void openSmOm(View view){
    Intent i = new Intent(this,SmOmActivity.class);
    startActivity(i);
  }*/
  @OnClick(R.id.btnListProject) void openListProject(View view){
    Intent i = new Intent(this,ListProjects.class);
    startActivity(i);
  }
  @OnClick(R.id.btnProfile) void openProfile(View view){
    Intent i = new Intent(this,ProfileActivity.class);
    startActivity(i);
  }
  @OnClick(R.id.btnLogout) void goLogout(View view){
    Intent i = new Intent(this,LoginActivity.class);
    GlobalPreferences GlPref = new GlobalPreferences(getApplicationContext());
    GlPref.clear();
    finish();
    startActivity(i);
  }

  @Override
  public void dialogPositive(DialogInterface dialogInterface, String[] permission) {
    dialogInterface.dismiss();
    ActivityCompat.requestPermissions(this, permission, PERMISSION_CALLBACK_CONSTANT);
  }

  @Override
  public void dialogNegative(DialogInterface dialogInterface) {
    dialogInterface.cancel();
  }

  @Override
  public void dialogSetting(DialogInterface dialogInterface) {

  }

}

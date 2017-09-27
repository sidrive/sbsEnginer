package id.geekgarden.esi;

import android.content.Intent;
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
import id.geekgarden.esi.data.model.engginer.ResponseEngginer;
import id.geekgarden.esi.listprojects.ListProjects;
import id.geekgarden.esi.listtiket.ListTiket;
import id.geekgarden.esi.login.LoginActivity;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import id.geekgarden.esi.profile.ProfileActivity;
import id.geekgarden.esi.sabaactivity.SabaActivity;
/*import id.geekgarden.esi.smom.SmOmActivity;*/
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
  private FirebaseAnalytics mFirebaseAnalytics;
  private Observable<ResponseEngginer> response;
  private Api mApi;
  private String key_push;
  private CompositeSubscription subscription;
  private GlobalPreferences glpref;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    ButterKnife.bind(this);
    mApi = ApiService.getervice();
    glpref = new GlobalPreferences(getApplicationContext());
    String fcm_token = FirebaseInstanceId.getInstance().getToken();
    Log.e("onCreate", "fcmtoken: "+fcm_token );
    String refreshtoken = glpref.read(PrefKey.refreshToken,String.class);
    Log.e("onCreate", "refreshtoken: "+refreshtoken );
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    Log.e("onCreate", "accesstoken: "+accessToken );
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
  }

  private void GetDataUser() {
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    Observable<ResponseUser> userrespon = mApi.GetUserData(accessToken).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    userrespon.subscribe(new Observer<ResponseUser>() {
      @Override
      public void onCompleted() {
      }
      @Override
      public void onError(Throwable e) {
        Log.e("onError", "MainActivity" + e.getMessage());
      }

      @Override
      public void onNext(ResponseUser responseUser) {
        if (responseUser.getData()!=null){
          String Id = responseUser.getData().getId().toString();
          String Id_Employee = responseUser.getData().getEmployeeId().toString();
          String email = responseUser.getData().getEmail().toString();
          String name = responseUser.getData().getName().toString();
          String phone_number = responseUser.getData().getPhoneNumber().toString();
          String user_type = responseUser.getData().getType().toString();
          glpref.write(PrefKey.id, Id,String.class);
          glpref.write(PrefKey.id_employee_, Id_Employee,String.class);
          glpref.write(PrefKey.email, email, String.class);
          glpref.write(PrefKey.full_name, name, String.class);
          glpref.write(PrefKey.phone_number, phone_number, String.class);
          glpref.write(PrefKey.userType,user_type,String.class);

        }
      }
    });
  }

  private void sendTokenToServer(final String token) {
    String accessToken = glpref.read(PrefKey.accessToken, String.class);
    BodyFCM bodyFCM = new BodyFCM();
    bodyFCM.setFcmToken(token);
    rx.Observable<ResponseFCM> respon = mApi.updateFcmToken(accessToken,bodyFCM).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    respon.subscribe(new Observer<ResponseFCM>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.e("onError", "MainActivity" + e.getMessage());
      }

      @Override
      public void onNext(ResponseFCM responseFCM) {
        Log.e("", "onNext: "+ responseFCM.getData().getFcmToken().toString());
      }
    });
  }

 /* private void getDataTiketFromJsonToFirebase() {
    response = mapi.getEgginer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    response.subscribe(new Observer<ResponseEngginer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(ResponseEngginer responseTikets) {

        EngginerItem item = new EngginerItem();
        for (int i = 0; i < responseTikets.getEngginer().size(); i++) {
          item.setId(responseTikets.getEngginer().get(i).getId());
          item.setName(responseTikets.getEngginer().get(i).getName());
          mTiketRef.push().setValue(item);

        }
      }
    });
  }*/

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

}

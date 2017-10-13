package id.geekgarden.esi.splash;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import id.geekgarden.esi.MainActivity;
import id.geekgarden.esi.R;
import id.geekgarden.esi.helper.UiUtils;
import id.geekgarden.esi.login.LoginActivity;
import id.geekgarden.esi.preference.CachePreferences;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import java.util.List;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.AppSettingsDialog.Builder;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

/**
 * Created by komuri on 30/08/2017.
 */

public class SplashScreen extends AppCompatActivity implements PermissionCallbacks {
    private Thread splashThread;
    private GlobalPreferences GlPref;
    private CachePreferences ChPref;
    private String access_token;
  private static final String[] LOCATION_AND_CONTACTS =
      {permission.CAMERA,permission.BLUETOOTH,permission.BLUETOOTH_ADMIN};
  private static final int RC_CAMERA_PERM = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GlPref = new GlobalPreferences(this);
        ChPref= new CachePreferences(getApplicationContext(),"GlobalPreferences");
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
        requestPermissionCamera();
      }
        access_token = GlPref.read(PrefKey.accessToken,String.class);

        //thread for splash screen running
        splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    int wait = 0;
                    while (wait<2500){
                        sleep(100);
                        wait += 100;
                    }
                    //gotoTest();
                    if (access_token.length()==0){
                        gotologin();
                    }else {
                        gotoMain();
                    }

                    SplashScreen.this.finish();
                }catch (Exception e){


                    Log.e("Splash","error :"+e.getMessage());
                }finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashThread.start();
    }

  private void requestPermissionCamera() {
    EasyPermissions.requestPermissions(
        this,
        getString(R.string.rationale_camera),
        RC_CAMERA_PERM,
        permission.CAMERA);
  }

  private void gotoMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void gotologin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
      if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
        new Builder(this).build().show();
      }
    }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @SuppressLint("StringFormatMatches")
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
      String yes = getString(R.string.yes);
      String no = getString(R.string.no);
      // Do something after user returned from app settings screen, like showing a Toast.
      UiUtils.showToast(this,getString(R.string.returned_from_app_settings_to_activity,
          hasCameraPermission() ? yes : no));
    }
  }
  private boolean hasCameraPermission() {
    return EasyPermissions.hasPermissions(this, permission.CAMERA);
  }
}
package id.geekgarden.esi.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import id.geekgarden.esi.MainActivity;
import id.geekgarden.esi.R;
import id.geekgarden.esi.login.LoginActivity;
import id.geekgarden.esi.preference.CachePreferences;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;

/**
 * Created by komuri on 30/08/2017.
 */

public class SplashScreen extends AppCompatActivity {
    private Thread splashThread;
    private GlobalPreferences GlPref;
    private CachePreferences ChPref;
    private String access_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GlPref = new GlobalPreferences(this);
        ChPref= new CachePreferences(getApplicationContext(),"GlobalPreferences");
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

}
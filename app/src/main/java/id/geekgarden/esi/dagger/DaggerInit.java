package id.geekgarden.esi.dagger;

import android.app.Application;
import android.content.Context;
import id.geekgarden.esi.StsApplicationDex;
import id.geekgarden.esi.dagger.component.AppComponent;
import id.geekgarden.esi.dagger.component.DaggerAppComponent;
import id.geekgarden.esi.dagger.component.DaggerNetworkComponent;
import id.geekgarden.esi.dagger.component.NetworkComponent;
import id.geekgarden.esi.dagger.module.AppModule;
import id.geekgarden.esi.dagger.module.NetworkModule;

/**
 * Created by rakasettya on 11/15/17.
 */

public class DaggerInit {

  public static AppComponent appComponent(Application application) {
    return DaggerAppComponent.builder().appModule(new AppModule(application)).build();
  }

  public static NetworkComponent networkComponent(Context context) {
    return DaggerNetworkComponent.builder()
        .appComponent(StsApplicationDex.component())
        .networkModule(new NetworkModule(context))
        .build();
  }
}

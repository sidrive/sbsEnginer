package id.geekgarden.esi;

import android.support.multidex.MultiDexApplication;
import id.geekgarden.esi.dagger.DaggerInit;
import id.geekgarden.esi.dagger.component.AppComponent;


public class StsApp extends MultiDexApplication {

  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    setupComponent();
  }
  private void setupComponent() {
    appComponent = DaggerInit.appComponent(this);
    appComponent.inject(this);
  }

  public static AppComponent component() {
    return appComponent;
  }

}

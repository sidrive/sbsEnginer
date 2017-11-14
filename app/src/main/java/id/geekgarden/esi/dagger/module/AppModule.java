package id.geekgarden.esi.dagger.module;

import android.content.Context;
import id.geekgarden.esi.dagger.PerApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Fajar Rianda on 01/05/2017.
 */
@Module
public class AppModule {

  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @PerApp @Provides Context provideContext() {
    return context;
  }

}

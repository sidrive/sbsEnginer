package id.geekgarden.esi.dagger.module;

import android.content.Context;
import id.geekgarden.esi.dagger.PerApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rakasettya on 11/15/17.
 */

@Module
public class AppModule {

  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @PerApplication
  @Provides Context provideContext() {
    return context;
  }

}

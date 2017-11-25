package id.geekgarden.esi.dagger.component;

import id.geekgarden.esi.StsApplicationDex;
import id.geekgarden.esi.dagger.PerApplication;
import id.geekgarden.esi.dagger.module.AppModule;
import dagger.Component;

/**
 * Created by rakasettya on 11/15/17.
 */

@PerApplication
@Component(modules = { AppModule.class})
public interface AppComponent {

  void inject(StsApplicationDex app);
}

package id.geekgarden.esi.dagger.component;

import id.geekgarden.esi.StsApp;
import id.geekgarden.esi.dagger.PerApp;
import id.geekgarden.esi.dagger.module.AppModule;
import dagger.Component;

@PerApp
@Component(modules = { AppModule.class})
public interface AppComponent {

  void inject(StsApp app);
}

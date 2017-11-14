package id.geekgarden.esi.dagger.component;

import id.geekgarden.esi.dagger.PerActivity;
import id.geekgarden.esi.dagger.module.NetworkModule;
import dagger.Component;

/**
 * Created by rakasettya on 11/15/17.
 */

@PerActivity @Component(modules = { NetworkModule.class }, dependencies = { AppComponent.class })
public interface NetworkComponent {

}

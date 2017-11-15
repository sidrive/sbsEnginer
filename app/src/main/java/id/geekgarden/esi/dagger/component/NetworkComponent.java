package id.geekgarden.esi.dagger.component;

import id.geekgarden.esi.dagger.PerActivity;
import id.geekgarden.esi.dagger.module.NetworkModule;
import dagger.Component;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailInstrumentForm;
import id.geekgarden.esi.listtiket.activityticketstaff.DetailOnProgresvisitPmOther;

/**
 * Created by rakasettya on 11/15/17.
 */

@PerActivity @Component(modules = { NetworkModule.class }, dependencies = { AppComponent.class })
public interface NetworkComponent {

  void inject(DetailInstrumentForm detailInstrumentForm);
}

package id.geekgarden.esi.dagger;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rakasettya on 11/15/17.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}

package id.geekgarden.esi.dagger.module;

import android.content.Context;
import id.geekgarden.esi.dagger.PerActivity;
import dagger.Module;
import dagger.Provides;
import id.geekgarden.esi.data.apis.ApiService;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rakasettya on 11/15/17.
 */

@Module public class NetworkModule {

  private final Context context;

  public NetworkModule(Context context) {
    this.context = context;
  }

  @Provides Context provideContext() {
    return context;
  }

  @Provides @PerActivity public OkHttpClient provideOkHttpClient(Context context) {
    try {
      return new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .connectTimeout(60, TimeUnit.SECONDS)
          .addInterceptor(new HttpLoggingInterceptor())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Provides @PerActivity
  public Retrofit.Builder providerRetrofitBuilder(OkHttpClient okHttpClient) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(okHttpClient);
  }

  @Provides @PerActivity public Retrofit provideRetrofit(Retrofit.Builder builder) {
    return builder.baseUrl(ApiService.BASE_URL).build();
  }

  @Provides @PerActivity public ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }
}

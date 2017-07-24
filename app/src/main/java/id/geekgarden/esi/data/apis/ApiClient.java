package id.geekgarden.esi.data.apis;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public class ApiClient {
  private static Retrofit retrofit = null;
  public static Retrofit getClient(String baseUrl){
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .readTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(loggingInterceptor)
        .build();
    if (retrofit == null){
      retrofit = new Retrofit.Builder()
          .baseUrl(baseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build();
    }
    return retrofit;
  }

}

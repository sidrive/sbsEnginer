package id.geekgarden.esi.data.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public class ApiClient {
  private static Retrofit retrofit = null;
  public static Retrofit getClient(String baseUrl){
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(FooRuntimeTypeAdapter.class, new FooRuntimeTypeAdapter());
    Gson gson = builder.create();
            /*.registerTypeAdapter(Date.class, new DateDeserializer())
            .registerTypeAdapter(Date.class, new DateSerializer())
            .create();*/
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
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .client(okHttpClient)
          .build();
    }
    return retrofit;
  }

}

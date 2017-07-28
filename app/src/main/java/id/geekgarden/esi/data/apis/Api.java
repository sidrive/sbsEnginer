package id.geekgarden.esi.data.apis;

import id.geekgarden.esi.data.model.ResponseTiketSample;
import id.geekgarden.esi.data.model.ResponseUsers;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface Api {
  @GET("users")
  Observable<ResponseUsers> getdataUsers();
  @GET("BambangHeriSetiawan/json/master/tikets.json")
  Observable<ResponseTiketSample> getdataTiketSample();
  }

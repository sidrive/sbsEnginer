package id.geekgarden.esi.data.apis;


import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.data.model.Login.BodyLogin;
import id.geekgarden.esi.data.model.Login.ResponseLogin;
import id.geekgarden.esi.data.model.User.ResponseUser;
import id.geekgarden.esi.data.model.engginer.ResponseEngginer;
import id.geekgarden.esi.data.model.kode_kegiatan.ResponseKodeKegiatan;
import id.geekgarden.esi.data.model.prioritys.ResponsePrioritys;
import id.geekgarden.esi.data.model.projects.ResponseProjects;
import id.geekgarden.esi.data.model.shi.ResponseShi;
import id.geekgarden.esi.data.model.sn_alat.ResponseSnAlat;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.detailopentiket.ResponseDetailTiket;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface Api {

  @GET("/api/engineer/tickets?status=new")
  Observable<ResponseTikets> getTikets(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=confirmed")
  Observable<ResponseTikets> getTiketsconfirmed(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=cancelled")
  Observable<ResponseTikets> getTiketscancelled(
          @Header("Authorization") String header);

  @POST("/api/token")
  Observable<ResponseLogin> authenticate(@Body BodyLogin bodyLogin);

  @Headers("Accept:application/json")
  @PUT("/api/update-fcm-token")
  Observable<ResponseFCM> updateFcmToken(
          @Header("Authorization") String header,
          @Body BodyFCM fcmToken);

  @GET("/api/me")
  Observable<ResponseUser> GetUserData (
          @Header("Authorization") String header);

  @Headers("Accept: application/json")
  @GET("/api/engineer/ticket/{id}")
  Observable<ResponseDetailTiket> detailtiket (
          @Header("Authorization") String header,
          @Path("id") String id);


  @GET("json/engginer.json")
  Observable<ResponseEngginer> getEgginer();
  @GET("json/kode_kegiatan.json")
  Observable<ResponseKodeKegiatan> getKodeKegiatan();
  @GET("json/prioritys.json")
  Observable<ResponsePrioritys> getPriority();
  @GET("json/projects.json")
  Observable<ResponseProjects> getProjects();
  @GET("json/shi.json")
  Observable<ResponseShi> getSHI();
  @GET("json/sn_alat.json")
  Observable<ResponseSnAlat> getSnAlat();

  }

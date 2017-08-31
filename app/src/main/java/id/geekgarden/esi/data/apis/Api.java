package id.geekgarden.esi.data.apis;


import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.data.model.Login.BodyLogin;
import id.geekgarden.esi.data.model.Login.Data;
import id.geekgarden.esi.data.model.Login.ResponseLogin;
import id.geekgarden.esi.data.model.User.ResponseUser;
import id.geekgarden.esi.data.model.engginer.ResponseEngginer;
import id.geekgarden.esi.data.model.kode_kegiatan.ResponseKodeKegiatan;
import id.geekgarden.esi.data.model.prioritys.ResponsePrioritys;
import id.geekgarden.esi.data.model.projects.ResponseProjects;
import id.geekgarden.esi.data.model.shi.ResponseShi;
import id.geekgarden.esi.data.model.sn_alat.ResponseSnAlat;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets_dialihkan.ResponseTiketsDialihkan;
import id.geekgarden.esi.data.model.tikets_penugasan.ResponseTiketsPenugasan;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface Api {

  @GET("json/tikets.json")
  Observable<ResponseTikets> getTikets();
  @GET("json/tikets_penugasan.json")
  Observable<ResponseTiketsPenugasan> getTiketsPenugasan();
  @GET("json/tikets_dialihkan.json")
  Observable<ResponseTiketsDialihkan> getTiketsDialihkan();
  @Headers("Content_Type: application/json")
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

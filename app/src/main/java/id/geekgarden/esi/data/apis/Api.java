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
import id.geekgarden.esi.data.model.reocurrence.ResponseReocurrence;
import id.geekgarden.esi.data.model.saba.detailsaba.ResponseDetailSaba;
import id.geekgarden.esi.data.model.saba.getsaba.ResponseSaba;
import id.geekgarden.esi.data.model.saba.updateendsaba.ResponseEndSaba;
import id.geekgarden.esi.data.model.saba.updatesaba.BodySaba;
import id.geekgarden.esi.data.model.saba.updatesaba.ResponseUpdateSaba;
import id.geekgarden.esi.data.model.shi.ResponseShi;
import id.geekgarden.esi.data.model.sn_alat.ResponseSnAlat;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.part.ResponsePart;
import id.geekgarden.esi.data.model.tikets.relatedticket.ResponseRelatedTicket;
import id.geekgarden.esi.data.model.tikets.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.data.model.tikets.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.data.model.tikets.updatestartedtiket.ResponseStartedTiket;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface Api {

  // ===================================================================
  //                                Ticket
  // ===================================================================

  @GET("/api/engineer/tickets?status=new")
  Observable<ResponseTikets> getTiketsnew(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=confirmed")
  Observable<ResponseTikets> getTiketsconfirmed(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=cancelled")
  Observable<ResponseTikets> getTiketscancelled(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=started")
  Observable<ResponseTikets> getTiketstarted(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=restarted")
  Observable<ResponseTikets> getTiketrestarted(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=held")
  Observable<ResponseTikets> getTiketheld(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets?status=done")
  Observable<ResponseTikets> getTiketended(
          @Header("Authorization") String header);

  @GET("/api/engineer/tickets")
  Observable<ResponseTikets> getTiketall(
          @Header("Authorization") String header);

  @POST("/api/token")
  Observable<ResponseLogin> authenticate(
          @Body BodyLogin bodyLogin);

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

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/confirm")
  Observable<ResponseConfirmTicket> updateconfirmtiket(
          @Header("Authorization") String header,
          @Path("id") String id,
          @Body BodyConfirmTicket comment);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/hold")
  Observable<ResponseOnProgress> updateonholdtiket(
          @Header("Authorization") String header,
          @Path("id") String id,
          @Body BodyOnProgress bodyOnProgress);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/end")
  Observable<ResponseOnProgressEnd> updateonendtiket(
          @Header("Authorization") String header,
          @Path("id") String id,
          @Body BodyOnProgress bodyOnProgress);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/start")
  Observable<ResponseStartedTiket> updateonstarttiket(
          @Header("Authorization") String header,
          @Path("id") String id);


  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/restart")
  Observable<ResponseOnRestart> updateonrestarttiket(
          @Header("Authorization") String header,
          @Path("id") String id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket-activities")
  Observable<Responsespinneronprogress> getSpinneronprogress(
          @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket/{id}/service-reports")
  Observable<ResponseServiceReport> getservicereport (
          @Header("Authorization") String header,
          @Path("id")String id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket/{id}/service-report/{ticket_id}/parts")
  Observable<ResponsePart> getpart (
          @Header("Authorization") String header,
          @Path("id")String id,
          @Path("ticket_id")String ticket_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket/{ticket_id}/associate/{related_ticket_id}")
  Observable<ResponseRelatedTicket> putrelatedticket (
          @Header("Authorization") String header,
          @Path("id")String id,
          @Path("related_ticket_id")String related_ticket_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/recently-closed-tickets")
  Observable<ResponseReocurrence> getreocurrence (
          @Header("Authorization") String header);

  // ===================================================================
  //                                SABA
  // ===================================================================
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @POST("/api/engineer/saba-activity")
  Observable<ResponseUpdateSaba> updateonsaba (
          @Header("Authorization") String header,
          @Body BodySaba description);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/saba-activities")
  Observable<ResponseSaba> getsaba (
          @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/saba-activity/{id}")
  Observable<ResponseDetailSaba> getdetailsaba (
          @Header("Authorization") String header,
          @Path("id") String id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/saba-activity/{id}/end")
  Observable<ResponseEndSaba> updateendsaba(
          @Header("Authorization") String header,
          @Path("id") String id);

  }

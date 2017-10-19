package id.geekgarden.esi.data.apis;


import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.data.model.Login.BodyLogin;
import id.geekgarden.esi.data.model.Login.ResponseLogin;
import id.geekgarden.esi.data.model.User.ResponseUser;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responseopenticketservice.ResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.ResponseSpinnerInstrument;
import id.geekgarden.esi.data.model.openticket.responsespinnerother.ResponseSpinnerOther;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
import id.geekgarden.esi.data.model.reocurrence.ResponseReocurrence;
import id.geekgarden.esi.data.model.saba.detailsaba.ResponseDetailSaba;
import id.geekgarden.esi.data.model.saba.getsaba.ResponseSaba;
import id.geekgarden.esi.data.model.saba.updateendsaba.ResponseEndSaba;
import id.geekgarden.esi.data.model.saba.updatesaba.BodySaba;
import id.geekgarden.esi.data.model.saba.updatesaba.ResponseUpdateSaba;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.part.ResponsePart;
import id.geekgarden.esi.data.model.tikets.part.partstatus.ResponseSpinnerPartStatus;
import id.geekgarden.esi.data.model.tikets.relatedticket.ResponseRelatedTicket;
import id.geekgarden.esi.data.model.tikets.searchtiket.ResponseSearchTiket;
import id.geekgarden.esi.data.model.tikets.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.spinnerpminstrument.ResponseSpinnerPMInstrument;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.data.model.tikets.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.data.model.tikets.updatestartedtiket.ResponseStartedTiket;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

  @Multipart
  @Headers({"Accept: application/json"})
  @POST("/api/engineer/ticket/{id}/upload-image")
  Observable<RequestBody> updateimage(
      @Header("Authorization") String header,
      @Path("id") String id,
      @Part MultipartBody.Part image);

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
  @PUT("/api/engineer/ticket/{ticket_id}/associate/{related_ticket_id}")
  Observable<ResponseRelatedTicket> putrelatedticket (
          @Header("Authorization") String header,
          @Path("ticket_id")String ticket_id,
          @Path("related_ticket_id")String related_ticket_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/recently-closed-tickets")
  Observable<ResponseReocurrence> getreocurrence (
          @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/customer/{customer_id}/instruments")
  Observable<ResponseSpinnerPMInstrument> getspinnerpminstrument (
      @Header("Authorization") String header,
      @Path("customer_id")String customer_id);
  // ===================================================================
  //                                PART
  // ===================================================================

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
  @GET("/api/part_statuses")
  Observable<ResponseSpinnerPartStatus> getpartstatus(
      @Header("Authorization") String header);

  // ===================================================================
  //                             OPEN TICKET
  // ===================================================================
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/division")
  Observable<ResponseSpinnerDivision> getspinnerdivision (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket-arrangements")
  Observable<ResponseSpinnerPriority> getspinnerpriority (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/division/{division_id}/customer")
  Observable<ResponseSpinnerCustomer> getspinnercustomer (
      @Header("Authorization") String header,
      @Path("division_id")int division_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/division/{division_id}/customer/{customer_id}/instrument")
  Observable<ResponseSpinnerInstrument> getspinnerinstrument (
      @Header("Authorization") String header,
      @Path("division_id")int division_id,
      @Path("customer_id")int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/division/{division_id}/customer/{customer_id}/instrument/{instrument_id}/staff")
  Observable<ResponseSpinnerEngineer> getspinnerengineer (
      @Header("Authorization") String header,
      @Path("division_id")int division_id,
      @Path("customer_id")int customer_id,
      @Path("instrument_id")int instrument_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @POST("/api/engineer/ticket")
  Observable<ResponseOpenservice> openticketservice(
      @Header("Authorization") String header,
      @Body BodyResponseOpenservice bodyResponseOpenservice);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/requests/{division_id}")
  Observable<ResponseSpinnerOther> getspinnerother (
      @Header("Authorization") String header,
      @Path("division_id")int division_id);


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


  // ===================================================================
  //                             SEARCH TICKET
  // ===================================================================

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/tickets")
  Observable<ResponseSearchTiket> searchtiket (
          @Header("Authorization") String header,
          @Query("q") String id);

}

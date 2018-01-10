package id.geekgarden.esi.data.apis;


import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.data.model.Login.BodyLogin;
import id.geekgarden.esi.data.model.Login.ResponseLogin;
import id.geekgarden.esi.data.model.User.ResponseUser;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenOther;
import id.geekgarden.esi.data.model.openticket.BodyResponseOpenService;
import id.geekgarden.esi.data.model.openticket.responsehardware.ResponseHardware;
import id.geekgarden.esi.data.model.openticket.responseinstrumentinstall.ResponseInstrumentInstall;
import id.geekgarden.esi.data.model.openticket.responseinstrumentreturn.ResponseInstrumentReturn;
import id.geekgarden.esi.data.model.openticket.responseinterface.ResponseCategory;
import id.geekgarden.esi.data.model.openticket.responseopenticketservice.ResponseOpenservice;
import id.geekgarden.esi.data.model.openticket.responsesoftware.ResponseSoftware;
import id.geekgarden.esi.data.model.openticket.responsespinnercustomer.ResponseSpinnerCustomer;
import id.geekgarden.esi.data.model.openticket.responsespinnerdivision.ResponseSpinnerDivision;
import id.geekgarden.esi.data.model.openticket.responsespinnerengineer.ResponseSpinnerEngineer;
import id.geekgarden.esi.data.model.openticket.responsespinnerinstrument.ResponseSpinnerInstrument;
import id.geekgarden.esi.data.model.openticket.responsespinnerpriority.ResponseSpinnerPriority;
import id.geekgarden.esi.data.model.openticket.responsespinnerrequest.ResponseRequestSpv;
import id.geekgarden.esi.data.model.reocurrence.ResponseReocurrence;
import id.geekgarden.esi.data.model.saba.detailsaba.ResponseDetailSaba;
import id.geekgarden.esi.data.model.saba.getsaba.ResponseSaba;
import id.geekgarden.esi.data.model.saba.updateendsaba.ResponseEndSaba;
import id.geekgarden.esi.data.model.saba.updatesaba.BodySaba;
import id.geekgarden.esi.data.model.saba.updatesaba.ResponseUpdateSaba;
import id.geekgarden.esi.data.model.tikets.staffticket.model.SpinnerOnProgress.Responsespinneronprogress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist.BodyChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping.BodyShipping;
import id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit.BodyChecklistVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer.ResponseAnalyzer;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab.ResponseHclab;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ResponseChecklist;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpmit.ResponseChecklistPmIt;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistvisit.ResponseVisit;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket.ResponseDetailTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.detailticketother.ResponseTicketDetailOther;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.ResponsePart;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.partstatus.ResponseSpinnerPartStatus;
import id.geekgarden.esi.data.model.tikets.staffticket.model.relatedticket.ResponseRelatedTicket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation.BodyInstallation;
import id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation.ResponseInstalled;
import id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket.ResponseSearchTiket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.servicereport.ResponseServiceReport;
import id.geekgarden.esi.data.model.tikets.staffticket.model.spinnerpminstrument.ResponsePMInstrument;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer.ResponseDivertedID;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateconfirmticket.BodyConfirmTicket;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateconfirmticket.ResponseConfirmTicket;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.BodyDiverted;
import id.geekgarden.esi.data.model.tikets.supervisorticket.model.updatediverted.ResponseDiverted;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.BodyOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.ended.ResponseOnProgressEnd;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket.hold.ResponseOnProgress;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updaterestartticket.ResponseOnRestart;
import id.geekgarden.esi.data.model.tikets.staffticket.model.updatestartedtiket.ResponseStartedTiket;
import id.geekgarden.esi.data.model.tikets.ticket.ResponseTikets;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface  Api {

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

  @GET("/api/engineer/tickets?status=closed")
  Observable<ResponseTikets> getTiketsclosed(
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

  @GET("/api/engineer/switch/tickets")
  Observable<ResponseTikets> getTiketswitch(
      @Header("Authorization") String header);

  @GET("/api/engineer/spv/complaint/tickets")
  Observable<ResponseTikets> getTiketscomplain(
      @Header("Authorization") String header);

  @GET("/api/engineer/spv/tickets?status=closed")
  Observable<ResponseTikets> getSpvTiketsclose(
      @Header("Authorization") String header);

  @GET("/api/engineer/spv/tickets?status=cancelled")
  Observable<ResponseTikets> getSpvTiketscancelled(
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

  @Headers("Accept: application/json")
  @GET("/api/engineer/ticket/{id}?include=division,request")
  Observable<ResponseTicketDetailOther> detailticketother (
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
  @GET("/api/engineer/{division_id}/ticket-activities")
  Observable<Responsespinneronprogress> getSpinneronprogress(
          @Header("Authorization") String header,
          @Path("division_id") String division_id);

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

  @Streaming
  @Headers({"Content-Type: application/json"})
  @GET("/api/invoice/{id}/print")
  Call<ResponseBody> downloadpdf(
      @Header("Authorization") String header,
      @Path("id")String id);

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
  @GET("/api/engineer/division/{division_id}/customer/{customer_id}/staff")
  Observable<ResponseSpinnerEngineer> getspinnerengineer (
      @Header("Authorization") String header,
      @Path("division_id")int division_id,
      @Path("customer_id")int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @POST("/api/engineer/ticket")
  Observable<ResponseOpenservice> openticketservice(
      @Header("Authorization") String header,
      @Body BodyResponseOpenService bodyResponseOpenService);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @POST("/api/engineer/ticket")
  Observable<ResponseOpenservice> openticketother(
      @Header("Authorization") String header,
      @Body BodyResponseOpenOther bodyResponseOpenOther);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/customer/{customer_id}/instrument-install")
  Observable<ResponseInstrumentInstall> getspinnerinstall(
      @Header("Authorization") String header,
      @Path("customer_id") int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/customer/{customer_id}/instrument-return")
  Observable<ResponseInstrumentReturn> getspinnerreturn(
      @Header("Authorization") String header,
      @Path("customer_id") int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/requests/{division_id}/spv")
  Observable<ResponseRequestSpv> getspinnerrequest(
      @Header("Authorization") String header,
      @Path("division_id") int division_id);

  // ===================================================================
  //                            Ticket Other
  // ===================================================================

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/customer/{customer_id}/instruments")
  Observable<ResponsePMInstrument> getspinnerpminstrument (
          @Header("Authorization") String header,
          @Path("customer_id")String customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/instrument-type/{instrumenttype_id}/checklist")
  Observable<ResponseChecklist> getpmchecklist (
          @Header("Authorization") String header,
          @Path("instrumenttype_id")int instrumenttype_id,
          @Query("type") String type);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/end")
  Observable<ResponseChecklist> updatechecklist (
          @Header("Authorization") String header,
          @Path("id")String id,
          @Body BodyChecklist bodyChecklist);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/visit-checklists")
  Observable<ResponseVisit> getvisitchecklist (
          @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/end")
  Observable<ResponseChecklist> updatechecklistvisit (
          @Header("Authorization") String header,
          @Path("id")String id,
          @Body BodyChecklistVisit bodyChecklistVisit);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/instrument-type/{instrumenttype_id}/checklist")
  Observable<ResponseChecklist> getshippingchecklist (
          @Header("Authorization") String header,
          @Path("instrumenttype_id")int instrumenttype_id,
          @Query("type") String type);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket/{ticket}/ticket-checklists")
  Observable<ResponseChecklist> getticketchecklist (
          @Header("Authorization") String header,
          @Path("ticket")int ticket);
//          @Query("type") String type);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/end")
  Observable<ResponseChecklist> updateshippingchecklist(
          @Header("Authorization") String header,
          @Path("id")String id,
          @Body BodyShipping bodyShipping);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("/api/engineer/ticket/{id}/install")
    Observable<ResponseInstalled> getinstallation (
      @Header("Authorization") String header,
      @Path("id")String id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/install")
  Observable<ResponseInstalled> updateinstallation (
      @Header("Authorization") String header,
      @Path("id")String id,
      @Body BodyInstallation bodyInstallation);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/software/{software_id}/checklists")
  Observable<ResponseHclab> getithclab (
          @Header("Authorization") String header,
          @Path("software_id")String software_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/hardware/{interfaceType_id}/checklists")
  Observable<ResponseAnalyzer> getitanalyzer (
      @Header("Authorization") String header,
      @Path("interfaceType_id")String interfaceType_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/categories")
  Observable<ResponseCategory> getcategory (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/{customer_id}/hardwares")
  Observable<ResponseHardware> getHardware (
      @Header("Authorization") String header,
      @Path("customer_id")int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/{customer_id}/softwares")
  Observable<ResponseSoftware> getSoftware (
      @Header("Authorization") String header,
      @Path("customer_id")int customer_id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/pm/checklists")
  Observable<ResponseChecklistPmIt> getPmIt (
      @Header("Authorization") String header);

  // ===================================================================
  //                          Supervisor Ticket
  // ===================================================================
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=new")
  Observable<ResponseTikets> getticketopenspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets")
  Observable<ResponseTikets> getticketallspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=started")
  Observable<ResponseTikets> getticketonprogressspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=confirmed")
  Observable<ResponseTikets> getticketconfirmspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=held")
  Observable<ResponseTikets> getticketonholdspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=done")
  Observable<ResponseTikets> getticketendedspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/customer/tickets?status=restarted")
  Observable<ResponseTikets> getticketonprogressholdspvcust (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets")
  Observable<ResponseTikets> getticketallspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=new")
  Observable<ResponseTikets> getticketopenspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=confirmed")
  Observable<ResponseTikets> getticketconfirmspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=started")
  Observable<ResponseTikets> getticketonprogressnewspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=held")
  Observable<ResponseTikets> getticketheldspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=restarted")
  Observable<ResponseTikets> getticketonprogressholdspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/spv/tickets?status=done")
  Observable<ResponseTikets> getticketendedspv (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets")
  Observable<ResponseTikets> getticketallspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=new")
  Observable<ResponseTikets> getticketopenspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=confirmed")
  Observable<ResponseTikets> getticketconfirmedspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=started")
  Observable<ResponseTikets> getticketonprogressnewspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=held")
  Observable<ResponseTikets> getticketheldspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=restarted")
  Observable<ResponseTikets> getticketonprogressholdspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/switch/tickets?status=done")
  Observable<ResponseTikets> getticketendedspvalih (
      @Header("Authorization") String header);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @GET("/api/engineer/ticket/{id}/switch")
  Observable<ResponseDivertedID> getassignid (
      @Header("Authorization") String header,
      @Path("id") String id);

  @Headers({"Accept: application/json", "Content-Type: application/json"})
  @PUT("/api/engineer/ticket/{id}/switch")
  Observable<ResponseDiverted> updateassign (
      @Header("Authorization") String header,
      @Path("id") String id,
      @Body BodyDiverted bodyDiverted);


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

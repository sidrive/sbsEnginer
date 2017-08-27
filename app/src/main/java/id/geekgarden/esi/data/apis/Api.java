package id.geekgarden.esi.data.apis;


import id.geekgarden.esi.data.model.engginer.ResponseEngginer;
import id.geekgarden.esi.data.model.kode_kegiatan.ResponseKodeKegiatan;
import id.geekgarden.esi.data.model.prioritys.ResponsePrioritys;
import id.geekgarden.esi.data.model.projects.ResponseProjects;
import id.geekgarden.esi.data.model.shi.ResponseShi;
import id.geekgarden.esi.data.model.sn_alat.ResponseSnAlat;
import id.geekgarden.esi.data.model.tikets.ResponseTikets;
import id.geekgarden.esi.data.model.tikets_dialihkan.ResponseTiketsDialihkan;
import id.geekgarden.esi.data.model.tikets_penugasan.ResponseTiketsPenugasan;
import retrofit2.http.GET;
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

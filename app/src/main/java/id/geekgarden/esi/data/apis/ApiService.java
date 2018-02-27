package id.geekgarden.esi.data.apis;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public class ApiService {
  /*public final static String BASE_URL = "https://reqres.in/api/";*/
  //public final static String BASE_URL = "http://saba.dev.komuri.co.id/api/";
  public final static String BASE_URL = "http://43.247.12.54:9015/api/";
  //public final static String URL_INVOICE = "saba.dev.komuri.co.id/id/invoice?";
  public static Api getService(){
    return ApiClient.getClient(BASE_URL).create(Api.class);
  }
}
